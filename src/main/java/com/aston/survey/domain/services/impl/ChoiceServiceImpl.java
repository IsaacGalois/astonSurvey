package com.aston.survey.domain.services.impl;

import com.aston.survey.domain.Choice;
import com.aston.survey.domain.repositories.ChoiceRepository;
import com.aston.survey.domain.services.ChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChoiceServiceImpl implements ChoiceService {

    @Autowired
    private ChoiceRepository choiceRepository;

    @Override
    public Iterable<Choice> listAllChoices() {
        return choiceRepository.findAll();
    }

    @Override
    public Choice getChoiceById(Long id) {
        return choiceRepository.findOne(id);
    }

    @Override
    public Choice saveChoice(Choice choice) {
        return choiceRepository.save(choice);
    }

    @Override
    public Iterable<Choice> saveChoiceList(Iterable<Choice> choiceIterable) {
        return choiceRepository.save(choiceIterable);
    }

    @Override
    public void deleteChoice(Long id) {
        choiceRepository.delete(id);
    }
}
