package com.kale.formvey.dto.response;

import com.kale.formvey.dto.answer.GetAnswerRes;
import com.kale.formvey.dto.question.GetQuestionInfoRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetResponseInfoRes {
    private Long surveyId;
    private String surveyTitle;
    private String surveyContent;
    private String startDate;
    private String endDate;
    private int isAnonymous; // 0 -> 익명x, 1 -> 익명 가능
    private int status;
    private List<GetQuestionInfoRes> questions = new ArrayList<>();

    private List<GetAnswerRes> answers=new ArrayList<>();
}
