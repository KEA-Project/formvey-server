package com.kale.formvey.service.response;

import com.kale.formvey.config.BaseException;
import com.kale.formvey.domain.*;
import com.kale.formvey.dto.answer.GetAnswerRes;
import com.kale.formvey.dto.answer.PostAnswerReq;
import com.kale.formvey.dto.choice.GetChoiceInfoRes;
import com.kale.formvey.dto.question.GetQuestionInfoRes;
import com.kale.formvey.dto.response.*;
import com.kale.formvey.dto.survey.GetSurveyBoardRes;
import com.kale.formvey.dto.survey.GetSurveyInfoRes;
import com.kale.formvey.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.kale.formvey.config.BaseResponseStatus.SURVEYS_EMPTY_SURVEY_ID;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final MemberRepository memberRepository;
    private final SurveyRepository surveyRepository;
    private final ResponseRepository responseRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ChoiceRepository choiceRepository;

    /**
     * 설문 응답
     */
    public void responseSurvey(PostResponseReq dto, Long surveyId, Long memberId) {
        Member member = memberRepository.findById(memberId).get(); // 설문 응답자
        Survey survey = surveyRepository.findById(surveyId).get(); // 응답하고자 하는 설문
        //응답 등록
        Response response = PostResponseReq.toEntity(member, survey, dto);
        response = responseRepository.save(response);

        survey.increaseResponseCnt(survey.getResponseCnt());
        surveyRepository.save(survey);
        
        List<Answer> answer=new ArrayList<>();

        //답변 등록
        for (int i = 0; i < dto.getAnswers().size(); i++) {
            Question question=questionRepository.findById(dto.getAnswers().get(i).getQuestionId()).get();
            answer.add(PostAnswerReq.toEntity(question,response,dto.getAnswers().get(i)));
        }
        answerRepository.saveAll(answer);
    }

    /**
     * 설문 응답 수정
     */
    public void updateResponse(PostResponseReq dto,Long surveyId, Long responseId) {
        Member member = memberRepository.findById(dto.getMemberId()).get(); // 설문 응답자
        Survey survey = surveyRepository.findById(surveyId).get(); // 응답하고자 하는 설문
        Response response = responseRepository.findById(responseId).get();//수정할 응답
        List<Answer> answers=answerRepository.findByResponseId(responseId);//수정할 답변들

        //답변 리스트 초기화
        for(Answer answer:answers){
            answerRepository.delete(answer);
        }
        response.update(dto,member,survey);
        response = responseRepository.save(response);

        //답변 등록
        for (int i = 0; i < dto.getAnswers().size(); i++) {
            Question question=questionRepository.findById(dto.getAnswers().get(i).getQuestionId()).get();
            List<Answer> answer=new ArrayList<>();
            answer.add(PostAnswerReq.toEntity(question,response,dto.getAnswers().get(i)));
            answerRepository.saveAll(answer);
        }
    }

    /**
     * 응답 삭제
     */
    public void deleteResponse(Long responseId) {
        //status 0으로 변경
        Response response=responseRepository.findById(responseId).get();
        response.updateStatus(0);
        responseRepository.save(response);
    }

    /**
     * 응답 설문 리스트 조회
     */
    public List<GetResponseListRes> getResponseList(Long memberId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending()); // 페이징 처리 id 내림차순
        Page<Response> res = responseRepository.findAllByMemberId(memberId,pageRequest);
        List<GetResponseListRes> responses = new ArrayList<>();

        int totalPages = res.getSize();

        totalPages = (totalPages / size) == 0? totalPages /size : (totalPages / size) + 1;

        for (Response response : res) {
            LocalDate nowDate = LocalDate.now();
            LocalDate endDate = response.getSurvey().getEndDate().toLocalDate(); // 시분초 제외한 설문 종료 날짜 변환
            Period period = nowDate.until(endDate); // 디데이 구하기

            GetResponseListRes dto = new GetResponseListRes(response.getSurvey().getId(), response.getId(),response.getSurvey().getSurveyTitle(), response.getSurvey().getSurveyContent(),response.getSurvey().getEndDate().toString(),
                    period.getDays(), response.getSurvey().getStatus(), totalPages);
            responses.add(dto);
        }
        return responses;
    }

    /**
     * 응답 설문 내용,답변 조회
     */
    public GetResponseInfoRes getResponseInfo(Long responseId) {
        Response response=responseRepository.findById(responseId).get();

        List<GetQuestionInfoRes> questions = response.getSurvey().getQuestions().stream()
                .map(question -> {
                    List<GetChoiceInfoRes> choices = question.getChoices().stream()
                            .map(choice -> new GetChoiceInfoRes(choice.getId(),choice.getChoiceIndex(), choice.getChoiceContent()))
                            .collect(Collectors.toList());
                    return new GetQuestionInfoRes(question.getId(),question.getQuestionIdx(), question.getQuestionTitle(),
                            question.getType(), question.getIsEssential(), question.getIsShort(), choices);
                })
                .collect(Collectors.toList());

        List<GetAnswerRes> answers=response.getAnswers().stream()
                .map(answer -> new GetAnswerRes(answer.getQuestion().getId(), answer.getAnswerContent()))
                .collect(Collectors.toList());

        return new GetResponseInfoRes( response.getSurvey().getId(), response.getSurvey().getSurveyTitle(),  response.getSurvey().getSurveyContent(),  response.getSurvey().getStartDate(),  response.getSurvey().getEndDate(),
                response.getSurvey().getIsAnonymous(), response.getSurvey().getStatus(),questions,answers);
    }

    /**
     * 응답 통계 조회
     */
    public List<GetResponseStatisticsRes> getResponseStatistics(Long surveyId) {
        List<GetResponseStatisticsRes> getResponseStatisticsRes = new ArrayList<>();
        List<Question> questions = questionRepository.findBySurveyId(surveyId);

        for (Question question : questions) {
            List<Answer> answers = answerRepository.findByQuestionId(question.getId());
            List<Choice> choices = choiceRepository.findByQuestionId(question.getId());
            List<MultipleChoiceInfo> multipleChoiceInfos= new ArrayList<>();
            List<String> subjectiveAnswers = new ArrayList<>();
            int[] multipleChoiceCnt = new int[choices.size()];
            Arrays.fill(multipleChoiceCnt, 0);

            if (question.getType() == 0) { // 주관식이면 주관식 답변 리스트 반환 객관식 답변은 null
                for (Answer answer : answers) {
                    subjectiveAnswers.add(answer.getAnswerContent());
                }
                getResponseStatisticsRes.add(new GetResponseStatisticsRes(question.getId(), question.getQuestionIdx(), question.getQuestionTitle(), null, subjectiveAnswers));
            }
            else { // 객관식 답변 리스트 반환 - choices 크기 만큼의 int배열 선언, ,answerContent랑 choiceContent랑 비교해서 일치하면 해당 인덱스 int값 상승
                for (Answer answer : answers) {
                    for (Choice choice : choices) {
                        if (answer.getAnswerContent().equals(choice.getChoiceContent())) {
                            multipleChoiceCnt[choice.getChoiceIndex()]++;
                        }
                    }
                }
                for (int i = 0; i < choices.size(); i++) {
                    MultipleChoiceInfo multipleChoiceInfo = new MultipleChoiceInfo(choices.get(i).getChoiceIndex(), choices.get(i).getChoiceContent(), multipleChoiceCnt[i]);
                    multipleChoiceInfos.add(multipleChoiceInfo);
                }
                getResponseStatisticsRes.add(new GetResponseStatisticsRes(question.getId(), question.getQuestionIdx(), question.getQuestionTitle(), multipleChoiceInfos, null));
            }
        }
        return getResponseStatisticsRes;
    }
}
