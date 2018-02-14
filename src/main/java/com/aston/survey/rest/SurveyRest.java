package com.aston.survey.rest;

import com.aston.survey.domain.Survey;
import com.aston.survey.domain.services.SurveyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/surveys")
public class SurveyRest {

    private Logger log = Logger.getLogger(SurveyRest.class);

    @Autowired
    private SurveyService surveyService;

    //GET ALL
    @GetMapping(value = "/")
    public Iterable<Survey> getAll(){ return surveyService.listAllSurveys();}

    //GET BY ID
    @GetMapping(value = "/{id}")
    public Survey getById(@PathVariable long id) {return surveyService.getSurveyById(id);}

    //SAVE
    @PostMapping(value = "/")
    public Survey save(@RequestBody Survey survey) {

        //load empty choice to each multiple choice question and empty comment to each comment question
        survey = surveyService.addEmptyChoiceAndOrCommentToMakerQuestions(survey);

        return surveyService.saveSurvey(survey);
    }

    //DELETE
    @DeleteMapping(value = "/{id}")
    public Boolean delete(@PathVariable long id) {
        boolean result = false;
        try {
            surveyService.deleteSurvey(id);
            result = true;
        } catch(Exception ex) {
            log.info(ex);
        }
        return result;
    }
}
