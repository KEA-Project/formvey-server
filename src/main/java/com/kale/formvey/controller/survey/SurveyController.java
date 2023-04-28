package com.kale.formvey.controller.survey;

import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.domain.Survey;
import com.kale.formvey.dto.survey.PostSurveyReq;
import com.kale.formvey.dto.survey.PostSurveyRes;
import com.kale.formvey.service.survey.SurveyService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/surveys")
public class SurveyController {
    private final SurveyService surveyService;
    /**
     * 첫 설문 생성(배포 / 임시) - status = 1 -> 짧폼등록 x, 임시저장  / status = 1 -> 짧폼등록 o, status = 2
     * [POST] /surveys/create/{status}/{memberId}
     * @return BaseResponse<PostSurveyRes>
     */
    @ResponseBody
    @PostMapping("/create/{status}/{memberId}")
    private BaseResponse<PostSurveyRes> createSurvey(@RequestBody PostSurveyReq dto, @PathVariable int status, @PathVariable Long memberId) {
        PostSurveyRes postSurveyRes = surveyService.createSurvey(memberId, dto, status);
        return new BaseResponse<>(postSurveyRes);
    }
    /**
     * 존재하는 설문 생성(배포 / 임시) - status = 1 -> 짧폼등록 x, 임시저장  / status = 1 -> 짧폼등록 o, status = 2
     * [POST] /surveys/temp/{memberId}
     * @return BaseResponse<PostSurveyRes>
     */
    @ResponseBody
    @PutMapping("/create/{status}/{surveyId}/{memberId}")
    private BaseResponse<PostSurveyRes> updateSurvey(@RequestBody PostSurveyReq dto, @PathVariable int status, @PathVariable Long surveyId, @PathVariable Long memberId) {
        PostSurveyRes postSurveyRes = surveyService.updateSurvey(surveyId, memberId, dto, status);
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
