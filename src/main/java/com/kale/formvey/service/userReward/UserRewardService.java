package com.kale.formvey.service.userReward;

import com.kale.formvey.config.BaseException;
import com.kale.formvey.domain.Member;
import com.kale.formvey.domain.Response;
import com.kale.formvey.domain.UserReward;
import com.kale.formvey.dto.member.GetMemberRes;
import com.kale.formvey.dto.member.PatchMemberReq;
import com.kale.formvey.dto.member.PostMemberReq;
import com.kale.formvey.dto.member.PostMemberRes;
import com.kale.formvey.dto.userReward.GetUserRewardListRes;
import com.kale.formvey.dto.userReward.PostRandomRewardReq;
import com.kale.formvey.dto.userReward.PostSelectRewardReq;
import com.kale.formvey.dto.userReward.SelectReward;
import com.kale.formvey.repository.MemberRepository;
import com.kale.formvey.repository.ResponseRepository;
import com.kale.formvey.repository.UserRewardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.kale.formvey.config.BaseResponseStatus.*;
import static com.kale.formvey.utils.ValidationRegex.isRegexEmail;

@Service
@RequiredArgsConstructor
@Transactional
public class UserRewardService {
    private final MemberRepository memberRepository;
    private final UserRewardRepository userRewardRepository;
    private final ResponseRepository responseRepository;


    /**
     * 랜덤 발송
     */
    public void randomReward(PostRandomRewardReq dto, Long surveyId, Long memberId) {
        List<Response> responses=responseRepository.findBySurveyId(surveyId);

        //리워드 랜덤 매핑
        for(int i=0;i< dto.getRewardUrl().size();i++){
            int index=(int) (Math.random()*responses.size());


            Member member=responses.get(index).getMember();
            UserReward userReward= SelectReward.toEntity(member,dto.getRewardUrl().get(i));
            userRewardRepository.save(userReward);
        }
    }

    /**
     * 지정 발송
     */
    public void selectReward(PostSelectRewardReq dto, Long memberId) {

        //리워드 지정 발송
        for(int i=0; i<dto.getRewards().size();i++){
            Member member=memberRepository.findById(dto.getRewards().get(i).getMemberId()).get();
            UserReward userReward= SelectReward.toEntity(member,dto.getRewards().get(i).getRewardUrl());
            userRewardRepository.save(userReward);
        }
    }

    /**
     * 리워드 조회
     */
    public List<GetUserRewardListRes> myReward(Long memberId) {
        List<GetUserRewardListRes> getUserRewardListRes=new ArrayList<>();
        List<UserReward> userRewards=userRewardRepository.findByMemberId(memberId);

        for(UserReward userReward : userRewards){
            getUserRewardListRes.add(new GetUserRewardListRes(userReward.getId(), userReward.getRewardImage()));
        }
        return getUserRewardListRes;
    }

}
