package com.kale.formvey.dto.userReward;

import com.kale.formvey.domain.Member;
import com.kale.formvey.domain.Response;
import com.kale.formvey.domain.Survey;
import com.kale.formvey.domain.UserReward;
import com.kale.formvey.dto.response.PostResponseReq;
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
public class SelectReward {
    private Long memberId;
    private String rewardUrl;

    public static UserReward toEntity(Member member,String rewardUrl){
        return UserReward.builder()
                .member(member)
                .rewardImage(rewardUrl)
                .build();
    }
}
