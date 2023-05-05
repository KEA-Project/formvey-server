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
import java.time.Period;
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

        for (Survey survey : searchedSurveys) {
            LocalDate nowDate = LocalDate.now();
            LocalDate endDate = survey.getEndDate().toLocalDate(); // 시분초 제외한 설문 종료 날짜 변환
            Period period = nowDate.until(endDate); // 디데이 구하기

            GetSurveyBoardRes dto = new GetSurveyBoardRes(survey.getId(), survey.getSurveyTitle(), period.getDays(), survey.getResponseCnt(), survey.getMember().getNickname());
            surveys.add(dto);
        }
        return surveys;
    }
}
