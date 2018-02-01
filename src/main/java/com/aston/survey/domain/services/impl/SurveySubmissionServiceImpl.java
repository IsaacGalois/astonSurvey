package com.aston.survey.domain.services.impl;

import com.aston.survey.domain.Choice;
import com.aston.survey.domain.Question;
import com.aston.survey.domain.Survey;
import com.aston.survey.domain.SurveySubmission;
import com.aston.survey.domain.repositories.SurveySubmissionRepository;
import com.aston.survey.domain.services.ChoiceService;
import com.aston.survey.domain.services.SurveyService;
import com.aston.survey.domain.services.SurveySubmissionService;
import com.aston.survey.domain.vo.SurveySubmissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SurveySubmissionServiceImpl implements SurveySubmissionService {

    @Autowired
    private SurveySubmissionRepository surveySubmissionRepository;

    @Autowired
    private ChoiceService choiceService;

    @Autowired
    private SurveyService surveyService;

    @Override
    public Iterable<SurveySubmission> listAllSurveySubmissions() {
        return surveySubmissionRepository.findAll();
    }

    @Override
    public SurveySubmission getSurveySubmissionById(Long id) {
        return surveySubmissionRepository.findOne(id);
    }

    @Override
    public SurveySubmission saveSurveySubmission(SurveySubmission surveySubmission) {
        return surveySubmissionRepository.save(surveySubmission);
    }

    @Override
    public Iterable<SurveySubmission> saveSurveySubmissionList(Iterable<SurveySubmission> surveySubmissionIterable) {
        return surveySubmissionRepository.save(surveySubmissionIterable);
    }

    @Override
    public void deleteSurveySubmission(Long id) {
        surveySubmissionRepository.delete(id);
    }

    @Override
    public SurveySubmission saveSurveySubmissionFromVO(SurveySubmissionVO surveySubmissionVO, Long surveyId) {
        HashMap<Question, Choice> answers = new HashMap<>();

        Survey survey = surveyService.getSurveyById(surveyId);
        List<Question> questionList = survey.getQuestions();

        Long id = null;
        if (surveySubmissionVO.getChoiceArray() != null) {
            for (int i = 0; i < surveySubmissionVO.getChoiceArray().length; i++) {
                id = surveySubmissionVO.getChoiceArray()[i];
                if (id != null) {
                    answers.put(questionList.get(i), choiceService.getChoiceById(id));
                } else {
                    answers.put(questionList.get(i), choiceService.getEmptyChoice());
                }
            }
        } else  //todo replace with log entry
            System.out.println("WARN: Null choice array in VO. One will be created (replace this with log entry later)");

        //below we add empty answers for trailing questions with no submission (not sure why this happens but this fixes it)
        int offset = answers.size();
        int diff = questionList.size() - offset;
        if (diff > 0) {
            for (int i = 0; i < diff; i++)
                answers.put(questionList.get(offset+i),choiceService.getEmptyChoice());
        }

        SurveySubmission ret = new SurveySubmission(survey, answers);
        surveySubmissionRepository.save(ret);

        return ret;
    }

}
