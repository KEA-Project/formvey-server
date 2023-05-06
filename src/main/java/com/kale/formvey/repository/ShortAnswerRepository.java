package com.kale.formvey.repository;

import com.kale.formvey.domain.ShortAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShortAnswerRepository extends JpaRepository<ShortAnswer, Long> {

    List<ShortAnswer> findByMemberId(Long memberId);
}
