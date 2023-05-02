package com.kale.formvey.controller.search;

import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.dto.survey.GetSurveyBoardRes;
import com.kale.formvey.service.survey.search.SearchService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/searchs")
public class SearchController {
    private final SearchService searchService;

    /**
     * 제작 설문 검색
     * [GET] /searchs/board
     * @return BaseResponse<GetSearchBoardRes>
     */
    @ResponseBody
    @GetMapping("/surveys")
    @ApiOperation(value = "설문 게시판 검색", notes = "설문 제목, 닉네임 기반 검색")
    private BaseResponse<List<GetSurveyBoardRes>> getSearchBoard(@RequestParam String keyword, @RequestParam("page") int page, @RequestParam("size") int size) {
        List<GetSurveyBoardRes> getSurveyBoardRes = searchService.getSearchBoard(keyword, page, size);

        return new BaseResponse<>(getSurveyBoardRes);
    }
}
