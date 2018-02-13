package com.aston.survey.domain.services;

import com.aston.survey.domain.Question;

public interface QuestionService {

    Iterable<Question> listAllQuestions();

    Question getQuestionById(Long id);

    Question saveQuestion(Question question);

    Iterable<Question> saveQuestionList(Iterable<Question> questionIterable);

    void deleteQuestion(Long id);

    Question addEmptyChoiceToFrontOfChoiceList(Question question);
}
