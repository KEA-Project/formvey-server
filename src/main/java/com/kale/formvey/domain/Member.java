package com.kale.formvey.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    private String nickName;
    private String password;
    private String ageRange;
    private String gender;
    private int point;
}
