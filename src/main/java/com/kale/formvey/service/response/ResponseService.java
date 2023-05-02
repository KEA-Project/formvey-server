package com.kale.formvey.service.response;

import com.kale.formvey.domain.*;
import com.kale.formvey.dto.answer.PostAnswerReq;
import com.kale.formvey.dto.response.AnswerCount;
import com.kale.formvey.dto.response.GetResponseStatisticsRes;
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

    public List<GetResponseStatisticsRes> getResponseStatistics(Long surveyId) {
        List<GetResponseStatisticsRes> getResponseStatisticsRes = new ArrayList<>();
        List<Question> questions = questionRepository.findBySurveyId(surveyId);

        for (Question question : questions) {
            List<Answer> answers = answerRepository.findByQuestionId(question.getId());
            List<Choice> choices = choiceRepository.findByQuestionId(question.getId());
            int[] choiceCntList = new int[choices.size()]; // 객관식 답변 카운트 리스트
            Arrays.fill(choiceCntList, 0); // 0으로 초기화

            if (question.getType() == 0) { // 주관식이면 주관식 답변 리스트 반환 객관식 답변은 null
                List<String> answerContents = new ArrayList<>();

                for (Answer answer : answers) {
                    answerContents.add(answer.getAnswerContent());
                }
                getResponseStatisticsRes.add(new GetResponseStatisticsRes(question.getQuestionIdx(), question.getQuestionTitle(), question.getType(), null, answerContents));
            }
            else { // 객관식 답변 리스트 반환
                for (Answer answer : answers) {
                    choiceCntList[Integer.parseInt(answer.getAnswerContent()) - 1]++;
                }
                getResponseStatisticsRes.add(new GetResponseStatisticsRes(question.getQuestionIdx(), question.getQuestionTitle(), question.getType(), choiceCntList, null));
            }
        }
        return getResponseStatisticsRes;
    }
}
