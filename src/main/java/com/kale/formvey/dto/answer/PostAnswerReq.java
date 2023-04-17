package com.kale.formvey.dto.answer;

import com.kale.formvey.domain.Answer;
import com.kale.formvey.domain.Question;
import com.kale.formvey.domain.Response;

public class PostAnswerReq {
    public static Answer toEntity(Question question, Response response, String content){
        return Answer.builder()
                .question(question)
                .response(response)
                .answerContent(content)
                .build();
    }
}
