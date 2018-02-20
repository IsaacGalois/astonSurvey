package com.aston.survey.domain.repositories;

import com.aston.survey.domain.Survey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SurveyRepository extends CrudRepository<Survey, Long> {

    //region CUSTOM HQL QUERY METHODS
    @Query(value =
            "SELECT s.submitted_answers_key, s.submitted_answers_choice_id, COUNT(s.submitted_answers_choice_id)\n" +
                    "FROM survey_submission_submitted_answers s, survey_submission u\n" +
                    "WHERE s.survey_submission_survey_submission_id = u.survey_submission_id\n" +
                    "AND u.survey_survey_id = :id\n" +
                    "GROUP BY s.submitted_answers_key,  s.submitted_answers_choice_id\n" +
                    "ORDER BY s.submitted_answers_key, s.submitted_answers_choice_id ASC", //have to sort also on choiceId because this set is accessed in choice increasing order
            nativeQuery = true
    )
    List<Object[]> getAnswerCountsById(@Param("id") Long id);

    @Query(value =
    "SELECT s.submitted_answers_key, COUNT(s.submitted_answers_key)\n" +
            "            FROM survey_submission_submitted_answers s, survey_submission u\n" +
            "            WHERE s.survey_submission_survey_submission_id = u.survey_submission_id\n" +
            "            AND u.survey_survey_id = :id\n" +
            "            GROUP BY s.submitted_answers_key\n" +
            "            ORDER BY s.submitted_answers_key ASC",
            nativeQuery = true)
    List<Object[]> getTotalQuestionSubmissionsById(@Param("id") Long id);

    @Query(value =
            "SELECT q.question_text, c.comment_text\n" +
                    "                    FROM survey_submission_submitted_answers s, survey_submission u, choice c, question q\n" +
                    "                    WHERE s.survey_submission_survey_submission_id = u.survey_submission_id\n" +
                    "                    AND s.submitted_answers_choice_id = c.choice_id\n" +
                    "                    AND s.submitted_answers_key = q.question_id\n" +
                    "                    AND c.comment_text IS NOT NULL \n" +
                    "                    AND c.choice_id != :emptyCommentId\n" +
                    "                    AND u.survey_survey_id= :id\n" +
                    "                    ORDER BY q.question_id ASC",
            nativeQuery = true)
    List<Object[]> getQuestionAndNonEmptyCommentChoiceIdArrayBySurveyId(@Param("id") Long id, @Param("emptyCommentId") Long emptyCommentId);

    @Query(value =
            "SELECT s.survey_id, s.type, COUNT(u.survey_survey_id) FROM survey s\n" +
                    "LEFT OUTER JOIN survey_submission u ON u.survey_survey_id = s.survey_id\n" +
                    "GROUP BY u.survey_survey_id,s.survey_id\n" +
                    "ORDER BY s.type ASC",
            nativeQuery = true)
    List<Object[]> getSurveySubmissionCountsOrderedByType();
    //endregion

    List<Survey> findAllByOrderByTypeAsc();
}
