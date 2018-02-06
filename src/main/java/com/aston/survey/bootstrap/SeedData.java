package com.aston.survey.bootstrap;

import com.aston.survey.domain.*;
import com.aston.survey.domain.repositories.QuestionRepository;
import com.aston.survey.domain.repositories.SurveyRepository;
import com.aston.survey.domain.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SeedData implements CommandLineRunner{

    private final SurveyService surveyService;
    private final SurveySubmissionService surveySubmissionService;

    public SeedData(SurveyService surveyService, SurveySubmissionService surveySubmissionService)
    {
        this.surveyService = surveyService;
        this.surveySubmissionService = surveySubmissionService;
    }

    @Transactional
    @Override
    public void run(String...strings) throws Exception {

        if(surveyService.listAllSurveys().spliterator().getExactSizeIfKnown() == 0) {
            //only load data if no data loaded
            initData();
        }
    }

    private void initData() {
        final Choice emptyChoice = new Choice(null,null,"");

        final List<Choice> yesNoMaybe = Stream.of(emptyChoice, new Choice("Yes"),new Choice("No"),new Choice("Maybe")).collect(Collectors.toList());
        final List<Choice> fiveWaySatisfaction = Stream.of(
                emptyChoice,
                new Choice("Very dissatisfied"),new Choice("Somewhat dissatisfied"),new Choice("Neither satisfied nor dissatisfied"),
                new Choice("Somewhat satisfied"),new Choice("Very satisfied")).collect(Collectors.toList());

        final Comment emptyComment = new Comment("");

        //Survey 1 - test
        Question question1 = new Question(null,null,
                "What color is the sky?", Stream.of(emptyChoice, new Choice("Red"),new Choice("Blue"),new Choice("Orange")).collect(Collectors.toList()));
        Question question2 = new Question(null,null,
                "What is the capitol of Minnesota?", Stream.of(emptyChoice, new Choice("Saint Paul"),new Choice("Minneapolis"),new Choice("Duluth")).collect(Collectors.toList()));
        Question question3 = new Question(null,null,
                "How many feet are in a mile?", Stream.of(emptyChoice, new Choice("99"),new Choice("1284"),new Choice("5280")).collect(Collectors.toList()));

        Survey survey1 = new Survey(null, null, Stream.of(question1,question2,question3).collect(Collectors.toList()), "Test","Test Data");

        //Survey 2 - interview follow up
        Question question4 = new Question(null,null,
                "Would you consider applying to Aston Technologies in the future?", yesNoMaybe);
        Question question5 = new Question(null,null,
                "Did something unexpected prevent you from interviewing with our team?", yesNoMaybe);
        Question question6 = new Question(null,null,
                "Did you complete the interview assignment?", yesNoMaybe);

        Survey survey2 = new Survey(null, null, Stream.of(question4,question5,question6).collect(Collectors.toList()), "Interview follow-up", "Test Data");

//        Survey 3 - satisfaction
        Question question7 = new Question(null,null,
                "How satisfied were you with the interview assignment difficulty?", fiveWaySatisfaction);
        Question question8 = new Question(null,null,
                "How satisfied were you with the application process?", fiveWaySatisfaction);
        Question question9 = new Question(null,null,
                "How satisfied were you with your application contact?", fiveWaySatisfaction);

        Survey survey3 = new Survey(null, null, Stream.of(question7,question8,question9).collect(Collectors.toList()), "Satisfaction", "Test Data");

        //Survey 4 - text input test
        Question question10 = new Question(null, null,
                "Should we test variable length choices on questions?",yesNoMaybe);
        Question question11 = new Question(null, null,
                "Adequate textArea test?",Stream.of(emptyComment).collect(Collectors.toList()));
        Question question12 = new Question(null, null,
                "Do we need to add another question after this to test variable length amount of questions?",yesNoMaybe);
        Question question13 = new Question(null, null,
                "This is the 13th question in the database, so is it unlucky to answer it?",yesNoMaybe);
        Question question14 = new Question(null, null,
                "Adequate multiple comment/comment at end test?",Stream.of(emptyComment).collect(Collectors.toList()));

        Survey survey4 = new Survey(null, null, Stream.of(question10,question11,question12,question13,question14).collect(Collectors.toList()),"Text Area test", "Test Data");

//        Submissions
        SurveySubmission surveySubmission1 = new SurveySubmission();
        surveySubmission1.setSurvey(survey1);
        HashMap<Question,Choice> answers1 = new HashMap<>();
        for(Question q : survey1.getQuestions())
            answers1.put(q,q.getChoices().get(1));
        surveySubmission1.setSubmittedAnswers(answers1);

        SurveySubmission surveySubmission2 = new SurveySubmission();
        surveySubmission2.setSurvey(survey2);
        HashMap<Question,Choice> answers2 = new HashMap<>();
        for(Question q : survey2.getQuestions())
            answers2.put(q,q.getChoices().get(1));
        surveySubmission2.setSubmittedAnswers(answers2);

        SurveySubmission surveySubmission3 = new SurveySubmission();
        surveySubmission3.setSurvey(survey3);
        HashMap<Question,Choice> answers3 = new HashMap<>();
        for(Question q : survey3.getQuestions())
            answers3.put(q,q.getChoices().get(1));
        surveySubmission3.setSubmittedAnswers(answers3);

        SurveySubmission surveySubmission4 = new SurveySubmission();
        surveySubmission4.setSurvey(survey4);
        HashMap<Question,Choice> answers4 = new HashMap<>();
        Question q = null;
        for(int i=0;i<survey4.getQuestions().size();i++) {
            q = survey4.getQuestions().get(i);
            if(i == 1 || i == 4) {
                answers4.put(q, new Comment(i==1 ? "I Love Aston Technologies! Also this is a great test." : "Good test, I guess."));
            } else
                answers4.put(q, q.getChoices().get(1));
        }
        surveySubmission4.setSubmittedAnswers(answers4);

        //load data
        surveyService.saveSurveyList(Stream.of(survey1,survey2,survey3,survey4).collect(Collectors.toList()));
        surveySubmissionService.saveSurveySubmissionList(Stream.of(surveySubmission1,surveySubmission2,surveySubmission3,surveySubmission4).collect(Collectors.toList()));

    }

}
