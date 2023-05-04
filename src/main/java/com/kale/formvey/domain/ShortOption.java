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
        name = "SHORTOPTION_SEQ_GENERATOR"
        , sequenceName = "SHORTOPTION_SEQ"
        , initialValue = 1
        , allocationSize = 1
)
@Builder
@Getter
public class ShortOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHORTOPTION_SEQ_GENERATOR")
    @Column(name = "shortoption_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shortform_id")
    private ShortForm shortForm;

    private int shortIndex;

    private String shortContent;
}
