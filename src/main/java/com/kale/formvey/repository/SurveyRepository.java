package com.kale.formvey.repository;

import com.kale.formvey.domain.Survey;
import com.kale.formvey.dto.survey.GetSurveyInfoRes;
import com.kale.formvey.dto.survey.GetSurveyListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    @Query("select new com.kale.formvey.dto.survey.GetSurveyListRes(s.id, s.surveyTitle, s.surveyContent, s.endDate, s.responseCnt, s.status) from Survey s join s.member m where m.id=:id")
    List<GetSurveyListRes> findSurveyByMember(@Param("id") Long id);

}
