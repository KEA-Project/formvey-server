package com.kale.formvey.dto.question;

import com.kale.formvey.domain.Question;
import com.kale.formvey.domain.Survey;
import com.kale.formvey.dto.choice.GetChoiceInfoRes;
import com.kale.formvey.dto.choice.PostChoiceReq;
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
public class GetQuestionInfoRes {

    private Long surveyId;

    private int questionIdx;

    private String questionTitle;

    private int type;

    private int isEssential;

    private int isShort;

    private List<GetChoiceInfoRes> choices = new ArrayList<>();

}
