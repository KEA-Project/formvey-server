package com.kale.formvey.controller.shortResult;

import com.kale.formvey.config.BaseException;
import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.service.shortResult.ShortResultService;
import com.kale.formvey.utils.JwtService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.kale.formvey.config.BaseResponseStatus.INVALID_USER_JWT;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/shortresults")
public class ShortResultController {

    private final ShortResultService shortResultService;
    private final JwtService jwtService;

    @ResponseBody
    @PostMapping("/{shortFormId}/{memberId}")
    @ApiOperation(value = "짧폼 해금", notes = "헤더에 jwt 필요(key: X-ACCESS-TOKEN, value: jwt 값)")
    @ApiImplicitParams({@ApiImplicitParam(name="shortFormId", value="응답한 짧폼 인덱스", required = true),
            @ApiImplicitParam(name="memberId", value="응답한 유저 인덱스", required = true)})
    @ApiResponses({
            @ApiResponse(code=2001, message="JWT를 입력해주세요."),
            @ApiResponse(code=2002, message="유효하지 않은 JWT입니다."),
            @ApiResponse(code=2003, message="권한이 없는 유저의 접근입니다."),
            @ApiResponse(code=4000, message="데이터베이스 연결에 실패하였습니다.")
    })
    private BaseResponse<String> responseShortResult(@PathVariable Long shortFormId, @PathVariable Long memberId) {
        try{
            //jwt에서 idx 추출.
            Long memberIdByJwt = jwtService.getUserIdx();
            //memberId와 접근한 유저가 같은지 확인
            if(memberId!= memberIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            shortResultService.responseShortResult(shortFormId, memberId);
            String result = "짧폼이 잠금 해제되었습니다.";

            return new BaseResponse<>(result);

        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
