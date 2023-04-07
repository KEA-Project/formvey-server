package com.kale.formvey.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostMemberReq {
    private String email;

    private String nickName;

    private String password;

    private String phone;
}
