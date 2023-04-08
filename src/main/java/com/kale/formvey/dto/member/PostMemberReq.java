package com.kale.formvey.dto.member;

import com.kale.formvey.domain.member.Member;
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

    private String nickname;

    private String password;

    private String phone;

    public static Member toEntity(PostMemberReq dto){
        return Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .phone(dto.getPhone())
                .point(0)
                .build();
    }
}
