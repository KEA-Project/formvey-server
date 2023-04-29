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
     * 설문 생성
     */
    public PostSurveyRes createSurvey(Long memberId, PostSurveyReq dto) throws BaseException {
        try {
            //설문 생성
            Member member = memberRepository.findById(memberId).get();
            Survey survey = PostSurveyReq.toEntity(member, dto);

            //배포가 된 설문인 경우
            if (!dto.getUrl().isEmpty()) survey.updateStatus(2);
            survey = surveyRepository.save(survey);

            //질문 생성
            for (PostQuestionReq postQuestionReq : dto.getQuestions()) {
                Question question = PostQuestionReq.toEntity(survey, postQuestionReq);
                question = questionRepository.save(question);

                //질문 옵션 생성
                // 주관식이 아닌 객관식, 찬부식인 경우
                if (!postQuestionReq.getChoices().isEmpty()) {
                    for (PostChoiceReq postChoiceReq : postQuestionReq.getChoices()) {
                        Choice choice = PostChoiceReq.toEntity(question, postChoiceReq);
                        choiceRepository.save(choice);
                    }
                }
            }
            return new PostSurveyRes(survey.getId());

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
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
    public GetSurveyInfoRes getSurveyInfo(Long surveyId) throws BaseException{
        try{
            // 해당 설문 id가 존재하지 않을 때
            if (surveyRepository.findById(surveyId).isEmpty())
                throw new BaseException(SURVEYS_EMPTY_SURVEY_ID);

            GetSurveyInfoRes getSurveyInfoRes=surveyRepository.findSurveyById(surveyId);

            return  getSurveyInfoRes;

        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
