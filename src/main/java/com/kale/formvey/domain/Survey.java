package com.kale.formvey.domain;

import com.kale.formvey.dto.member.PatchMemberReq;
import com.kale.formvey.dto.survey.PostSurveyReq;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@SequenceGenerator(
        name = "SURVEY_SEQ_GENERATOR"
        , sequenceName = "SURVEY_SEQ"
        , initialValue = 1
        , allocationSize = 1
)
@Builder
@Getter
public class Survey extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SURVEY_SEQ_GENERATOR")
    @Column(name = "survey_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String surveyTitle;

    private String surveyContent;

    private LocalDate startDate;

    private LocalDate endDate;

    private int responseCnt;

    private int isAnonymous; // 0 -> 익명x, 1 -> 익명 가능

    private int isPublic; // 0 -> 게시판 공개 x -> 1 ->

    private int rewardOption; // 0 -> 리워드 지정 x, 1 -> 리워드 랜덤 발송, 2 -> 리워드 지정 발송

    private String url;

    private String exitUrl;

    @OneToMany(mappedBy = "survey")
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "survey")
    private List<Response> responses = new ArrayList<>();

    public void update(PostSurveyReq dto, Member member) {
        this.surveyTitle = dto.getSurveyTitle();
        this.member = member;
        this.surveyContent = dto.getSurveyContent();
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
        this.responseCnt = 0;
        this.isAnonymous = dto.getIsAnonymous();
        this.isPublic = dto.getIsPublic();
        this.rewardOption = dto.getRewardOption();
        this.url = dto.getUrl();
        this.exitUrl = dto.getExitUrl();
    }
}
