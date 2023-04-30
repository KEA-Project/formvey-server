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

    private List<PostShortOptionReq> shortOptions = new ArrayList<>();

    //-------------------------------------------------------------

    public static ShortForm toEntity(Survey survey, PostShortFormReq dto){
        return ShortForm.builder()
                .survey(survey)
                .shortQuestion(dto.shortQuestion)
                .build();
    }

}
