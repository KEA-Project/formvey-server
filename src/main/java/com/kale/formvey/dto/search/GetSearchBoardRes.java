package com.kale.formvey.dto.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetSearchBoardRes {
    private Long Id;
    private String surveyTitle;
    private int dDay;
    private int responseCnt;
    private String nickname;
}
