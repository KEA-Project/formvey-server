package com.kale.formvey.service.response;

import com.kale.formvey.domain.*;
import com.kale.formvey.dto.answer.PostAnswerReq;
import com.kale.formvey.dto.response.PostResponseReq;
import com.kale.formvey.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

}
