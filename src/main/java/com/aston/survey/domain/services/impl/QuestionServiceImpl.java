package com.aston.survey.domain.services.impl;

import com.aston.survey.domain.Question;
import com.aston.survey.domain.repositories.QuestionRepository;
import com.aston.survey.domain.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

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
}
