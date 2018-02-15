package com.aston.survey.controller;

import com.aston.survey.domain.Survey;
import com.aston.survey.domain.SurveySubmission;
import com.aston.survey.domain.services.ChoiceService;
import com.aston.survey.domain.services.CommentService;
import com.aston.survey.domain.services.SurveyService;
import com.aston.survey.domain.services.impl.SurveySubmissionServiceImpl;
import com.aston.survey.domain.vo.SurveySubmissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

    @Value("${server.servlet.path}")
    private String rootUrl;

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

        model.addAttribute("rootUrl",rootUrl);
        model.addAttribute("surveys",surveys);

        return "/index";
    }

    @GetMapping(value = "/surveyHub")
    public String surveyHub(Model model) {

        model.addAttribute("rootUrl",rootUrl);
        model.addAttribute("surveyTypeArray",surveyService.getSurveysInTypes());
        model.addAttribute("subCountsByTypeArray",surveyService.getSurveySubmissionCounts());

        return "/survey/survey_hub";
    }

    @GetMapping(value = "/takeSurvey/{id}")
    public String takeSurvey(@PathVariable long id, Model model) {

        Survey mySurvey = surveyService.getSurveyById(id);

        model.addAttribute("rootUrl",rootUrl);

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

        return "/survey/take_survey";
    }

    @PostMapping(value = "/submitSurvey/{id}")
    public String submitSurvey(SurveySubmissionVO surveySubmissionVO, @PathVariable long id, Model model) {

        model.addAttribute("rootUrl",rootUrl);

        SurveySubmission surveySubmission = surveySubmissionService.saveSurveySubmissionFromVO(surveySubmissionVO,id);
        surveySubmissionService.saveSurveySubmission(surveySubmission);

        return "/survey/thank_you";
    }

    @GetMapping(value = "/login")
    public String login(Model model) {
        model.addAttribute("rootUrl",rootUrl);
        return "login";
    }
    //endregion

    //region ADMIN
    @GetMapping(value = "/admin/statsHub")
    public String getStatsHub(Model model) {
        Survey[][] surveyTypeArray = surveyService.getSurveysInTypes();
        model.addAttribute("rootUrl",rootUrl);
        model.addAttribute("surveyTypeArray", surveyTypeArray);
        model.addAttribute("subCountsByTypeArray", surveyService.getSurveySubmissionCounts());
//        model.addAttribute("commentsState", surveyService.getCommentsState(surveyTypeArray));

        return "admin/stats_hub";
    }

    @GetMapping( value = "/admin/stats/{id}")
    public String getStats(@PathVariable long id, Model model) {

        model.addAttribute("rootUrl",rootUrl);

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

    //todo: finish non-empty comments routing (change statsHub?)
    @GetMapping(value = "/admin/non-emptyComments/{id}")
    public String getNonEmptyComments(@PathVariable long id, Model model) {

        model.addAttribute("rootUrl",rootUrl);
        model.addAttribute("survey",surveyService.getSurveyById(id));
        String[][] neCommentsArray = surveyService.getQuestionTextAndNonEmptyCommentTextArrayBySurveyId(id);
        model.addAttribute("neCommentsArray", neCommentsArray);
        model.addAttribute("numRows",neCommentsArray.length);

        return "admin/non-empty_comments";
    }
    
    @GetMapping(value = "/admin/surveyMaker")
    public String surveyMaker(Model model) {
        model.addAttribute("rootUrl",rootUrl);
        return "/admin/survey_maker";
    }

    @GetMapping(value = "/admin/addSurvey")
    public String surveyAdded(Model model) {
        model.addAttribute("rootUrl",rootUrl);
        return "/admin/survey_added";
    }
    //endregion

}

//todo: colored non-empty comment buttons on statsHub?
//todo: add sql injection protection by validating choice, question, and survey inputs before saving to db

//later:
//todo: add testing, full comments, & logging

//much later:
//todo: add paging?
//todo: enforce answering of all multi-choice questions (no empty choice submissions?) comment questions (how to enforce)?
//todo: make deeper stat analysis?
//todo: add "write-ins" input text boxes (one line) as possible choices? <--will be time consuming and complicated...