package com.kale.formvey.dto.answer;

import com.kale.formvey.domain.Answer;
import com.kale.formvey.domain.Question;
import com.kale.formvey.domain.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostAnswerReq {

    private Long questionId;

    private String content;

    public static Answer toEntity(Question question, Response response, String content){
        return Answer.builder()
                .question(question)
                .response(response)
                .answerContent(content)
                .build();
    }
}
