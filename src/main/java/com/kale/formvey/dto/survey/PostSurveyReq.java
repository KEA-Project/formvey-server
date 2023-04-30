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
    private int isAnonymous; // 0 -> 익명x, 1 -> 익명 가능
    private int isPublic; // 0 -> 게시판 등록x, 1 -> 게시판 등록o
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
                .isPublic(dto.isPublic)
                .url(dto.url)
                .exitUrl(dto.exitUrl)
                .build();
    }
    public boolean isUrlNull(){
        return this.getUrl() == null;
    }
}
