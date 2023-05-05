package com.kale.formvey.dto.choice;

import com.kale.formvey.domain.Choice;
import com.kale.formvey.domain.Member;
import com.kale.formvey.domain.Question;
import com.kale.formvey.dto.member.PostMemberReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostChoiceReq {
    private int choiceIdx;

    private String choiceContent;

    public static Choice toEntity(Question question, PostChoiceReq dto){
        return Choice.builder()
                .question(question)
                .choiceIndex(dto.choiceIdx)
                .choiceContent(dto.choiceContent)
                .build();
    }
}
