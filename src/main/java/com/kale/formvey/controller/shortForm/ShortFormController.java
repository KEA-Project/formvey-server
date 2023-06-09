package com.kale.formvey.controller.shortForm;

import com.kale.formvey.config.BaseException;
import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.dto.shortForm.*;
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
     * 짧폼 리스트 조회
     * [GET] /shortForms/board/{memberId}
     * @return BaseResponse<List<GetShortFormListRes>>
     */
    @ResponseBody
    @GetMapping("/board/{memberId}")
    @ApiOperation(value = "짧폼 리스트 조회")
    public BaseResponse<List<GetShortFormListRes>> getShortFormList(@RequestParam("page") int page, @RequestParam("size") int size, @PathVariable Long memberId) {
        List<GetShortFormListRes> getShortFormListRes = shortFormService.getShortFormList(page, size, memberId);

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

    /**
     * 짧폼 메인 조회
     * [GET] /shortForms/random/{memberId}
     * @return BaseResponse<GetShortFormMainRes>
     */
    @ResponseBody
    @GetMapping("/random/{memberId}")
    @ApiOperation(value = "짧폼 랜덤 조회", notes = "헤더에 jwt 필요(key: X-ACCESS-TOKEN, value: jwt 값)")
    @ApiImplicitParam(name = "memberId", value = "로그인한 유저의 인덱스", required = true)
    public BaseResponse<GetShortFormMainRes> getRandomShortForm(@PathVariable Long memberId) {
        GetShortFormMainRes getShortFormMainRes = shortFormService.getShortFormMain(memberId);

        return new BaseResponse<>(getShortFormMainRes);
    }
}
