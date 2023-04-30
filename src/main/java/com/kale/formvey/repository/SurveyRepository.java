package com.kale.formvey.repository;

import com.kale.formvey.domain.Survey;
import com.kale.formvey.dto.survey.GetSurveyListRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    @Query("select new com.kale.formvey.dto.survey.GetSurveyListRes(s.id, s.surveyTitle, s.surveyContent, s.endDate, s.responseCnt, s.status) from Survey s join s.member m where m.id=:id")
    List<GetSurveyListRes> findSurveyByMember(@Param("id") Long id);

    // 게시판 검색 쿼리
    @Query("SELECT s FROM Survey s WHERE s.surveyTitle LIKE %:keyword% OR s.member.nickname LIKE %:keyword%")
    Page<Survey> findAllBySearchTitle(String keyword, Pageable pageable);

}
