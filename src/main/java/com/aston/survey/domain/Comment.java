package com.aston.survey.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

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

    //allow commentText to be stored as a VARCHAR2 (Max size 2,147,483,647 characters) <--h2
    //allow commentText to be stored as a LONGTEXT (Max size 4,294,967,295 characters) <--MySql
    @Lob
    private String commentText;
}