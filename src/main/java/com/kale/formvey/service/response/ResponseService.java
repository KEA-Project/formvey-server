package com.kale.formvey.service.response;

import com.kale.formvey.domain.*;
import com.kale.formvey.dto.answer.PostAnswerReq;
import com.kale.formvey.dto.response.GetResponseStatisticsRes;
import com.kale.formvey.dto.response.MultipleChoiceInfo;
import com.kale.formvey.dto.response.PostResponseReq;
import com.kale.formvey.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        //답변 등록
        for (int i = 0; i < dto.getAnswers().size(); i++) {
            Question question=questionRepository.findById(dto.getAnswers().get(i).getQuestionId()).get();
            answerRepository.save(PostAnswerReq.toEntity(question, response, dto.getAnswers().get(i).getContent()));
        }
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

            if (question.getType() == 0) { // 주관식이면 주관식 답변 리스트 반환 객관식 답변은 null
                for (Answer answer : answers) {
                    subjectiveAnswers.add(answer.getAnswerContent());
                }
                getResponseStatisticsRes.add(new GetResponseStatisticsRes(question.getId(), question.getQuestionIdx(), question.getQuestionTitle(), null, subjectiveAnswers));
            }
            else { // 객관식 답변 리스트 반환
                getResponseStatisticsRes.add(new GetResponseStatisticsRes(question.getId(), question.getQuestionIdx(), question.getQuestionTitle(), multipleChoiceInfos, null));
            }
        }
        return getResponseStatisticsRes;
    }
}
