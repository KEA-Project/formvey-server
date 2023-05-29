package com.kale.formvey.dto.shortAnswer;

import com.kale.formvey.domain.Member;
import com.kale.formvey.domain.ShortAnswer;
import com.kale.formvey.domain.ShortForm;
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
public class PostShortAnswerReq {

//    private String shortAnswer;
    private List<String> shortAnswer = new ArrayList<>();

    private int point;

    public static ShortAnswer toEntity(Member member, ShortForm shortForm, PostShortAnswerReq dto){
        return ShortAnswer.builder()
                .member(member)
                .shortForm(shortForm)
                .shortAnswer(dto.shortAnswer.toString())
                .build();
    }
}
