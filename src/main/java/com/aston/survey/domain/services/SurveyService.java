package com.aston.survey.domain.services;

import com.aston.survey.domain.Survey;

import java.util.List;

public interface SurveyService {

    Iterable<Survey> listAllSurveys();

    Survey getSurveyById(Long id);

    Survey saveSurvey(Survey survey);

    Iterable<Survey> saveSurveyList(Iterable<Survey> surveyIterable);

    void deleteSurvey(Long id);

    Long[][] getAnswerFrequencyBySurveyId(Long id);
}
