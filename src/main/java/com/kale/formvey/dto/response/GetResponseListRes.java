package com.kale.formvey.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetResponseListRes {
    private Long surveyId;
    private Long responseId;
    private String surveyTitle;
    private String surveyContent;
    private String endDate;
    private int dDay;
    private int status;
    private int pages;
}
