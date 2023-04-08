package com.kale.formvey.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetMemberRes {
    private Long id;

    private String email;

    private String nickname;

    private int point;

    private String phone;

}
