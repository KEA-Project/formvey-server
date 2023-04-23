package com.kale.formvey.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@SequenceGenerator(
        name = "QUESTION_SEQ_GENERATOR"
        , sequenceName = "QUESTION_SEQ"
        , initialValue = 1
        , allocationSize = 1
)
@Builder
@Getter
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QUESTION_SEQ_GENERATOR")
    @Column(name = "question_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    private int questionIdx;

    private String questionTitle;

    private int type;

    private int isEssential;

    private int isShort;

    @OneToMany(mappedBy = "question")
    private List<Choice> choices = new ArrayList<>();

    @OneToOne(mappedBy = "question")
    private Answer answer;
}
