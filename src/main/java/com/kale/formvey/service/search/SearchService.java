package com.kale.formvey.service.search;

import com.kale.formvey.domain.Survey;
import com.kale.formvey.dto.search.GetSearchBoardRes;
import com.kale.formvey.repository.MemberRepository;
import com.kale.formvey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SurveyRepository surveyRepository;
    public List<GetSearchBoardRes> getSearchBoard(String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending()); // 페이징
        Page<Survey> searchedSurveys = surveyRepository.findAllBySearchTitle(keyword, pageRequest);
        List<GetSearchBoardRes> surveys = new ArrayList<>();

        for (Survey survey : searchedSurveys) {
            LocalDate nowDate = LocalDate.now();
            LocalDate endDate = survey.getEndDate();
            Period period = nowDate.until(endDate); // 디데이 구하기

            GetSearchBoardRes dto = new GetSearchBoardRes(survey.getId(), survey.getSurveyTitle(), period.getDays(), survey.getResponseCnt(), survey.getMember().getNickname());
            surveys.add(dto);
        }
        return surveys;
    }
}
