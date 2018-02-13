package com.aston.survey.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "QuestionId")
    private Long id;

    @Version
    private Integer version;

    private String questionText;

    //Fetch type must be lazy or else on get survey request (GET /takeSurvey/{id}) will do triple join of
    // surveys with questions with choices. So surveys will come back with question lists that have a separate
    // question entry for each choice that question has.
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Choice> choices;

}
