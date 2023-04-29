package com.kale.formvey.controller.auth;

import com.kale.formvey.config.BaseException;
import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.dto.auth.PostLoginReq;
import com.kale.formvey.dto.auth.PostLoginRes;
import com.kale.formvey.service.auth.AuthService;
import com.kale.formvey.utils.JwtService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.kale.formvey.config.BaseResponseStatus.INVALID_USER_JWT;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/logout")
public class LogoutController {
    private final AuthService authService;
    private final JwtService jwtService;

    /**
     * 로그아웃
     * [PATCH] /logout/{memberId}
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{memberId}")
    @ApiOperation(value = "로그아웃", notes = "헤더에 jwt 필요(key: X-ACCESS-TOKEN, value: jwt 값)")
    @ApiImplicitParam(name = "memberId", value = "로그아웃할 유저 인덱스", required = true)
    @ApiResponses({
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2003, message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code = 2010, message = "유저 아이디 값을 확인해주세요."),
            @ApiResponse(code = 4000, message = "데이터베이스 연결에 실패하였습니다.")
    })
    private BaseResponse<String> emailLogin(@PathVariable("memberId") Long memberId) {
        //jwt에서 idx 추출.
        Long memberIdByJwt = jwtService.getUserIdx();
        //memberId와 접근한 유저가 같은지 확인
        if (memberId != memberIdByJwt) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        authService.logOut(memberId);
        String result = "회원 로그아웃을 완료했습니다.";

        return new BaseResponse<>(result);
    }
}
