package com.kale.formvey.dto.userReward;

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
public class GetUserRewardListRes {
    private Long userRewardId;
    private String rewardUrl;

}
