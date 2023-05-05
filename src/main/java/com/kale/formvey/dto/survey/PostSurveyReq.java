package com.kale.formvey.dto.survey;

import com.kale.formvey.domain.Member;
import com.kale.formvey.domain.Survey;
import com.kale.formvey.dto.question.PostQuestionReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostSurveyReq {
    private String surveyTitle;
    private String surveyContent;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private int responseCnt;
    private int isAnonymous; // 0 -> 익명x, 1 -> 익명 가능
    private int isPublic; // 0 -> 게시판 등록x, 1 -> 게시판 등록o
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
                .exitUrl(dto.exitUrl)
                .build();
    }
}
