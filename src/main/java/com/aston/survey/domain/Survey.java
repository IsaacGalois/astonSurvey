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
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SurveyId")
    private Long id;

    @Version
    private Integer version;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Question> questions;

    private String name;
    private String type;

    public Survey(List<Question> questions, String name, String type) {
        this.questions = questions;
        this.name = name;
        this.type = type;
    }
}