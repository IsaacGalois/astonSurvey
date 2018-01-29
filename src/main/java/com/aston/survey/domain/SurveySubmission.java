package com.aston.survey.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveySubmission {

    public SurveySubmission(Survey survey, List<Choice> choices) {
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
    private List<Choice> submittedAnswers;
}
