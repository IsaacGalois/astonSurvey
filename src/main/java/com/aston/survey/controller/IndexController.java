package com.aston.survey.controller;

import com.aston.survey.domain.Survey;
import com.aston.survey.domain.SurveySubmission;
import com.aston.survey.domain.services.SurveyService;
import com.aston.survey.domain.services.impl.SurveySubmissionServiceImpl;
import com.aston.survey.domain.vo.SurveySubmissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    SurveyService surveyService;

    @Autowired
    SurveySubmissionServiceImpl surveySubmissionService;

    @GetMapping(value = {"/",""})
    public String index(Model model) {

        Iterable<Survey> surveys = surveyService.listAllSurveys();

        model.addAttribute("surveys",surveys);

        return "/index";
    }

    @GetMapping(value = "/takeSurvey/{id}")
    public String takeSurvey(@PathVariable long id, Model model) {

        Survey mySurvey = surveyService.getSurveyById(id);

        model.addAttribute("survey",mySurvey);
        model.addAttribute("questions",mySurvey.getQuestions());

        model.addAttribute("surveySubmissionVO", new SurveySubmissionVO(mySurvey.getQuestions().size()));

        return "take_survey";
    }

    @PostMapping(value = "/submitSurvey/{id}")
    public String submitSurvey(SurveySubmissionVO surveySubmissionVO, @PathVariable long id, Model model) {

        SurveySubmission surveySubmission = surveySubmissionService.saveSurveySubmissionFromVO(surveySubmissionVO,id);
        surveySubmissionService.saveSurveySubmission(surveySubmission);

        return "thank_you";
    }

    @GetMapping( value = "/adminStats/{id}")
    public String getAdminStats(@PathVariable long id, Model model) {

        Long[][] statArray = surveyService.getAnswerFrequencyBySurveyId(id);

        model.addAttribute("statArray", statArray);
        model.addAttribute("survey", surveyService.getSurveyById(id));

        //todo: need to redirect to id specific one here?

        return "/admin/admin_stats";
    }

}
