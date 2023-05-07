package com.kale.formvey.repository;

import com.kale.formvey.domain.ShortResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShortResultRepository extends JpaRepository<ShortResult, Long> {

    // 해금된 짧폼 조회 게시판
    @Query("SELECT s FROM ShortResult s WHERE s.member.id =:id")
    Page<ShortResult> findAllByMember(Long id,Pageable pageable);


    @Query("SELECT s FROM ShortResult s WHERE s.member.id =:id")
    List<ShortResult> findAllByMember(Long memberId);

    // 전체 짧폼 조회 게시판 (해금 여부 확인용)
    @Query("SELECT s.shortForm.id FROM ShortResult s WHERE s.member.id =:id")
    List<Long> findSurveyIdByMember(Long id);
}
