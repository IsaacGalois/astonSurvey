package com.aston.survey.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SurveySubmissionVO {

    public SurveySubmissionVO(Integer numQuestions) {
        this.choiceArray = new String[numQuestions];
    }

//    NOTE: This is a String Array so that it can handle Comment textArea input, otherwise it will consist of Long choiceId values
//              that must be parsed.
    private String[] choiceArray;
}