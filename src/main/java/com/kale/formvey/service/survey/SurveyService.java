package com.kale.formvey.service.survey;

import com.kale.formvey.config.BaseException;
import com.kale.formvey.domain.*;
import com.kale.formvey.dto.choice.GetChoiceInfoRes;
import com.kale.formvey.dto.choice.PostChoiceReq;
import com.kale.formvey.dto.question.GetQuestionInfoRes;
import com.kale.formvey.dto.question.PostQuestionReq;
import com.kale.formvey.dto.response.GetResponseListRes;
import com.kale.formvey.dto.survey.*;
import com.kale.formvey.repository.ChoiceRepository;
import com.kale.formvey.repository.MemberRepository;
import com.kale.formvey.repository.QuestionRepository;
import com.kale.formvey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.kale.formvey.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Transactional
public class SurveyService {
    private final MemberRepository memberRepository;
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;

    /**
     * 설문 첫 생성 컨트롤 메서드 (status = 1 -> 임시저장 / status = 2 -> 배포)
     */
    public PostSurveyRes createSurvey(Long memberId, PostSurveyReq dto, int status) { // 1 -> 짧폼 저장 x
        Member member = memberRepository.findById(memberId).get();
        Survey survey = PostSurveyReq.toEntity(member, dto);
        survey.setStatus(status);
        survey = surveyRepository.save(survey);

        return setQuestion(dto, survey);
    }

    /**
     * 존재하는 설문 컨트롤 메서드 (status = 1 -> 임시저장 / status = 2 -> 배포)
     */
    public PostSurveyRes updateSurvey(Long surveyId, Long memberId, PostSurveyReq dto, int status) { // 1 -> 짧폼 저장 x
        Member member = memberRepository.findById(memberId).get();
        Survey survey = surveyRepository.findById(surveyId).get();
        List<Question> questions = questionRepository.findBySurveyId(surveyId);

        questionRepository.deleteAll(questions);
        survey.update(dto, member);
        survey.setStatus(status);
        surveyRepository.save(survey);

        return setQuestion(dto, survey);
    }

    private PostSurveyRes setQuestion(PostSurveyReq dto, Survey survey) {
        for (PostQuestionReq postQuestionReq : dto.getQuestions()) {
            Question question = PostQuestionReq.toEntity(survey, postQuestionReq);
            question = questionRepository.save(question);

            // 주관식이 아닌 객관식, 찬부식인 경우
            if (!postQuestionReq.getChoices().isEmpty()) {
                for (PostChoiceReq postChoiceReq : postQuestionReq.getChoices()) {
                    Choice choice = PostChoiceReq.toEntity(question, postChoiceReq);
                    choiceRepository.save(choice);
                }
            }
        }
        return new PostSurveyRes(survey.getId());
    }

    /**
     * 설문 삭제
     */
    public void deleteSurvey(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId).get();
        surveyRepository.delete(survey); //설문과 관련된 모든 것 삭제
    }

    /**
     * 게시판 조회
     */
    public List<GetSurveyBoardRes> getSurveyBoard(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending()); // 페이징 처리 id 내림차순
        Page<Survey> boardSurveys = surveyRepository.findAll(pageRequest);
        List<GetSurveyBoardRes> surveys = new ArrayList<>();

        for (Survey survey : boardSurveys) {
            LocalDate nowDate = LocalDate.now();
            LocalDate endDate = survey.getEndDate().toLocalDate(); // 시분초 제외한 설문 종료 날짜 변환
            Period period = nowDate.until(endDate); // 디데이 구하기

            GetSurveyBoardRes dto = new GetSurveyBoardRes(survey.getId(), survey.getSurveyTitle(), period.getDays(), survey.getResponseCnt(), survey.getMember().getNickname());
            surveys.add(dto);
        }
        return surveys;
    }

    /**
     * 제작 설문 리스트 조회
     */
    public List<GetSurveyListRes> getSurveyList(Long memberId,int page,int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending()); // 페이징 처리 id 내림차순
        Page<Survey> sur = surveyRepository.findByMemberId(memberId,pageRequest);
        List<GetSurveyListRes> surveys = new ArrayList<>();

        for (Survey survey : sur) {
            LocalDate nowDate = LocalDate.now();
            LocalDate endDate = survey.getEndDate().toLocalDate(); // 시분초 제외한 설문 종료 날짜 변환
            Period period = nowDate.until(endDate); // 디데이 구하기

            GetSurveyListRes dto = new GetSurveyListRes(survey.getId(), survey.getSurveyTitle(), survey.getSurveyContent(),survey.getEndDate().toString(),
                    period.getDays(), survey.getResponseCnt(),survey.getStatus());
            surveys.add(dto);
        }
        return surveys;
    }

    /**
     * 설문 내용 조회
     */
    public GetSurveyInfoRes getSurveyInfo(Long surveyId) {
        // 해당 설문 id가 존재하지 않을 때
        if (surveyRepository.findById(surveyId).isEmpty())
            throw new BaseException(SURVEYS_EMPTY_SURVEY_ID);

        Survey survey = surveyRepository.findById(surveyId).get();

        List<GetQuestionInfoRes> questions = survey.getQuestions().stream()
                .map(question -> {
                    List<GetChoiceInfoRes> choices = question.getChoices().stream()
                            .map(choice -> new GetChoiceInfoRes(choice.getId(),choice.getChoiceIndex(), choice.getChoiceContent()))
                            .collect(Collectors.toList());
                    return new GetQuestionInfoRes(question.getId(),question.getQuestionIdx(), question.getQuestionTitle(),
                            question.getType(), question.getIsEssential(), question.getIsShort(), choices);
                })
                .collect(Collectors.toList());

        return new GetSurveyInfoRes(survey.getMember().getId(),survey.getSurveyTitle(), survey.getSurveyContent(), survey.getStartDate(), survey.getEndDate(),
                survey.getResponseCnt(), survey.getIsAnonymous(), survey.getIsPublic(), survey.getUrl(), survey.getExitUrl(), survey.getStatus(),questions);
    }
}