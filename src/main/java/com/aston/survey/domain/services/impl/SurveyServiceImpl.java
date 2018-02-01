package com.aston.survey.domain.services.impl;

import com.aston.survey.domain.Survey;
import com.aston.survey.domain.repositories.SurveyRepository;
import com.aston.survey.domain.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SurveyServiceImpl implements SurveyService {

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

    @Override
    public Long[][] getAnswerFrequencyBySurveyId(Long id) {
        List<Object[]> statReturned = surveyRepository.getAnswerCountsById(id);
        Long[][] statMatrix = new Long[statReturned.size()][];

        for (int i = 0; i < statReturned.size(); i++) {
            Long[] row = new Long[statReturned.get(i).length];
            Arrays.fill(row, 0l);
            statMatrix[i] = row;
            for (int j = 0; j < statReturned.get(i).length; j++) {
                statMatrix[i][j] = ((java.math.BigInteger) statReturned.get(i)[j]).longValue();
            }
        }

        return statMatrix;
    }
}
