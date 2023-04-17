package com.kale.formvey.repository;

import com.kale.formvey.domain.Member;
import com.kale.formvey.domain.Question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findBySurveyId(Long id);
}
