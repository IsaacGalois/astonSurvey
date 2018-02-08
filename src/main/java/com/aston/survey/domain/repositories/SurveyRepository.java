package com.aston.survey.domain.repositories;

import com.aston.survey.domain.Survey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyRepository extends CrudRepository<Survey, Long> {

    @Query(value =
            "SELECT s.SUBMITTED_ANSWERS_KEY, s.SUBMITTED_ANSWERS_CHOICE_ID, COUNT(s.SUBMITTED_ANSWERS_CHOICE_ID) " +
                    "FROM SURVEY_SUBMISSION_SUBMITTED_ANSWERS s, SURVEY_SUBMISSION u  " +
                    "WHERE s.SURVEY_SUBMISSION_SURVEY_SUBMISSION_ID = u.SURVEY_SUBMISSION_ID " +
                    "AND u.SURVEY_SURVEY_ID = :id\n" +
                    "GROUP BY s.SUBMITTED_ANSWERS_KEY,  s.SUBMITTED_ANSWERS_CHOICE_ID\n" +
                    "ORDER BY s.SUBMITTED_ANSWERS_KEY, s.SUBMITTED_ANSWERS_CHOICE_ID ASC", //have to sort also on choiceId because this set is accessed in choice increasing order
            nativeQuery = true
    )
    List<Object[]> getAnswerCountsById(@Param("id") Long id);

    @Query(value =
    "SELECT s.SUBMITTED_ANSWERS_KEY, COUNT(s.SUBMITTED_ANSWERS_KEY) \n" +
            "FROM SURVEY_SUBMISSION_SUBMITTED_ANSWERS s, SURVEY_SUBMISSION u \n" +
            "WHERE s.SURVEY_SUBMISSION_SURVEY_SUBMISSION_ID = u.SURVEY_SUBMISSION_ID\n" +
            "AND u.SURVEY_SURVEY_ID = :id\n" +
            "GROUP BY s.SUBMITTED_ANSWERS_KEY\n" +
            "ORDER BY s.SUBMITTED_ANSWERS_KEY ASC",
            nativeQuery = true)
    List<Object[]> getTotalQuestionSubmissionsById(@Param("id") Long id);

    List<Survey> findAllByOrderByTypeAsc();

    @Query(value =
            "SELECT s.SURVEY_ID, s.TYPE, COUNT(u.SURVEY_SURVEY_ID) FROM SURVEY s\n" +
                    "LEFT OUTER JOIN SURVEY_SUBMISSION u ON u.SURVEY_SURVEY_ID = s.SURVEY_ID\n" +
                    "GROUP BY u.SURVEY_SURVEY_ID\n" +
                    "ORDER BY s.TYPE ASC",
            nativeQuery = true)
    List<Object[]> getSurveySubmissionCountsOrderedByType();

}
