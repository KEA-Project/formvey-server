package com.kale.formvey.repository;

import com.kale.formvey.domain.Survey;
import com.kale.formvey.dto.choice.GetChoiceInfoRes;
import com.kale.formvey.dto.question.GetQuestionInfoRes;
import com.kale.formvey.dto.survey.GetSurveyInfoRes;
import com.kale.formvey.dto.survey.GetSurveyListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    @Query("select new com.kale.formvey.dto.survey.GetSurveyListRes(s.id, s.surveyTitle, s.surveyContent, s.endDate, s.responseCnt, s.status) from Survey s join s.member m where m.id=:id")
    List<GetSurveyListRes> findSurveyByMember(@Param("id") Long id);

//    @Query("select new com.kale.formvey.dto.survey.GetSurveyInfoRes(s.surveyTitle, s.surveyContent,s.startDate, s.endDate, s.responseCnt, s.isAnonymous, s.rewardOption, s.url, s.exitUrl, s.status, q.questionIdx, q.questionTitle, q.type, q.isEssential, q.isShort, c.choiceIndex, c.choiceContent) FROM Survey s JOIN s.questions q JOIN q.choices c WHERE s.id = :surveyId")
//    GetSurveyInfoRes findSurveyById(@Param("surveyId") Long surveyId);

    @Query("SELECT s.surveyTitle, s.surveyContent, s.startDate, s.endDate, s.responseCnt, s.isAnonymous, s.rewardOption, s.url, s.exitUrl, s.status, " +
            "q.questionIdx, q.questionTitle, q.type, q.isEssential, q.isShort, " +
            "c.choiceIndex, c.choiceContent " +
            "FROM Survey s " +
            "LEFT JOIN s.questions q " +
            "LEFT JOIN q.choices c " +
            "WHERE s.id = :surveyId")
    GetSurveyInfoRes findSurveyById(@Param("surveyId") Long surveyId);

}
