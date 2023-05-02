package com.kale.formvey.dto.response;

import com.kale.formvey.domain.Choice;
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
    int questionIdx;

    String questionTitle;

    int type;

    int[] answerCount;

    List<String> answerContent = new ArrayList<>();
}


