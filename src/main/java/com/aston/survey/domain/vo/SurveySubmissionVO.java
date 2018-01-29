package com.aston.survey.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveySubmissionVO {

    public SurveySubmissionVO(Integer numQuestions) {
        this.choiceArray = new Long[numQuestions];
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SurveySubmissionVOId")
    private Integer id;

    @Version
    private Integer version;

    private Long[] choiceArray;
}