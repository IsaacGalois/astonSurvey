package com.aston.survey.domain.services.impl;

import com.aston.survey.domain.Survey;
import com.aston.survey.domain.repositories.SurveyRepository;
import com.aston.survey.domain.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurveyServiceImpl implements SurveyService{

    @Autowired
    private SurveyRepository surveyRepository;

    @Override
    public Iterable<Survey> listAllSurveys() {
        return surveyRepository.findAll();
    }

    @Override
    public Survey getSurveyById(Long id) {
        return surveyRepository.findOne(id);
    }

    @Override
    public Survey saveSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    @Override
    public Iterable<Survey> saveSurveyList(Iterable<Survey> surveyIterable) {
        return surveyRepository.save(surveyIterable);
    }

    @Override
    public void deleteSurvey(Long id) {
        surveyRepository.delete(id);
    }
}
