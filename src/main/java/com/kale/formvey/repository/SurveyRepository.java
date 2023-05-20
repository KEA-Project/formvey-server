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
import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    //제작 설문 리스트 조회
    @Query("SELECT s FROM Survey s join s.member m where m.id=:id")
    Page<Survey> findByMemberId(Long id, Pageable pageable);

    // 게시판 리스트 조회
    @Query("SELECT s FROM Survey s WHERE s.isPublic = 1")
    Page<Survey> findPublicSurvey(Pageable pageable);

    // 게시판 검색 쿼리
    @Query("SELECT s FROM Survey s WHERE s.surveyTitle LIKE %:keyword% OR s.member.nickname LIKE %:keyword%")
    Page<Survey> findAllBySearchTitle(String keyword, Pageable pageable);

    // 게시판 검색 페이지 쿼리
    @Query("SELECT s FROM Survey s WHERE s.surveyTitle LIKE %:keyword% OR s.member.nickname LIKE %:keyword%")
    List<Survey> findAllBySearch(String keyword);

    //제작 설문 리스트 조회
    @Query("SELECT s FROM Survey s join s.member m where m.id =:id")
    List<Survey> findByMemberId(Long id);

    // 제작 중인 설문 리스트 조회
    @Query("SELECT s FROM Survey s JOIN s.member m WHERE m.id=:id AND s.status=:status")
    List<Survey> findAllByStatus(Long id,int status);

}
