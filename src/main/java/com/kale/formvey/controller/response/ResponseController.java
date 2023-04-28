package com.kale.formvey.controller.response;

import com.kale.formvey.config.BaseException;
import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.dto.response.PostResponseReq;
import com.kale.formvey.service.response.ResponseService;
import com.kale.formvey.utils.JwtService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.kale.formvey.config.BaseResponseStatus.INVALID_USER_JWT;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/responses")
public class ResponseController {
    private final ResponseService responseService;
    private final JwtService jwtService;

    /**
     * 설문 응답
     * [POST] /responses/{surveyId}/{memberId}
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PostMapping("/{surveyId}/{memberId}")
    @ApiOperation(value = "설문 응답", notes = "헤더에 jwt 필요(key: X-ACCESS-TOKEN, value: jwt 값)")
    @ApiImplicitParams({@ApiImplicitParam(name="surveyId", value="응답한 설문 인덱스", required = true),
            @ApiImplicitParam(name="memberId", value="응답한 유저 인덱스", required = true)})
    @ApiResponses({
            @ApiResponse(code=2001, message="JWT를 입력해주세요."),
            @ApiResponse(code=2002, message="유효하지 않은 JWT입니다."),
            @ApiResponse(code=2003, message="권한이 없는 유저의 접근입니다."),
            @ApiResponse(code=4000, message="데이터베이스 연결에 실패하였습니다.")
    })
    private BaseResponse<String> responseSurvey(@RequestBody PostResponseReq dto,
                                                         @PathVariable Long surveyId, @PathVariable Long memberId) {
        try{
            //jwt에서 idx 추출.
            Long memberIdByJwt = jwtService.getUserIdx();
            //memberId와 접근한 유저가 같은지 확인
            if(memberId!= memberIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            responseService.responseSurvey(dto, surveyId, memberId);
            String result = "응답이 등록되었습니다";

            return new BaseResponse<>(result);

        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}

