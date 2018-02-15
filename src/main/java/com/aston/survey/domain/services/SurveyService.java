package com.aston.survey.domain.services;

import com.aston.survey.domain.Survey;

import java.util.List;

public interface SurveyService {

    Iterable<Survey> listAllSurveys();

    Survey getSurveyById(Long id);

    Survey saveSurvey(Survey survey);

    Iterable<Survey> saveSurveyList(Iterable<Survey> surveyIterable);

    void deleteSurvey(Long id);

    long[][] getAnswerFrequencyBySurveyId(Long id);

    String[][] getQuestionTextAndNonEmptyCommentTextArrayBySurveyId(Long id);

    long[][] getTotalQuestionSubmissionsBySurveyId(Long id);

    long[][] cleanAnswerFrequencyMatrix(Long id, long[][] statMatrix);

    int[] getCommentQuestionIndicesById(Long id);

    long[] getCommentQuestionIdsBySurveyId(Long id);

    Survey[][] getSurveysInTypes();

    long[][] getSurveySubmissionCounts();

    Survey addEmptyChoiceAndOrCommentToMakerQuestions(Survey survey);

    int[] getCommentsState(Survey[][] surveyArray);
}
