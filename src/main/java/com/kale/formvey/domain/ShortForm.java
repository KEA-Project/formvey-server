package com.kale.formvey.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@SequenceGenerator(
        name = "SHORTFORM_SEQ_GENERATOR"
        , sequenceName = "SHORTFORM_SEQ"
        , initialValue = 1
        , allocationSize = 1
)
@Builder
@Getter
public class ShortForm extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHORTFORM_SEQ_GENERATOR")
    @Column(name = "shortform_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    private String shortQuestion;
    private int shortType;

    private int shortResponse;

    @OneToMany(mappedBy = "shortForm")
    private List<ShortOption> shortOptions = new ArrayList<>();

    @OneToMany(mappedBy = "shortForm")
    private List<ShortAnswer> shortAnswer = new ArrayList<>();


}
