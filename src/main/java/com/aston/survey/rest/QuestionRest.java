package com.aston.survey.rest;

import com.aston.survey.domain.Question;
import com.aston.survey.domain.services.QuestionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

public class QuestionRest {
    private Logger log = Logger.getLogger(QuestionRest.class);

    @Autowired
    private QuestionService questionService;

    //GET ALL
    @GetMapping(value = "/")
    public Iterable<Question> getAll(){ return questionService.listAllQuestions();}

    //GET BY ID
    @GetMapping(value = "/{id}")
    public Question getById(@PathVariable long id) {return questionService.getQuestionById(id);}

    //SAVE
    @PostMapping(value = "/")
    public Question save(@RequestBody Question question) { return questionService.saveQuestion(question);}

    //DELETE
    @DeleteMapping(value = "/{id}")
    public Boolean delete(@PathVariable long id) {
        boolean result = false;
        try {
            questionService.deleteQuestion(id);
            result = true;
        } catch(Exception ex) {
            log.info(ex);
        }
        return result;
    }
}
