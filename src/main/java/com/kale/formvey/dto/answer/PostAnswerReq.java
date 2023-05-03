package com.kale.formvey.dto.answer;

import com.kale.formvey.domain.Answer;
import com.kale.formvey.domain.Question;
import com.kale.formvey.domain.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostAnswerReq {

    private Long questionId;

    private List<String> content=new ArrayList<>();

    public static Answer toEntity(Question question, Response response, PostAnswerReq dto){
        return Answer.builder()
                .question(question)
                .response(response)
                .answerContent(dto.content.toString())
                .build();
    }
}
