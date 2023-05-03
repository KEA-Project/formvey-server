package com.kale.formvey.repository;

import com.kale.formvey.domain.Answer;
import com.kale.formvey.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByResponseId(Long id);
}
