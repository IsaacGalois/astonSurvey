package com.aston.survey.domain.services.impl;

import com.aston.survey.domain.Choice;
import com.aston.survey.domain.Question;
import com.aston.survey.domain.repositories.QuestionRepository;
import com.aston.survey.domain.services.ChoiceService;
import com.aston.survey.domain.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ChoiceService choiceService;

    @Override
    public Iterable<Question> listAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Question getQuestionById(Long id) {
        return questionRepository.findOne(id);
    }

    @Override
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Iterable<Question> saveQuestionList(Iterable<Question> questionIterable) {
        return questionRepository.save(questionIterable);
    }

    @Override
    public void deleteQuestion(Long id) {
        questionRepository.delete(id);
    }

    @Override
    public Question addEmptyChoiceToFrontOfChoiceList(Question question) {
        List<Choice> choiceList = new ArrayList<>();
        choiceList.add(choiceService.getEmptyChoice());

        for(Choice choice : question.getChoices()) {
            if(choice != choiceService.getEmptyChoice())
                choiceList.add(choice);
        }

        question.setChoices(choiceList);

        return question;
    }
}
