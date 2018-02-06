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

import java.util.Arrays;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    SurveyService surveyService;

    @Autowired
    ChoiceService choiceService;

    @Autowired
    CommentService commentService;

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
        int[] commentIndexArray = surveyService.getCommentQuestionIndicesById(id);
        model.addAttribute("commentIndexArray",surveyService.getCommentQuestionIndicesById(id));
        model.addAttribute("numComments",commentIndexArray.length);
        model.addAttribute("emptyChoiceId",choiceService.getEmptyChoice().getId());
        model.addAttribute("emptyCommentId",commentService.getEmptyComment().getId());

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

        model.addAttribute("survey", surveyService.getSurveyById(id));

        int[] commentIndexArray = surveyService.getCommentQuestionIndicesById(id);
        model.addAttribute("commentIndexArray",commentIndexArray);
        model.addAttribute("numComments",commentIndexArray.length);
        if(commentIndexArray.length > 0)
            model.addAttribute("statArray", surveyService.cleanAnswerFrequencyMatrix(id, surveyService.getAnswerFrequencyBySurveyId(id)));
        else
            model.addAttribute("statArray", surveyService.getAnswerFrequencyBySurveyId(id));
        model.addAttribute("emptyChoiceId",choiceService.getEmptyChoice().getId());
        model.addAttribute("emptyCommentId",commentService.getEmptyComment().getId());

        return "/admin/admin_stats";
    }

}

//todo: add paging?
//todo: make stats page for non-empty comments?
//todo: make deeper stat analysis?
//todo: rest endpoints?
//todo: add "write-ins" input text boxes (one line) as possible choices? <--will be time consuming and complicated...
//todo: database seems to reject submissions with incomplete answers (not all questions mapped to an answer, even empty answer)
//         ^-- is this a problem?

//later:
//todo: add testing, full comments, & logging