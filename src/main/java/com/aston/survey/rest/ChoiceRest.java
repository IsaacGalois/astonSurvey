package com.aston.survey.rest;

import com.aston.survey.domain.Choice;
import com.aston.survey.domain.services.ChoiceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/choices")
public class ChoiceRest {
    private Logger log = Logger.getLogger(ChoiceRest.class);

    @Autowired
    private ChoiceService choiceService;

    //GET ALL
    @GetMapping(value = "/")
    public Iterable<Choice> getAll(){ return choiceService.listAllChoices();}

    //GET BY ID
    @GetMapping(value = "/{id}")
    public Choice getById(@PathVariable long id) {return choiceService.getChoiceById(id);}

    //SAVE
    @PostMapping(value = "/")
    public Choice save(@RequestBody Choice question) { return choiceService.saveChoice(question);}

    //DELETE
    @DeleteMapping(value = "/{id}")
    public Boolean delete(@PathVariable long id) {
        boolean result = false;
        try {
            choiceService.deleteChoice(id);
            result = true;
        } catch(Exception ex) {
            log.info(ex);
        }
        return result;
    }
}
