package com.kale.formvey.repository;

import com.kale.formvey.domain.ShortForm;
import com.kale.formvey.dto.survey.GetSurveyListRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShortFormRepository extends JpaRepository<ShortForm, Long> {

}
