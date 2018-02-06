package com.aston.survey.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveySubmissionVO {

    public SurveySubmissionVO(Integer numQuestions) {
        this.choiceArray = new String[numQuestions];
    }

//    NOTE: This is a String Array so that it can handle Comment textArea input, otherwise it will consist of Long choiceId values
//              that must be parsed.
    private String[] choiceArray;
}