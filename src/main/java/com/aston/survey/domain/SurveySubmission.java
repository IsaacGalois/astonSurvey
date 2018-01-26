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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SurveyId")
    private Long id;

    @Version
    private Integer version;

    @ManyToOne(cascade = CascadeType.ALL)
    private Survey survey;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Choice> submittedAnswers;
}
