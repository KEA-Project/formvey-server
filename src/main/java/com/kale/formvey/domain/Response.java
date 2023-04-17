package com.kale.formvey.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@SequenceGenerator(
        name = "RESPONSE_SEQ_GENERATOR"
        , sequenceName = "RESPONSE_SEQ"
        , initialValue = 1
        , allocationSize = 1
)
@Builder
@Getter
public class Response extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESPONSE_SEQ_GENERATOR")
    @Column(name = "response_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    private LocalDate responseDate;

    @OneToMany(mappedBy = "response")
    private List<Answer> answers = new ArrayList<>();
}