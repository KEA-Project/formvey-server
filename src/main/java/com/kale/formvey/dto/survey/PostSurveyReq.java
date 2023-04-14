package com.kale.formvey.dto.survey;

import com.kale.formvey.domain.Member;
import com.kale.formvey.domain.Question;
import com.kale.formvey.domain.Survey;
import com.kale.formvey.dto.member.PostMemberReq;
import com.kale.formvey.dto.question.PostQuestionReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostSurveyReq {
    private String surveyTitle;
    private String surveyContent;
    private LocalDate startDate;
    private LocalDate endDate;

    private int responseCnt;
    private boolean isAnonymous; // 0 -> 익명x, 1 -> 익명 가능

    private boolean status; // 0 -> 임시저장, 1 -> 배포 완료
    private int rewardOption; // 0 -> 리워드 지정 x, 1 -> 리워드 랜덤 발송, 2 -> 리워드 지정 발송
    private String url;
    private String exitUrl;

    private List<PostQuestionReq> questions = new ArrayList<>();

    //-------------------------------------------------------------
    public static Survey toEntity(Member member, PostSurveyReq dto){
        return Survey.builder()
                .member(member)
                .surveyTitle(dto.surveyTitle)
                .surveyContent(dto.surveyContent)
                .startDate(dto.startDate)
                .endDate(dto.endDate)
                .responseCnt(0)
                .isAnonymous(dto.isAnonymous)
                .rewardOption(dto.rewardOption)
                .url(dto.url)
                .exitUrl(dto.exitUrl)
                .build();
    }
}
