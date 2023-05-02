package com.kale.formvey.service.response;

import com.kale.formvey.domain.*;
import com.kale.formvey.dto.answer.PostAnswerReq;
import com.kale.formvey.dto.response.PostResponseReq;
import com.kale.formvey.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
