package com.kale.formvey.controller.search;

import com.kale.formvey.config.BaseResponse;
import com.kale.formvey.dto.search.GetSearchBoardRes;
import com.kale.formvey.service.search.SearchService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiResponses({
            @ApiResponse(code = 2001, message = "JWT를 입력해주세요."),
            @ApiResponse(code = 2002, message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 4000, message = "데이터베이스 연결에 실패하였습니다.")
    })
    private BaseResponse<List<GetSearchBoardRes>> gatSearchBoard(@RequestParam String keyword, @RequestParam("page") int page, @RequestParam("size") int size) {
        List<GetSearchBoardRes> getSearchBoardRes = searchService.getSearchBoard(keyword, page, size);

        return new BaseResponse<>(getSearchBoardRes);
    }
}
