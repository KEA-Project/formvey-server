package com.kale.formvey.dto.question;

import com.kale.formvey.domain.Member;
import com.kale.formvey.domain.Question;
import com.kale.formvey.domain.Survey;
import com.kale.formvey.dto.choice.PostChoiceReq;
import com.kale.formvey.dto.member.PostMemberReq;
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
public class PostQuestionReq {
    private int questionIdx;

    private String questionTitle;

    private int type;

    private List<PostChoiceReq> choices = new ArrayList<>();

    private boolean isEssential;

    private boolean isShort;

    public static Question toEntity(Survey survey, PostQuestionReq dto){
        return Question.builder()
                .survey(survey)
                .questionIdx(dto.questionIdx)
                .questionTitle(dto.questionTitle)
                .isShort(dto.isShort)
                .isEssential(dto.isEssential)
                .build();
    }
}
