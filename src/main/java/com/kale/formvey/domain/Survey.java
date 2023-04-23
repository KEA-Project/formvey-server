package com.kale.formvey.domain;

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

    private int rewardOption; // 0 -> 리워드 지정 x, 1 -> 리워드 랜덤 발송, 2 -> 리워드 지정 발송

    private String url;

    private String exitUrl;

    @OneToMany(mappedBy = "survey")
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "survey")
    private List<Response> responses = new ArrayList<>();

    @OneToMany(mappedBy = "survey")
    private List<ShortForm> shortforms = new ArrayList<>();

    public void updateStatus(int i) {
        setStatus(i);
    }
}
