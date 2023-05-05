package com.kale.formvey.controller.shortForm;

import com.kale.formvey.config.BaseException;
import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.dto.shortForm.PostShortFormReq;
import com.kale.formvey.dto.shortForm.PostShortFormRes;
import com.kale.formvey.service.shortForm.ShortFormService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/shortforms")
public class ShortFormController {

    private final ShortFormService shortFormService;

    /**
     * 짧폼 생성
     * [POST] /shortforms/create/{surveyId}
     * @return BaseResponse<PostShortFormRes>
     */
//    @ResponseBody
//    @PostMapping("/create/{surveyId}")
//    @ApiOperation(value = "짧폼 생성")
//    @ApiImplicitParam(name = "surveyId", value = "짧폼을 생성할 설문 id", required = true)
//    @ApiResponses({
//            @ApiResponse(code=2030, message="설문 아이디 값을 확인해주세요."),
//            @ApiResponse(code=4000, message="데이터베이스 연결에 실패하였습니다.")
//    })
//    private BaseResponse<PostShortFormRes> createShortForm(@PathVariable Long surveyId, @RequestBody PostShortFormReq dto) {
//        try{
//
//            PostShortFormRes postShortFormRes = shortFormService.createShortForm(surveyId, dto);
//
//            return new BaseResponse<>(postShortFormRes);
//
//        } catch (BaseException exception){
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }
}
