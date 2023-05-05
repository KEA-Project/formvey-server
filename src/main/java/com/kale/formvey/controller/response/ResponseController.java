package com.kale.formvey.controller.response;

import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.dto.response.*;
import com.kale.formvey.dto.survey.DeleteSurveyReq;
import com.kale.formvey.dto.response.PostResponseReq;
import com.kale.formvey.dto.survey.GetSurveyBoardRes;
import com.kale.formvey.dto.survey.GetSurveyInfoRes;
import com.kale.formvey.service.response.ResponseService;
import com.kale.formvey.utils.JwtService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * [POST] /responses/{surveyId}
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PostMapping("/{surveyId}")
    @ApiOperation(value = "설문 응답", notes = "헤더에 jwt 필요(key: X-ACCESS-TOKEN, value: jwt 값)")
    @ApiImplicitParam(name = "surveyId", value = "응답할 설문 인덱스", required = true)
    @ApiResponses({
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2003, message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code = 4000, message = "데이터베이스 연결에 실패하였습니다.")
    })
    private BaseResponse<String> responseSurvey(@RequestBody PostResponseReq dto, @PathVariable Long surveyId) {
        //jwt에서 idx 추출.
        Long memberIdByJwt = jwtService.getUserIdx();
        //memberId와 접근한 유저가 같은지 확인
        if (dto.getMemberId() != memberIdByJwt) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }

        responseService.responseSurvey(dto, surveyId, memberIdByJwt);
        String result = "응답이 등록되었습니다";

        return new BaseResponse<>(result);
    }

    /**
     * 설문 응답 수정
     * [PUT] /responses/update/{surveyId}/{responseId}
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PutMapping("/update/{surveyId}/{responseId}")
    @ApiOperation(value = "설문 응답 수정", notes = "헤더에 jwt 필요(key: X-ACCESS-TOKEN, value: jwt 값)")
    @ApiImplicitParams({@ApiImplicitParam(name = "surveyId", value = "수정할 설문 인덱스", required = true),
            @ApiImplicitParam(name = "responseId", value = "수정할 응답 인덱스", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2003, message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code = 4000, message = "데이터베이스 연결에 실패하였습니다.")
    })
    private BaseResponse<String> updateResponse(@RequestBody PostResponseReq dto, @PathVariable Long surveyId,@PathVariable Long responseId) {
        //jwt에서 idx 추출.
        Long memberIdByJwt = jwtService.getUserIdx();
        //memberId와 접근한 유저가 같은지 확인
        if (dto.getMemberId() != memberIdByJwt) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }

        responseService.updateResponse(dto,surveyId,responseId);
        String result = "응답이 수정되었습니다";

        return new BaseResponse<>(result);
    }

    /**
     * 응답 삭제
     * [PATCH] /responses/delete/{responseId}
     * @return BaseResponse<String>
     */
    @PatchMapping("/delete/{responseId}")
    @ApiOperation(value = "응답 삭제", notes = "헤더에 jwt 필요(key: X-ACCESS-TOKEN, value: jwt 값)")
    @ApiImplicitParam(name = "responseId", value = "삭제할 응답 인덱스", required = true)
    @ApiResponses({
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2003, message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code = 4000, message = "데이터베이스 연결에 실패하였습니다.")
    })
    public BaseResponse<String> deleteResponse(@PathVariable Long responseId, @RequestBody DeleteResponseReq deleteResponseReq) {
        //jwt에서 idx 추출.
        Long memberIdByJwt = jwtService.getUserIdx();
        //memberId와 접근한 유저가 같은지 확인
        if (deleteResponseReq.getMemberId() != memberIdByJwt) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        responseService.deleteResponse(responseId);
        String result = "응답이 삭제되었습니다.";

        return new BaseResponse<>(result);
    }


    /**
     * 응답 설문 리스트 조회
     * [GET] /responses/list/{memberId}
     * @return BaseResponse<List<GetResponseListRes>>
     */
    @ResponseBody
    @GetMapping("/list/{memberId}")
    @ApiOperation(value = "응답 설문 리스트 조회", notes = "헤더에 jwt 필요(key: X-ACCESS-TOKEN, value: jwt 값)")
    @ApiImplicitParam(name = "memberId", value = "응답한 유저의 인덱스", required = true)
    @ApiResponses({
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2003, message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code = 4000, message = "데이터베이스 연결에 실패하였습니다.")
    })
    public BaseResponse<List<GetResponseListRes>> getResponseList(@PathVariable Long memberId, @RequestParam("page") int page, @RequestParam("size") int size) {
        //jwt에서 idx 추출.
        Long memberIdByJwt = jwtService.getUserIdx();
        //memberId와 접근한 유저가 같은지 확인
        if (memberId != memberIdByJwt) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        List<GetResponseListRes> getResponseListRes = responseService.getResponseList(memberId,page,size);

        return new BaseResponse<>(getResponseListRes);
    }

    /**
     * 응답 내용 조회
     * [GET] /responses/info/{responseId}
     * @return BaseResponse<GetResponseInfoRes>
     */
    @ResponseBody
    @GetMapping("/info/{responseId}")
    @ApiOperation(value = "응답 내용 조회", notes = "헤더에 jwt 필요(key: X-ACCESS-TOKEN, value: jwt 값)")
    @ApiImplicitParam(name = "responseId", value = "조회할 응답 인덱스", required = true)
    @ApiResponses({
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 2003, message = "권한이 없는 유저의 접근입니다."),
            @ApiResponse(code = 4000, message = "데이터베이스 연결에 실패하였습니다.")
    })
    public BaseResponse<GetResponseInfoRes> getResponseInfo(@PathVariable Long responseId) {
        //jwt에서 idx 추출.
        Long memberIdByJwt = jwtService.getUserIdx();

        GetResponseInfoRes getResponseInfoRes = responseService.getResponseInfo(responseId);

        return new BaseResponse<>(getResponseInfoRes);
    }


    /**
     * 제작 설문 응답 통계 조회
     * [GET] /responses/statistics/{surveyId}/{memberId}
     * @return BaseResponse<List<GetResponseStatisticsRes>>
     */
    @ResponseBody
    @GetMapping("/statistics/{surveyId}/{memberId}")
    @ApiOperation(value = "제작 설문 응답 통계 조회", notes = "헤더에 jwt 필요(key: X-ACCESS-TOKEN, value: jwt 값)")
    @ApiImplicitParams({@ApiImplicitParam(name = "surveyId", value = "제작한 설문 인덱스", required = true),
            @ApiImplicitParam(name = "memberId", value = "조회한 유저 인덱스", required = true)})
    @ApiResponses({
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다.")
    })
    public BaseResponse<List<GetResponseStatisticsRes>> getResponseStatistics(@PathVariable Long surveyId, @PathVariable Long memberId) {
        //jwt에서 idx 추출.
        Long memberIdByJwt = jwtService.getUserIdx();
        //memberId와 접근한 유저가 같은지 확인
        if (memberId != memberIdByJwt) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        List<GetResponseStatisticsRes> getResponseStatisticsRes = responseService.getResponseStatistics(surveyId);

        return new BaseResponse<>(getResponseStatisticsRes);
    }
}

