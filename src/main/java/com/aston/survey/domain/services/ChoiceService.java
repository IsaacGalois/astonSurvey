package com.aston.survey.domain.services;

import com.aston.survey.domain.Choice;

public interface ChoiceService {

    Iterable<Choice> listAllChoices();

    Choice getChoiceById(Long id);

    Choice saveChoice(Choice choice);

    Iterable<Choice> saveChoiceList(Iterable<Choice> choiceIterable);

    void deleteChoice(Long id);

    Choice getEmptyChoice();
}
