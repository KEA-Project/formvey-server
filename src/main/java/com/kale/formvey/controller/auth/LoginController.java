package com.kale.formvey.controller.auth;

import com.kale.formvey.config.BaseException;
import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.dto.auth.PostLoginReq;
import com.kale.formvey.dto.auth.PostLoginRes;
import com.kale.formvey.service.auth.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
    private final AuthService authService;

    /**
     * 이메일 로그인 및 가입 여부 확인
     * [POST] /login/email
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/email")
    @ApiOperation("이메일 로그인")
    private BaseResponse<PostLoginRes> emailLogin(@RequestBody PostLoginReq dto) {
        try {
            PostLoginRes postLoginRes = authService.emailLogin(dto);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 카카오 로그인 및 가입 여부 확인(미가입 시 회원가입 자동 진행)
     * [POST] /login/kakao/{access-token}
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/kakao/{access-token}")
    @ApiOperation("카카오 로그인")
    private BaseResponse<PostLoginRes> kakaoLogin(@PathVariable("access-token") String token) {
        try {
            PostLoginRes postLoginRes = authService.kakaoLogin(token);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
