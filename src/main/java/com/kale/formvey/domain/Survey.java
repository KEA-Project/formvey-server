package com.kale.formvey.domain;

import com.kale.formvey.dto.member.PatchMemberReq;
import com.kale.formvey.dto.survey.PostSurveyReq;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
public class Survey extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String surveyTitle;

    private String surveyContent;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int responseCnt;

    private int isAnonymous; // 0 -> 익명x, 1 -> 익명 가능

    private int isPublic; // 0 -> 게시판 공개 x -> 1 ->

    private String exitUrl;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.REMOVE)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "survey", cascade = CascadeType.REMOVE)
    private List<Response> responses = new ArrayList<>();

    @OneToMany(mappedBy = "survey", cascade = CascadeType.REMOVE)
    private List<ShortForm> shortForms  = new ArrayList<>();

    public void update(PostSurveyReq dto, Member member) {
        this.surveyTitle = dto.getSurveyTitle();
        this.member = member;
        this.surveyContent = dto.getSurveyContent();
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
        this.responseCnt = 0;
        this.isAnonymous = dto.getIsAnonymous();
        this.isPublic = dto.getIsPublic();
        this.exitUrl = dto.getExitUrl();
    }

    public void increaseResponseCnt() {
        this.responseCnt++;
    }
}
