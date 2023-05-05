package com.kale.formvey.repository;

import com.kale.formvey.domain.ShortForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShortFormRepository extends JpaRepository<ShortForm, Long> {
    Page<ShortForm> findAll(Pageable pageable);

    //포함 안 되어있는 거 찾기 위한것
    @Query(value = "SELECT s FROM ShortForm s WHERE s.id NOT IN (SELECT sa.shortForm.id FROM ShortAnswer sa WHERE sa.member.id = ?1) ORDER BY RAND()")
    Optional<ShortForm> findRandom(Long memberId);

}
