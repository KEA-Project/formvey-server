package com.kale.formvey.dto.survey;

import com.kale.formvey.domain.Member;
import com.kale.formvey.domain.Survey;
import com.kale.formvey.dto.question.PostQuestionReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetSurveyListRes {
    private Long surveyId;
    private String surveyTitle;
    private String surveyContent;
    private String endDate;
    private int dDay;
    private int responseCnt;
    private int status;
    private int pages;
}
