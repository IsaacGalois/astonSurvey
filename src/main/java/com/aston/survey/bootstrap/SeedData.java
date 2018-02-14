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
        final Choice emptyChoice = new Choice("");

        final List<Choice> yesNoMaybe = Stream.of(emptyChoice, new Choice("Yes"),new Choice("No"),new Choice("Maybe")).collect(Collectors.toList());
        final List<Choice> fiveWaySatisfaction = Stream.of(
                emptyChoice,
                new Choice("Very dissatisfied"),new Choice("Somewhat dissatisfied"),new Choice("Neither satisfied nor dissatisfied"),
                new Choice("Somewhat satisfied"),new Choice("Very satisfied")).collect(Collectors.toList());
        final List<Choice> fiveWayAgreeDisagree = Stream.of(
                emptyChoice,
                new Choice("Strongly disagree"), new Choice("Somewhat disagree"), new Choice("Neither agree nor disagree"),
                new Choice("Somewhat agree"), new Choice("Strongly agree")).collect(Collectors.toList());
        final List<Choice> fiveWayAcceptability = Stream.of(
                emptyChoice,
                new Choice("Unacceptable"), new Choice("Needs Improvement"), new Choice("Meets Standard"),
                new Choice("Exceeds Standard"), new Choice("Outstanding"), new Choice("N/A (have not experienced or observed)")).collect(Collectors.toList());

        final Comment emptyComment = new Comment("");


        //region Test Data
        //Survey 1 - test
        Question question1 = new Question("What color is the sky?", Stream.of(emptyChoice, new Choice("Red"),new Choice("Blue"),new Choice("Orange")).collect(Collectors.toList()));
        Question question2 = new Question("What is the capitol of Minnesota?", Stream.of(emptyChoice, new Choice("Saint Paul"),new Choice("Minneapolis"),new Choice("Duluth")).collect(Collectors.toList()));
        Question question3 = new Question("How many feet are in a mile?", Stream.of(emptyChoice, new Choice("99"),new Choice("1284"),new Choice("5280")).collect(Collectors.toList()));

        Survey survey1 = new Survey(null, null, Stream.of(question1,question2,question3).collect(Collectors.toList()), "Test","Test Data");

        //Survey 2 - interview follow up
        Question question4 = new Question("Would you consider applying to Aston Technologies in the future?", yesNoMaybe);
        Question question5 = new Question("Did something unexpected prevent you from interviewing with our team?", yesNoMaybe);
        Question question6 = new Question("Did you complete the interview assignment?", yesNoMaybe);

        Survey survey2 = new Survey(null, null, Stream.of(question4,question5,question6).collect(Collectors.toList()), "Interview follow-up", "Test Data");

