package com.kale.formvey.controller.survey;

import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.domain.Survey;
import com.kale.formvey.dto.survey.PostSurveyReq;
import com.kale.formvey.dto.survey.PostSurveyRes;
import com.kale.formvey.service.survey.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/surveys")
public class SurveyController {
    private final SurveyService surveyService;
    /**
     * 설문 생성
     * [POST] /surveys/create/{memberId}
     * @return BaseResponse<PostSurveyRes>
     */
    @ResponseBody
    @PostMapping("/create/{memberId}")
    private BaseResponse<PostSurveyRes> emailSignup(@RequestBody PostSurveyReq dto, @PathVariable Long memberId) {
        PostSurveyRes postSurveyRes = surveyService.createSurvey(memberId, dto);
        return new BaseResponse<>(postSurveyRes);
    }

    /**
     * 설문 삭제
     * [DELETE] /surveys/delete/{memberId}
     * @return BaseResponse<PostSurveyRes>
     */
    @DeleteMapping("/delete/{surveyId}")
    public BaseResponse<String> deleteSurvey(@PathVariable Long surveyId) {
        surveyService.deleteSurvey(surveyId);
        String result = "설문이 삭제되었습니다.";
        return new BaseResponse<>(result);
    }
}
