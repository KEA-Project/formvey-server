package com.kale.formvey.controller.survey;

import com.kale.formvey.config.BaseException;
import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.domain.Survey;
import com.kale.formvey.dto.survey.*;
import com.kale.formvey.service.survey.SurveyService;
import com.kale.formvey.utils.JwtService;
import io.swagger.annotations.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kale.formvey.config.BaseResponseStatus.INVALID_USER_JWT;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/surveys")
public class SurveyController {
    private final SurveyService surveyService;
    private final JwtService jwtService;

    /**
     * 설문 생성
     * [POST] /surveys/create
     * @return BaseResponse<PostSurveyRes>
     */
    @ResponseBody
    @PostMapping("/create")
    @ApiOperation(value = "설문 생성", notes = "헤더에 jwt 필요(key: X-ACCESS-TOKEN, value: jwt 값)")
    @ApiResponses({
            @ApiResponse(code=2001, message="JWT를 입력해주세요."),
            @ApiResponse(code=2002, message="유효하지 않은 JWT입니다."),
            @ApiResponse(code=4000, message="데이터베이스 연결에 실패하였습니다.")
    })
    private BaseResponse<PostSurveyRes> createSurvey(@RequestBody PostSurveyReq dto) {
        try{
            //jwt에서 idx 추출.
            Long memberId = jwtService.getUserIdx();

            PostSurveyRes postSurveyRes = surveyService.createSurvey(memberId, dto);

            return new BaseResponse<>(postSurveyRes);

        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 설문 수정
     * [PATCH] /surveys/modify/{surveyId}
     * @return BaseResponse<String>
     */

    /**
     * 설문 삭제
     * [Patch] /surveys/delete/{surveyId}
     * @return BaseResponse<String>
     */
    @PatchMapping("/delete/{surveyId}")
    @ApiOperation(value = "설문 삭제", notes = "헤더에 jwt 필요(key: X-ACCESS-TOKEN, value: jwt 값)")
    @ApiImplicitParam(name="surveyId", value="삭제할 설문 인덱스", required = true)
    @ApiResponses({
            @ApiResponse(code=2001, message="JWT를 입력해주세요."),
            @ApiResponse(code=2002, message="유효하지 않은 JWT입니다."),
            @ApiResponse(code=2003, message="권한이 없는 유저의 접근입니다."),
            @ApiResponse(code=4000, message="데이터베이스 연결에 실패하였습니다.")
    })
    public BaseResponse<String> deleteSurvey(@PathVariable Long surveyId, @RequestBody DeleteSurveyReq deleteSurveyReq) {
        try{
            //jwt에서 idx 추출.
            Long memberIdByJwt = jwtService.getUserIdx();
            //memberId와 접근한 유저가 같은지 확인
            if(deleteSurveyReq.getMemberId()!= memberIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            surveyService.deleteSurvey(surveyId);
            String result = "설문이 삭제되었습니다.";

            return new BaseResponse<>(result);

        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 제작 설문 리스트 조회
     * [GET] /surveys/list/{memberId}
     * @return BaseResponse<List<GetSurveyListRes>>
     */
    @ResponseBody
    @GetMapping("/list/{memberId}")
    @ApiOperation(value = "제작 설문 리스트 조회", notes = "헤더에 jwt 필요(key: X-ACCESS-TOKEN, value: jwt 값)")
    @ApiImplicitParam(name="memberId", value="설문 제작 유저의 인덱스", required = true)
    @ApiResponses({
            @ApiResponse(code=2001, message="JWT를 입력해주세요."),
            @ApiResponse(code=2002, message="유효하지 않은 JWT입니다."),
            @ApiResponse(code=2003, message="권한이 없는 유저의 접근입니다."),
            @ApiResponse(code=4000, message="데이터베이스 연결에 실패하였습니다.")
    })
    public BaseResponse<List<GetSurveyListRes>> getSurveyList(@PathVariable Long memberId){
        try {
            //jwt에서 idx 추출.
            Long memberIdByJwt = jwtService.getUserIdx();
            //memberId와 접근한 유저가 같은지 확인
            if(memberId!= memberIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            List<GetSurveyListRes> getSurveyListRes=surveyService.getSurveyList(memberId);

            return new BaseResponse<>(getSurveyListRes);

        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 설문 내용 조회
     * [GET] /surveys/info/{surveyId}
     * @return BaseResponse<GetSurveyRes>
     */
    @ResponseBody
    @GetMapping("/info/{surveyId}")
    @ApiOperation(value = "설문 내용 조회", notes = "헤더에 jwt 필요(key: X-ACCESS-TOKEN, value: jwt 값)")
    @ApiImplicitParam(name="surveyId", value="조회할 설문 인덱스", required = true)
    @ApiResponses({
            @ApiResponse(code=2001, message="JWT를 입력해주세요."),
            @ApiResponse(code=2002, message="유효하지 않은 JWT입니다."),
            @ApiResponse(code=2003, message="권한이 없는 유저의 접근입니다."),
            @ApiResponse(code=2030, message="설문 아이디 값을 확인해주세요."),
            @ApiResponse(code=4000, message="데이터베이스 연결에 실패하였습니다.")
    })
    public BaseResponse<GetSurveyInfoRes> getSurveyInfo(@PathVariable Long surveyId, @RequestBody GetSurveyReq getSurveyReq){
        try {
            //jwt에서 idx 추출.
            Long memberIdByJwt = jwtService.getUserIdx();
            //memberId와 접근한 유저가 같은지 확인
            if(getSurveyReq.getMemberId()!= memberIdByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            GetSurveyInfoRes getSurveyInfoRes=surveyService.getSurveyInfo(memberIdByJwt,surveyId);

            return new BaseResponse<>(getSurveyInfoRes);

        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
