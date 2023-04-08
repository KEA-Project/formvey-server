package com.kale.formvey.controller.auth;

import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.dto.auth.PostLoginReq;
import com.kale.formvey.dto.auth.PostLoginRes;
import com.kale.formvey.dto.member.PostMemberRes;
import com.kale.formvey.repository.MemberRepository;
import com.kale.formvey.service.auth.AuthService;
import com.kale.formvey.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private final AuthService authService;

    @ResponseBody
    @PostMapping
    private BaseResponse<PostLoginRes> emailLogin(@RequestBody PostLoginReq dto) {
        PostLoginRes postLoginRes = authService.emailLogin(dto);
        return new BaseResponse<>(postLoginRes);
    }
}
