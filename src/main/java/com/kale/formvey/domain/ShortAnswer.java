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
        name = "SHORTANSWER_SEQ_GENERATOR"
        , sequenceName = "SHORTANSWER_SEQ"
        , initialValue = 1
        , allocationSize = 1
)
@Builder
@Getter
public class ShortAnswer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHORTANSWER_SEQ_GENERATOR")
    @Column(name = "shortanswer_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shortform_id")
    private ShortForm shortForm;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String shortAnswer;
}
