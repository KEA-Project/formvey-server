package com.kale.formvey.controller.survey;

import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.dto.survey.PostSurveyReq;
import com.kale.formvey.dto.survey.PostSurveyRes;
import com.kale.formvey.service.survey.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/surveys")
public class SurveyController {
    private final SurveyService surveyService;
    /**
     * 설문 생성
     * [POST] /surveys
     * @return BaseResponse<PostSurveyRes>
     */
//    @ResponseBody
//    @PostMapping()
//    private BaseResponse<PostSurveyRes> emailSignup(@RequestBody PostSurveyReq dto) {
//        return new BaseResponse<>();
//    }
}
