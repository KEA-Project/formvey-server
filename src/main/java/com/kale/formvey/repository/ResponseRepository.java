package com.kale.formvey.repository;

import com.kale.formvey.domain.Response;
import com.kale.formvey.domain.Survey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResponseRepository extends JpaRepository<Response, Long> {

    //응답 설문 리스트 조회
    @Query("SELECT r FROM Response r  join r.member m where m.id=:id and r.status=1")
    Page<Response> findAllByMemberId(Long id, Pageable pageable);
}
