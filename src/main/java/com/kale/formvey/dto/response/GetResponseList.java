package com.kale.formvey.dto.response;

import com.kale.formvey.dto.survey.GetSurveyListRes;
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
public class GetResponseList {
    private List<GetResponseListRes> getResponseListRes = new ArrayList<>();

    private int totalPageCnt;

    private int releasedPageCnt;

    private int closedPageCnt;
}
