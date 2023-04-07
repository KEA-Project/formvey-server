package com.kale.formvey.controller;

import com.kale.formvey.config.BaseException;
import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.dto.member.PostMemberReq;
import com.kale.formvey.dto.member.PostMemberRes;
import com.kale.formvey.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    @ResponseBody
    @PostMapping("/signup")
    private BaseResponse<PostMemberRes> emailSignup(@RequestBody PostMemberReq postMemberReq) {
        try {
            PostMemberRes postMemberRes = memberService.emailSignup(postMemberReq);
            return new BaseResponse<>(postMemberRes);
        } catch (BaseException e) {
            return new BaseResponse<>((e.getStatus()));
        }
    }
}
