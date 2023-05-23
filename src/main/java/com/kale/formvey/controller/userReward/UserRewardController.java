package com.kale.formvey.controller.userReward;

import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.dto.response.*;
import com.kale.formvey.dto.userReward.GetUserRewardListRes;
import com.kale.formvey.dto.userReward.PostRandomRewardReq;
import com.kale.formvey.dto.userReward.PostSelectRewardReq;
import com.kale.formvey.service.response.ResponseService;
import com.kale.formvey.service.userReward.UserRewardService;
import com.kale.formvey.utils.JwtService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kale.formvey.config.BaseResponseStatus.INVALID_USER_JWT;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rewards")
public class UserRewardController {
    private final UserRewardService userRewardService;
    private final JwtService jwtService;

    /**
     * 랜덤 발송
     * [POST] /rewards/random/{surveyId}
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PostMapping("/random/{surveyId}")
    @ApiOperation(value = "랜덤 발송", notes = "헤더에 jwt 필요(key: X-ACCESS-TOKEN, value: jwt 값)")
    @ApiImplicitParam(name = "surveyId", value = "제작한 설문 인덱스", required = true)
    @ApiResponses({
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2003, message = "권한이 없는 유저의 접근입니다.")
    })
    private BaseResponse<String> randomReward(@RequestBody PostRandomRewardReq dto, @PathVariable Long surveyId) {
        //jwt에서 idx 추출.
        Long memberIdByJwt = jwtService.getUserIdx();
        userRewardService.randomReward(dto, surveyId, memberIdByJwt);

        String result = "리워드가 전송되었습니다.";
        return new BaseResponse<>(result);
    }

    /**
     * 지정 발송
     * [POST] /rewards/select
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PostMapping("/select")
    @ApiOperation(value = "지정 발송", notes = "헤더에 jwt 필요(key: X-ACCESS-TOKEN, value: jwt 값)")
    @ApiResponses({
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2003, message = "권한이 없는 유저의 접근입니다.")
    })
    private BaseResponse<String> selectReward(@RequestBody PostSelectRewardReq dto) {
        //jwt에서 idx 추출.
        Long memberIdByJwt = jwtService.getUserIdx();
        userRewardService.selectReward(dto, memberIdByJwt);

        String result = "리워드가 전송되었습니다.";
        return new BaseResponse<>(result);
    }

    /**
     * 리워드 조회
     * [GET] /rewards/{memberId}
     * @return BaseResponse<List<GetUserRewardListRes>>
     */
    @ResponseBody
    @GetMapping("/{memberId}")
    @ApiOperation(value = "리워드 보관함", notes = "헤더에 jwt 필요(key: X-ACCESS-TOKEN, value: jwt 값)")
    @ApiImplicitParam(name = "memberId", value = "멤버 인덱스", required = true)
    @ApiResponses({
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2003, message = "권한이 없는 유저의 접근입니다.")
    })
    private BaseResponse<List<GetUserRewardListRes>> myReward(@PathVariable Long memberId) {
        //jwt에서 idx 추출.
        Long memberIdByJwt = jwtService.getUserIdx();
        //memberId와 접근한 유저가 같은지 확인
        if (memberId != memberIdByJwt) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        List<GetUserRewardListRes> getUserRewardListRes=userRewardService.myReward(memberId);

        return new BaseResponse<>(getUserRewardListRes);
    }


}

