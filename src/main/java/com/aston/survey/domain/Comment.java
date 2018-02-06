package com.aston.survey.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment extends Choice{

    public Comment(String commentText) {
        this.setChoiceText("Enter Comment Here...");
        this.commentText = commentText;
    }

    public Comment() {
        this.setChoiceText("Enter Comment Text...");
    }

//    because Comment is a Choice, it has a String choiceText field. This is used as placeholder text in the textArea.

    @Column(columnDefinition = "VARCHAR2")
    private String commentText;
}