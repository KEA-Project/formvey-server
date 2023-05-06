package com.kale.formvey.repository;

import com.kale.formvey.domain.ShortResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShortResultRepository extends JpaRepository<ShortResult, Long> {

    @Query("SELECT s FROM ShortResult s WHERE s.member.id =:id")
    Page<ShortResult> findAllByMember(Long id,Pageable pageable);
}