//        Survey 3 - satisfaction
        Question question7 = new Question("How satisfied were you with the interview assignment difficulty?", fiveWaySatisfaction);
        Question question8 = new Question("How satisfied were you with the application process?", fiveWaySatisfaction);
        Question question9 = new Question("How satisfied were you with your application contact?", fiveWaySatisfaction);

        Survey survey3 = new Survey(null, null, Stream.of(question7,question8,question9).collect(Collectors.toList()), "Satisfaction", "Test Data");

        //Survey 4 - text input test
        Question question10 = new Question("Should we test variable length choices on questions?",yesNoMaybe);
        Question question11 = new Question("Adequate textArea test?",Stream.of(emptyComment).collect(Collectors.toList()));
        Question question12 = new Question("Do we need to add another question after this to test variable length amount of questions?",yesNoMaybe);
        Question question13 = new Question("This is the 13th question in the database, so is it unlucky to answer it?",yesNoMaybe);
        Question question14 = new Question("Adequate multiple comment/comment at end test?",Stream.of(emptyComment).collect(Collectors.toList()));

        Survey survey4 = new Survey( Stream.of(question10,question11,question12,question13,question14).collect(Collectors.toList()),"Text Area test", "Test Data");
        //endregion

        //region Demo Survey
        //Survey 5 - Real World Example Job Satisfaction
        Question question15 = new Question("I would like to work in the IT field in the future.", fiveWayAgreeDisagree);
        Question question16 = new Question("Aston Technologies is far away from where I live.", fiveWayAgreeDisagree);
        Question question17 = new Question("I would like to work with the people I interacted with.", fiveWayAgreeDisagree);
        Question question18 = new Question("I was treated fairly and professionally by Aston Technologies Staff.", fiveWayAgreeDisagree);
        Question question19 = new Question("I may apply to Aston Technologies again in the future.", fiveWayAgreeDisagree);
        Question question20 = new Question("Aston Technologies has a fun and welcoming business culture.", fiveWayAgreeDisagree);
        Question question21 = new Question("I am too busy with other obligations to work at Aston Technologies right now.", fiveWayAgreeDisagree);
        Question question22 = new Question("My life is moving in a different direction than Aston Technologies currently.", fiveWayAgreeDisagree);
        Question question23 = new Question("I could thrive at Aston Technologies if hired.", fiveWayAgreeDisagree);
        Question question24 = new Question("I would be proud to work at Aston Technologies.", fiveWayAgreeDisagree);
        Question question25 = new Question("Arranging transportation to Aston Technologies is not difficult.", fiveWayAgreeDisagree);
        Question question26 = new Question("I found the Aston Technologies application process to be straightforward and easy to complete.", fiveWayAgreeDisagree);
        Question question27 = new Question("The Aston Technologies website was easy to navigate and presented in a professional way.", fiveWayAgreeDisagree);
        Question question28 = new Question("I found the prospect of traveling for extended periods unappealing.", fiveWayAgreeDisagree);
        Question question29 = new Question("I found the depth and breadth of course material that would be covered intimidating.", fiveWayAgreeDisagree);

        Survey survey5 = new Survey( Stream.of(question15,question16,question17,question18,question19,question20,question21,question22,question23,question24,question25,question26,question27,question28,question29)
                .collect(Collectors.toList()),"Official Aston Technologies Interview Follow-Up Survey", "Demo Survey");
        //endregion

        //region Manager Reviews
        //Valuing Behaviors
        Question mrq1_1 = new Question("Seeks input from all team members.",fiveWayAcceptability);
        Question mrq1_2 = new Question( "Measures results instead of individual styles.", fiveWayAcceptability);
        Question mrq1_3 = new Question( "Maintains a balance between \"people\" issues and \"business\" issues", fiveWayAcceptability);
        Question mrq1_4 = new Question( "Shows genuine concern for team members", fiveWayAcceptability);
        Question mrq1_5 = new Question( "Keeps the focus on fixing problems rather than finding someone to blame.", fiveWayAcceptability);
        Question mrq1_6 = new Question( "Treats people fairly, without showing favoritism.", fiveWayAcceptability);
        Question mrq1_7 = new Question( "Cares about me.", fiveWayAcceptability);
        Question mrq1_8 = new Question( "Protects confidentiality.", fiveWayAcceptability);
        Question mrq1_9 = new Question( "Recognizes and rewards my individual contributions in a manner meaningful to me.", fiveWayAcceptability);

        //Interdependence Behaviors
        Question mrq2_1 = new Question( "Supports a team environment by; recognizing and rewarding collaboration, cooperation and activities contributing to others' success.", fiveWayAcceptability);
        Question mrq2_2 = new Question( "Recognizes and rewards team-supportive actions and behaviors.", fiveWayAcceptability);
        Question mrq2_3 = new Question( "Recognizes and supports the work of other departments.", fiveWayAcceptability);
        Question mrq2_4 = new Question( "Doesn't criticize those who are not present.",fiveWayAcceptability);
        Question mrq2_5 = new Question( "Considers the impact of actions and decisions on other departments before implementing.", fiveWayAcceptability);

        //Communications Behaviors
        Question mrq3_1 = new Question("Encourages others to express different ideas and perspectives.", fiveWayAcceptability);
        Question mrq3_2 = new Question("Is open to other perspectives and is willing to change his/her position when presented with compelling information.", fiveWayAcceptability);
        Question mrq3_3 = new Question("Open to negative and/or constructive feedback.", fiveWayAcceptability);
        Question mrq3_4 = new Question("Keeps me informed on status of my work and updates in the organization.", fiveWayAcceptability);
        Question mrq3_5 = new Question("Gives open and constructive feedback.", fiveWayAcceptability);
        Question mrq3_6 = new Question("Effectively deals with conflict.", fiveWayAcceptability);
        Question mrq3_7 = new Question("Lets me know how I am doing.", fiveWayAcceptability);
        Question mrq3_8 = new Question("Involves me in decision-making when appropriate.", fiveWayAcceptability);
        Question mrq3_9 = new Question("Sets a clear direction for our department.", fiveWayAcceptability);

        //Valuing Diversity Behaviors
        Question mrq4_1 = new Question("Ensures that department activities are inclusive by verifying scheduling needs.", fiveWayAcceptability);
        Question mrq4_2 = new Question("Seeks input/feedback from diverse individuals and groups, including internal/external customers.", fiveWayAcceptability);
        Question mrq4_3 = new Question("Treats everyone with respect and fairness.", fiveWayAcceptability);

        //Leadership Behaviors
        Question mrq5_1 = new Question("Encourages and embraces change by challenging status quo.", fiveWayAcceptability);
        Question mrq5_2 = new Question("Provides cross-functional development opportunities for team members.", fiveWayAcceptability);
        Question mrq5_3 = new Question("Encourages and supports my involvement in training and development activities and events.", fiveWayAcceptability);
        Question mrq5_4 = new Question("Encourages and supports my involvement in community activities and events.", fiveWayAcceptability);
        Question mrq5_5 = new Question("Encourages and supports my involvement in company activities and events.", fiveWayAcceptability);
        Question mrq5_6 = new Question("Actions and behaviors are consistent with words.", fiveWayAcceptability);
        Question mrq5_7 = new Question("Is trustworthy.", fiveWayAcceptability);
        Question mrq5_8 = new Question("Role model for continuous improvement.", fiveWayAcceptability);
        Question mrq5_9 = new Question("Uses a coaching management style, rather than an authoritarian boss management style.", fiveWayAcceptability);
        Question mrq5_10 = new Question("Supports me, helps me achieve results", fiveWayAcceptability);
        Question mrq5_11 = new Question("Supports a customer service approach for both internal and external customers.", fiveWayAcceptability);
        Question mrq5_12 = new Question("Deals with issues that need to be addressed.", fiveWayAcceptability);
        Question mrq5_13 = new Question("Provides a clear sense of purpose and direction, roles and responsibilities, for me individually and for our group team members.", fiveWayAcceptability);

        //General Feedback
        Question mrq6_1 = new Question("What activities, behavior, feedback or coaching would you like your manager to stop doing? Please explain.",Stream.of(emptyComment).collect(Collectors.toList()));
        Question mrq6_2 = new Question("List and briefly describe examples of the behavior, activities, feedback or coaching your manager has provided that makes your job and work environment more enjoyable and meaningful to you.",Stream.of(emptyComment).collect(Collectors.toList()));
        Question mrq6_3 = new Question("Please provide comments that you feel will be meaningful for your manager to sustain or improve his or her effectiveness.",Stream.of(emptyComment).collect(Collectors.toList()));

        Survey managerEffectivenessSurvey = new Survey(Stream.of(
                mrq1_1,mrq1_2,mrq1_3,mrq1_4,mrq1_5,mrq1_6,mrq1_7,mrq1_8,mrq1_9,
                mrq2_1,mrq2_2,mrq2_3,mrq2_4,mrq2_5,
                mrq3_1,mrq3_2,mrq3_3,mrq3_4,mrq3_5,mrq3_6,mrq3_7,mrq3_8,mrq3_9,
                mrq4_1,mrq4_2,mrq4_3,
                mrq5_1, mrq5_2,mrq5_3,mrq5_4,mrq5_5,mrq5_6,mrq5_7,mrq5_8,mrq5_9,mrq5_10,mrq5_11,mrq5_12,mrq5_13,
                mrq6_1,mrq6_2,mrq6_3
        ).collect(Collectors.toList()), "Manager Effectiveness Evaluation", "Internal Surveys");

        //endregion

//        region Submissions
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
        Question qText = null;
        for(int i=0;i<survey4.getQuestions().size();i++) {
            qText = survey4.getQuestions().get(i);
            if(i == 1 || i == 4) {
                answers4.put(qText, new Comment(i==1 ? "I Love Aston Technologies! Also this is a great test." : "Good test, I guess."));
            } else
                answers4.put(qText, qText.getChoices().get(1));
        }
        surveySubmission4.setSubmittedAnswers(answers4);

        //load dummy submission into survey5
//        SurveySubmission surveySubmission5 = new SurveySubmission();
//        surveySubmission5.setSurvey(survey5);
//        HashMap<Question,Choice> answers5 = new HashMap<>();
//        for(Question q : survey5.getQuestions())
//            answers5.put(q,q.getChoices().get(1));
//        surveySubmission5.setSubmittedAnswers(answers5);
        //endregion

        //load data
        surveyService.saveSurveyList(Stream.of(survey1,survey2,survey3,survey4,survey5,managerEffectivenessSurvey).collect(Collectors.toList()));
        surveySubmissionService.saveSurveySubmissionList(Stream.of(surveySubmission1,surveySubmission2,surveySubmission3,surveySubmission4).collect(Collectors.toList()));

    }

}
