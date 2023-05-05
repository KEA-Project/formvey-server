package com.kale.formvey.repository;

import com.kale.formvey.domain.Response;
import com.kale.formvey.domain.Survey;
import com.kale.formvey.dto.survey.GetSurveyListRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    //제작 설문 리스트 조회
    @Query("SELECT s FROM Survey s join s.member m where m.id=:id")
    Page<Survey> findByMemberId(Long id, Pageable pageable);

    // 게시판 리스트 조회
    Page<Survey> findAll(Pageable pageable);

    // 게시판 검색 쿼리
    @Query("SELECT s FROM Survey s WHERE s.surveyTitle LIKE %:keyword% OR s.member.nickname LIKE %:keyword%")
    Page<Survey> findAllBySearchTitle(String keyword, Pageable pageable);


}
