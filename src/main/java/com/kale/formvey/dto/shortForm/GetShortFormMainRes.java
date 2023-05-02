package com.kale.formvey.dto.shortForm;

import com.kale.formvey.dto.shortOption.GetShortOptionRes;
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
public class GetShortFormMainRes {
    private Long surveyId;
    private String surveyTitle;
    private Long Id;
    private String shortQuestion;
    private int shortType;
    private List<GetShortOptionRes> options = new ArrayList<>();
}
