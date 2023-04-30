package com.kale.formvey.dto.survey;

import com.kale.formvey.dto.question.PostQuestionReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetSurveyInfoRes {
    private Long memberId;
    private String surveyTitle;
    private String surveyContent;
    private LocalDate startDate;
    private LocalDate endDate;
    private int responseCnt;
    private int isAnonymous; // 0 -> 익명x, 1 -> 익명 가능
    private String url;
    private String exitUrl;
    private int status;
    private int responded; // 0 -> 응답 안한 설문, 1 -> 응답한 설문
    private List<PostQuestionReq> questions = new ArrayList<>();
}
