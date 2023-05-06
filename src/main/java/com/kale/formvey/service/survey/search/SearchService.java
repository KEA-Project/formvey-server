package com.kale.formvey.service.survey.search;

import com.kale.formvey.domain.Survey;
import com.kale.formvey.dto.survey.GetSurveyBoardRes;
import com.kale.formvey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final SurveyRepository surveyRepository;
    public List<GetSurveyBoardRes> getSearchBoard(String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending()); // 페이징 처리 id 내림차순
        Page<Survey> searchedSurveys = surveyRepository.findAllBySearchTitle(keyword, pageRequest);
        List<GetSurveyBoardRes> surveys = new ArrayList<>();

        int totalPages = surveyRepository.findAllBySearch(keyword).size();

        if(totalPages % size == 0)
            totalPages = totalPages / size;
        else
            totalPages = totalPages / size + 1;

        for (Survey survey : searchedSurveys) {
            LocalDateTime nowDate = LocalDateTime.now();
            LocalDateTime endDate = survey.getEndDate();

            int remainDay = (int) ChronoUnit.DAYS.between(nowDate, endDate);

            GetSurveyBoardRes dto = new GetSurveyBoardRes(survey.getId(), survey.getSurveyTitle(), remainDay, survey.getResponseCnt(), survey.getMember().getNickname(), totalPages);
            surveys.add(dto);
        }
        return surveys;
    }
}
