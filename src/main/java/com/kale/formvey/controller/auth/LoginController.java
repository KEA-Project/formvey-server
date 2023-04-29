package com.kale.formvey.controller.auth;

import com.kale.formvey.config.BaseException;
import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.dto.auth.PostLoginReq;
import com.kale.formvey.dto.auth.PostLoginRes;
import com.kale.formvey.service.auth.AuthService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    @ApiResponses({
            @ApiResponse(code = 2014, message = "비밀번호가 일치하지 않습니다."),
            @ApiResponse(code = 2015, message = "존재하지 않는 이메일입니다."),
            @ApiResponse(code = 4000, message = "데이터베이스 연결에 실패하였습니다.")
    })
    private BaseResponse<PostLoginRes> emailLogin(@RequestBody PostLoginReq dto) {
        PostLoginRes postLoginRes = authService.emailLogin(dto);

        return new BaseResponse<>(postLoginRes);
    }

    /**
     * 카카오 로그인 및 가입 여부 확인(미가입 시 회원가입 자동 진행 후 로그인도 자동 진행됨)
     * [POST] /login/kakao/{access-token}
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/kakao/{access-token}")
    @ApiOperation("카카오 소셜 로그인")
    @ApiResponses({
            @ApiResponse(code = 4000, message = "데이터베이스 연결에 실패하였습니다.")
    })
    private BaseResponse<PostLoginRes> kakaoLogin(@PathVariable("access-token") String token) {
        PostLoginRes postLoginRes = authService.kakaoLogin(token);

        return new BaseResponse<>(postLoginRes);
    }
}
