package com.kale.formvey.service.survey;

import com.kale.formvey.domain.Choice;
import com.kale.formvey.domain.Member;
import com.kale.formvey.domain.Question;
import com.kale.formvey.domain.Survey;
import com.kale.formvey.dto.choice.PostChoiceReq;
import com.kale.formvey.dto.question.PostQuestionReq;
import com.kale.formvey.dto.survey.PostSurveyReq;
import com.kale.formvey.dto.survey.PostSurveyRes;
import com.kale.formvey.repository.ChoiceRepository;
import com.kale.formvey.repository.MemberRepository;
import com.kale.formvey.repository.QuestionRepository;
import com.kale.formvey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SurveyService {
    private final MemberRepository memberRepository;
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;

    /**
     * 설문 첫 생성 컨트롤 메서드 (status = 1 -> 배포 / status = 2 -> 임시저장)
     */
    public PostSurveyRes createSurvey(Long memberId, PostSurveyReq dto, int status) { // 1 -> 짧폼 저장 x
        Member member = memberRepository.findById(memberId).get();
        Survey survey = PostSurveyReq.toEntity(member, dto);
        survey.setStatus(status);
        survey = surveyRepository.save(survey);

        return setQuestion(dto, survey);
    }

    /**
     * 존재하는 설문 컨트롤 메서드 (status = 1 -> 배포 / status = 2 -> 임시저장)
     */
    public PostSurveyRes updateSurvey(Long surveyId, Long memberId, PostSurveyReq dto, int status) { // 1 -> 짧폼 저장 x
        Member member = memberRepository.findById(memberId).get();
        Survey survey = surveyRepository.findById(surveyId).get();
        List<Question> questions = questionRepository.findBySurveyId(surveyId);

        for (Question question : questions) { // 질문 리스트 초기화
            List<Choice> choices = choiceRepository.findByQuestionId(question.getId());
            questionRepository.delete(question);

            if (!choices.isEmpty()) // 객관식 옵션 초기화
                choiceRepository.deleteAll(choices);
        }

        survey.update(dto, member);
        survey.setStatus(status);
        surveyRepository.save(survey);

        return setQuestion(dto, survey);
    }

    /**
     * 설문 삭제 메서드
     */
    public void deleteSurvey(Long surveyId) {
        surveyRepository.deleteById(surveyId);
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
}