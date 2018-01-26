package com.aston.survey.bootstrap;

import com.aston.survey.domain.Choice;
import com.aston.survey.domain.Question;
import com.aston.survey.domain.Survey;
import com.aston.survey.domain.repositories.QuestionRepository;
import com.aston.survey.domain.repositories.SurveyRepository;
import com.aston.survey.domain.services.ChoiceService;
import com.aston.survey.domain.services.QuestionService;
import com.aston.survey.domain.services.SurveyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SeedData implements CommandLineRunner{

    private final SurveyRepository surveyRepository;
    private final QuestionService questionService;
    private final ChoiceService choiceService;

    public SeedData(SurveyRepository surveyRepository, QuestionService questionService, ChoiceService choiceService)
    {
        this.surveyRepository = surveyRepository;
        this.questionService = questionService;
        this.choiceService = choiceService;
    }

    @Transactional
    @Override
    public void run(String...strings) throws Exception {

        if(surveyRepository.count() == 0) {
            //only load data if no data loaded
            initData();
        }
    }

    private void initData() {
        final List<Choice> yesNoMaybe = Stream.of(new Choice("Yes"),new Choice("No"),new Choice("Maybe")).collect(Collectors.toList());
        final List<Choice> fiveWaySatisfaction = Stream.of(
                new Choice("Not at all satisfied"),new Choice("Somewhat dissatisfied"),new Choice("Neither satisfied nor dissatisfied"),
                new Choice("Somewhat satisfied"),new Choice("Completely satisfied")).collect(Collectors.toList());

        //Survey 1 - test
        Question question1 = new Question(null,null,
                "What color is the sky?", Stream.of(new Choice("Red"),new Choice("Blue"),new Choice("Orange")).collect(Collectors.toList()));
        Question question2 = new Question(null,null,
                "What is the capitol of Minnesota?", Stream.of(new Choice("Saint Paul"),new Choice("Minneapolis"),new Choice("Duluth")).collect(Collectors.toList()));
        Question question3 = new Question(null,null,
                "How many feet are in a mile?", Stream.of(new Choice("99"),new Choice("1284"),new Choice("5280")).collect(Collectors.toList()));

        Survey survey1 = new Survey(null, null, Stream.of(question1,question2,question3).collect(Collectors.toList()), "test");

        //Survey 2 - interview follow up
        Question question4 = new Question(null,null,
                "Would you consider applying to Aston Technologies in the future?", yesNoMaybe);
        Question question5 = new Question(null,null,
                "Did something unexpected prevent you from interviewing with our team?", yesNoMaybe);
        Question question6 = new Question(null,null,
                "Did you complete the interview assignment?", yesNoMaybe);

        Survey survey2 = new Survey(null, null, Stream.of(question4,question5,question6).collect(Collectors.toList()), "interview follow up");

        //Survey 3 - satisfaction
//        Question question7 = new Question(null,null,
//                "How satisfied were you with the interview assignment difficulty?", fiveWaySatisfaction);
//        Question question8 = new Question(null,null,
//                "How satisfied were you with the application process?", fiveWaySatisfaction);
//        Question question9 = new Question(null,null,
//                "How satisfied were you with your application contact?", fiveWaySatisfaction);
//
//        Survey survey3 = new Survey(null, null, Stream.of(question7,question8,question9).collect(Collectors.toList()), "satisfaction");

        //load data
//        surveyRepository.save(Stream.of(survey1,survey2,survey3).collect(Collectors.toList()));
        surveyRepository.save(Stream.of(survey1,survey2).collect(Collectors.toList()));

    }

}
