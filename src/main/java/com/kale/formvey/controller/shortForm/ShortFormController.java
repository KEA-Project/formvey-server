package com.kale.formvey.controller.shortForm;

import com.kale.formvey.config.BaseException;
import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.dto.shortForm.GetShortFormListRes;
import com.kale.formvey.dto.shortForm.GetShortFormRes;
import com.kale.formvey.dto.shortForm.PostShortFormReq;
import com.kale.formvey.dto.shortForm.PostShortFormRes;
import com.kale.formvey.service.shortForm.ShortFormService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/shortforms")
public class ShortFormController {

    private final ShortFormService shortFormService;

    /**
     * 짧폼 생성
<<<<<<< HEAD
     * [POST] /shortForms/create/{surveyId}
=======
     * [POST] /shortforms/create/{surveyId}
>>>>>>> 2e373c2363d42a93649e5937c992a213a5b3a14d
     * @return BaseResponse<PostShortFormRes>
     */
    @ResponseBody
    @PostMapping("/create/{surveyId}")
    @ApiOperation(value = "짧폼 생성")
    @ApiImplicitParam(name = "surveyId", value = "짧폼을 생성할 설문 id", required = true)
    @ApiResponses({
            @ApiResponse(code=2030, message="설문 아이디 값을 확인해주세요."),
            @ApiResponse(code=2050, message="짧폼 아이디 값을 확인해주세요."),
            @ApiResponse(code=4000, message="데이터베이스 연결에 실패하였습니다.")
    })
    private BaseResponse<PostShortFormRes> createShortForm(@PathVariable Long surveyId, @RequestBody PostShortFormReq dto) {
        try{

            PostShortFormRes postShortFormRes = shortFormService.createShortForm(surveyId, dto);

            return new BaseResponse<>(postShortFormRes);

        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 짧폼 리스트 조회
     * [GET] /shortForms/board
     * @return BaseResponse<List<GetShortFormListRes>>
     */
    @ResponseBody
    @GetMapping("/board")
    @ApiOperation(value = "짧폼 리스트 조회")
    public BaseResponse<List<GetShortFormListRes>> getShortFormList(@RequestParam("page") int page, @RequestParam("size") int size) {
        List<GetShortFormListRes> getShortFormListRes = shortFormService.getShortFormList(page, size);

        return new BaseResponse<>(getShortFormListRes);
    }

    /**
     * 짧폼 상세 조회
     * [GET] /shortForms/info/{shortFormId}
     * @return BaseResponse<GetShortFormRes>
     */
    @ResponseBody
    @GetMapping("/info/{shortFormId}")
    @ApiOperation(value = "짧폼 상세 조회")
    public BaseResponse<GetShortFormRes> getShortForm(@PathVariable Long shortFormId) {
        GetShortFormRes getShortFormRes = shortFormService.getShortForm(shortFormId);

        return new BaseResponse<>(getShortFormRes);
    }
}
