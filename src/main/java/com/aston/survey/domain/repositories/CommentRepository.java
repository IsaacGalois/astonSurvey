package com.aston.survey.domain.repositories;

import com.aston.survey.domain.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    Comment getByCommentText(String commentText);
}
