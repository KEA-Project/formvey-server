package com.kale.formvey.service.survey;

import com.kale.formvey.config.BaseException;
import com.kale.formvey.domain.Choice;
import com.kale.formvey.domain.Member;
import com.kale.formvey.domain.Question;
import com.kale.formvey.domain.Survey;
import com.kale.formvey.dto.choice.PostChoiceReq;
import com.kale.formvey.dto.question.PostQuestionReq;
import com.kale.formvey.dto.survey.GetSurveyInfoRes;
import com.kale.formvey.dto.survey.GetSurveyListRes;
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
import java.util.Optional;

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
     * 설문 삭제
     */
    public void deleteSurvey(Long surveyId) throws BaseException {
        //설문과 관련된 모든 것 삭제 필요
        try {
            //status 0으로 변경
            Survey survey = surveyRepository.findById(surveyId).get();
            survey.setStatus(0);
            surveyRepository.save(survey);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
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
     * 제작 설문 리스트 조회
     */
    public List<GetSurveyListRes> getSurveyList(Long memberId) throws BaseException {
        try {
            List<GetSurveyListRes> findSurveys = surveyRepository.findSurveyByMember(memberId);

            return findSurveys;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 설문 내용 조회
     */
    public GetSurveyInfoRes getSurveyInfo(Long memberId, Long surveyId) throws BaseException{
        try{
            // 해당 설문 id가 존재하지 않을 때
            if (surveyRepository.findById(surveyId).isEmpty())
                throw new BaseException(SURVEYS_EMPTY_SURVEY_ID);

            GetSurveyInfoRes getSurveyInfoRes=new GetSurveyInfoRes();

            return  getSurveyInfoRes;

        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}