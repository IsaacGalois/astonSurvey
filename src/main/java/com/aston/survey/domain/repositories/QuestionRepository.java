package com.aston.survey.domain.repositories;

import com.aston.survey.domain.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {
}
