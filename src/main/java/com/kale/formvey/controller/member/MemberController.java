package com.kale.formvey.controller.member;

import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.dto.member.GetMemberRes;
import com.kale.formvey.dto.member.PatchMemberReq;
import com.kale.formvey.dto.member.PostMemberReq;
import com.kale.formvey.dto.member.PostMemberRes;
import com.kale.formvey.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;
    @ResponseBody
    @PostMapping("/members/signup")
    private BaseResponse<PostMemberRes> emailSignup(@RequestBody PostMemberReq dto) {
        PostMemberRes postMemberRes = memberService.emailSignup(dto);
        return new BaseResponse<>(postMemberRes);
    }
    @GetMapping("/members/info/{memberId}")
    private BaseResponse<GetMemberRes> getMemberInfo(@PathVariable Long memberId) {
        GetMemberRes getMemberRes = memberService.getMemberInfo(memberId);
        return new BaseResponse<>(getMemberRes);
    }
    @ResponseBody
    @PatchMapping("/members/edit/{memberId}")
    private BaseResponse<String> editProfile(@RequestBody PatchMemberReq dto, @PathVariable Long memberId) {
        memberService.editProfile(memberId, dto);
        String result = "프로필 수정이 완료되었습니다";
        return new BaseResponse<>(result);
    }
}
