package com.kale.formvey.domain;

import com.kale.formvey.dto.member.PostMemberReq;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column
    private String email;

    @Column(name = "nickname")
    private String nickName;

    @Column
    private String password;

    @Column
    private int point;

    @Column
    private String phone;
}
