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
        name = "SHORTRESULT_SEQ_GENERATOR"
        , sequenceName = "SHORTRESULT_SEQ"
        , initialValue = 1
        , allocationSize = 1
)
@Builder
@Getter
public class ShortResult extends BaseEntity {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE, generator = "SHORTRESULT_SEQ_GENERATOR")
    @Column(name = "shortresult_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shortform_id")
    private ShortForm shortForm;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
