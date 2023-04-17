package com.kale.formvey.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@SequenceGenerator(
        name = "ANSWER_SEQ_GENERATOR"
        , sequenceName = "ANSWER_SEQ"
        , initialValue = 1
        , allocationSize = 1
)
@Builder
@Getter
public class Answer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ANSWER_SEQ_GENERATOR")
    @Column(name = "answer_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "response_id")
    private Response response;

    private String answerContent;
}
