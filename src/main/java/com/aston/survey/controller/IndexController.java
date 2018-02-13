package com.aston.survey.controller;

import com.aston.survey.domain.Survey;
import com.aston.survey.domain.SurveySubmission;
import com.aston.survey.domain.services.ChoiceService;
import com.aston.survey.domain.services.CommentService;
import com.aston.survey.domain.services.SurveyService;
import com.aston.survey.domain.services.impl.SurveySubmissionServiceImpl;
import com.aston.survey.domain.vo.SurveySubmissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

    //region AUTOWIRED SERVICES
    @Autowired
    SurveyService surveyService;

    @Autowired
    ChoiceService choiceService;

    @Autowired
    CommentService commentService;

    @Autowired
    SurveySubmissionServiceImpl surveySubmissionService;
    //endregion

    //region PUBLIC ACCESS
    @GetMapping(value = {"/",""})
    public String index(Model model) {

        Iterable<Survey> surveys = surveyService.listAllSurveys();

        model.addAttribute("surveys",surveys);

        return "/index";
    }

    @GetMapping(value = "/surveyHub")
    public String surveyHub(Model model) {

        model.addAttribute("surveyTypeArray",surveyService.getSurveysInTypes());
        model.addAttribute("subCountsByTypeArray",surveyService.getSurveySubmissionCounts());

        return "/survey/survey_hub";
    }

    @GetMapping(value = "/takeSurvey/{id}")
    public String takeSurvey(@PathVariable long id, Model model) {

        Survey mySurvey = surveyService.getSurveyById(id);

        //populate survey and its questions
        model.addAttribute("survey",mySurvey);
        model.addAttribute("questions",mySurvey.getQuestions());

        //populate Comment handling data structures
        int[] commentIndexArray = surveyService.getCommentQuestionIndicesById(id);
        model.addAttribute("commentIndexArray",surveyService.getCommentQuestionIndicesById(id));
        model.addAttribute("numComments",commentIndexArray.length);

        //emptyComment and emptyChoice ids
        model.addAttribute("emptyChoiceId",choiceService.getEmptyChoice().getId());
        model.addAttribute("emptyCommentId",commentService.getEmptyComment().getId());

        //add VO
        model.addAttribute("surveySubmissionVO", new SurveySubmissionVO(mySurvey.getQuestions().size()));

        return "take_survey";
    }

    @PostMapping(value = "/submitSurvey/{id}")
    public String submitSurvey(SurveySubmissionVO surveySubmissionVO, @PathVariable long id, Model model) {

        SurveySubmission surveySubmission = surveySubmissionService.saveSurveySubmissionFromVO(surveySubmissionVO,id);
        surveySubmissionService.saveSurveySubmission(surveySubmission);

        return "thank_you";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }
    //endregion

    //region ADMIN
    @GetMapping(value = "/admin/statsHub")
    public String getStatsHub(Model model) {
        model.addAttribute("surveyTypeArray", surveyService.getSurveysInTypes());
        model.addAttribute("subCountsByTypeArray", surveyService.getSurveySubmissionCounts());

        return "admin/stats_hub";
    }

    @GetMapping( value = "/admin/stats/{id}")
    public String getStats(@PathVariable long id, Model model) {

        //populate survey
        model.addAttribute("survey", surveyService.getSurveyById(id));

        //populate Comment handling data structures
        int[] commentIndexArray = surveyService.getCommentQuestionIndicesById(id);
        model.addAttribute("commentIndexArray",commentIndexArray);
        model.addAttribute("numComments",commentIndexArray.length);

        //populate the statistics array from database ("cleaned" if survey contains Comment questions)
        if(commentIndexArray.length > 0)
            model.addAttribute("statArray", surveyService.cleanAnswerFrequencyMatrix(id, surveyService.getAnswerFrequencyBySurveyId(id)));
        else
            model.addAttribute("statArray", surveyService.getAnswerFrequencyBySurveyId(id));

        //populate total submissions for each question statistics
        model.addAttribute("totalQuestionSubmissionsArray",surveyService.getTotalQuestionSubmissionsBySurveyId(id));

        //populate emptyChoice and emptyComment ids
        model.addAttribute("emptyChoiceId",choiceService.getEmptyChoice().getId());
        model.addAttribute("emptyCommentId",commentService.getEmptyComment().getId());

        return "admin/stats";
    }
    
    @GetMapping(value = "/admin/surveyMaker")
    public String surveyMaker() {
        return "/admin/survey_maker";
    }

    @GetMapping(value = "/admin/addSurvey")
    public String surveyAdded() {
        return "/admin/survey_added";
    }
    //endregion

}

//todo: add paging?
//todo: make stats page for non-empty comments?
//todo: make deeper stat analysis?
//todo: add "write-ins" input text boxes (one line) as possible choices? <--will be time consuming and complicated...
//todo: add sql injection protection. (@Valid?)

//later:
//todo: add testing, full comments, & logging