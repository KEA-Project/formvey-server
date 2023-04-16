package com.kale.formvey.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@SequenceGenerator(
        name = "CHOICE_SEQ_GENERATOR"
        , sequenceName = "CHOICE_SEQ"
        , initialValue = 1
        , allocationSize = 1
)
@Builder
@Getter
public class Choice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHOICE_SEQ_GENERATOR")
    @Column(name = "choice_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private int choiceIndex;

    private String choiceContent;
}
