package com.kale.formvey.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    private int questionIdx;

    private String questionTitle;

    private int type;

    private boolean isEssential;

    private boolean isShort;

    @OneToMany(mappedBy = "question")
    private List<Choice> choices = new ArrayList<>();

    @OneToOne(mappedBy = "question")
    private Answer answer;
}
