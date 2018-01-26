package com.aston.survey.controller;

import com.aston.survey.domain.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    @Autowired
    SurveyService surveyService;

    @GetMapping(value = {"/",""})
    public String index() {
        return "/index";
    }

    @GetMapping(value = "/takeSurvey/{id}")
    public String takeSurvey(@PathVariable long id) {

        return "take_survey";
    }

}
