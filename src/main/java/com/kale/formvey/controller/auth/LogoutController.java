package com.kale.formvey.controller.auth;

import com.kale.formvey.config.BaseException;
import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.dto.auth.PostLoginReq;
import com.kale.formvey.dto.auth.PostLoginRes;
import com.kale.formvey.service.auth.AuthService;
import com.kale.formvey.utils.JwtService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.kale.formvey.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/logout")
public class LogoutController {
    private final AuthService authService;

    private final JwtService jwtService;

    /**
     * 로그아웃
     * [PATCH] /logout/email
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{member_id}")
    @ApiOperation(value = "로그아웃", notes = "헤더에 jwt 필요(key: X-ACCESS-TOKEN, value: jwt 값)")
    private BaseResponse<String> emailLogin(@PathVariable("member_id") Long memberId) {
        try {
            //jwt에서 idx 추출.
            Long userIdxByJwt = jwtService.getUserIdx();
            //memberIdx와 접근한 유저가 같은지 확인
            if(memberId!= userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            authService.logOut(memberId);
            String result = "회원 로그아웃을 완료했습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


}
