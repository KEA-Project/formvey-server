package com.kale.formvey.controller.response;

import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.dto.response.PostResponseReq;
import com.kale.formvey.service.response.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/responses")
public class ResponseController {
    private final ResponseService responseService;
    /**
     * 설문 응답
     * [POST] /responses/{surveyId}/{memberId}
     * @return BaseResponse<PostResponseRes>
     */
    @ResponseBody
    @PostMapping("/{surveyId}/{memberId}")
    private BaseResponse<String> responseSurvey(@RequestBody PostResponseReq dto,
                                                         @PathVariable Long surveyId, @PathVariable Long memberId) {
        responseService.responseSurvey(dto, surveyId, memberId);
        String result = "응답이 등록되었습니다";
        return new BaseResponse<>(result);
    }
}

