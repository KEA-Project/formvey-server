package com.kale.formvey.dto.response;

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
public class GetResponseStatisticsRes {
    Long questionId;

    int questionIdx;

    String questionTitle;

    List<MultipleChoiceInfo> multipleChoiceInfos;

    List<String> subjectiveAnswers = new ArrayList<>();
}


