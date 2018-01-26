package com.aston.survey.domain.repositories;

import com.aston.survey.domain.Survey;
import org.springframework.data.repository.CrudRepository;

public interface SurveyRepository extends CrudRepository<Survey, Long> {
}
