package com.kale.formvey.service.response;

import com.kale.formvey.config.BaseException;
import com.kale.formvey.domain.*;
import com.kale.formvey.dto.answer.PostAnswerReq;
import com.kale.formvey.dto.response.PostResponseReq;
import com.kale.formvey.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.kale.formvey.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final MemberRepository memberRepository;
    private final SurveyRepository surveyRepository;
    private final ResponseRepository responseRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    /**
     * 설문 응답
     */
    public void responseSurvey(PostResponseReq dto, Long surveyId, Long memberId) throws BaseException{
        try{
            Member member = memberRepository.findById(memberId).get(); // 설문 응답자
            Survey survey = surveyRepository.findById(surveyId).get(); // 응답하고자 하는 설문
            //응답 등록
            Response response = PostResponseReq.toEntity(member, survey, dto);
            response = responseRepository.save(response);

            //답변 등록
            List<Question> questions = questionRepository.findBySurveyId(surveyId);
            for (int i = 0; i < questions.size(); i++) {
                answerRepository.save(PostAnswerReq.toEntity(questions.get(i), response, dto.getContents().get(i)));
            }
            
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
