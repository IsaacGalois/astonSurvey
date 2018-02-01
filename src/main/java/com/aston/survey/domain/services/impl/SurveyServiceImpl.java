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


        //make rows one entry longer to handle empty choices
        Long[][] statMatrix = new Long[statReturned.size()][statReturned.get(0).length + 1];

        for (int i = 0; i < statReturned.size(); i++) {
            Arrays.fill(statMatrix[i], 0l);
            for (int j = 0; j < statReturned.get(i).length; j++) {
                if (((java.math.BigInteger) statReturned.get(i)[1]).longValue() != 1) {    //not the empty choice
                    statMatrix[i][j] = ((java.math.BigInteger) statReturned.get(i)[j]).longValue();
                } else {                                   //Handle empty choice
                    statMatrix[i][statReturned.get(0).length]++;
                }
            }
        }
        System.out.println("statMatrix:");
        for (Long[] row : statMatrix)
            System.out.println(Arrays.toString(row));

        return statMatrix;
    }
}
