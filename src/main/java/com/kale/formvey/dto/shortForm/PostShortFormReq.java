package com.kale.formvey.dto.shortForm;

import com.kale.formvey.domain.ShortForm;
import com.kale.formvey.domain.Survey;
import com.kale.formvey.dto.choice.PostChoiceReq;
import com.kale.formvey.dto.shortOption.PostShortOptionReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostShortFormReq {

    private String shortQuestion;

    private Long memberId;

    private int shortType;

    private int shortResponse;

    private List<PostChoiceReq> shortOptions = new ArrayList<>();
    //-------------------------------------------------------------

    public static ShortForm toEntity(Survey survey, PostShortFormReq dto){
        return ShortForm.builder()
                .survey(survey)
                .memberId(survey.getMember().getId())
                .shortType(dto.shortType)
                .shortResponse(0)
                .shortQuestion(dto.shortQuestion)
                .build();
    }
}
