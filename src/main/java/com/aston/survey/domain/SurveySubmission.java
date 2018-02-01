package com.aston.survey.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveySubmission {

    public SurveySubmission(Survey survey, HashMap<Question,Choice> choices) {
        this.survey = survey;
        this.submittedAnswers = choices;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SurveySubmissionId")
    private Long id;

    @Version
    private Integer version;

    @ManyToOne(cascade = CascadeType.ALL)
    private Survey survey;

    @ManyToMany(cascade = CascadeType.ALL)
    private Map<Question,Choice> submittedAnswers;

}
