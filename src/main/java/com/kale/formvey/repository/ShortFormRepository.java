package com.kale.formvey.repository;

import com.kale.formvey.domain.ShortForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShortFormRepository extends JpaRepository<ShortForm, Long> {
    Page<ShortForm> findAll(Pageable pageable);

    //포함 안 되어있는 거 찾기 위한것
    @Query(value = "SELECT * FROM short_form WHERE shortform_id NOT IN (SELECT shortform_id FROM short_answer WHERE member_id = ?1) ORDER BY DBMS_RANDOM.VALUE FETCH FIRST 1 ROWS ONLY", nativeQuery = true)
    Optional<ShortForm> findRandom(Long memberId);


}
