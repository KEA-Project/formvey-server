package com.kale.formvey.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
public class ShortResult extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shortresult_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shortform_id")
    private ShortForm shortForm;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
