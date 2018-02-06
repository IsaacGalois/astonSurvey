package com.aston.survey.domain.services;

import com.aston.survey.domain.Comment;

public interface CommentService {

    Iterable<Comment> listAllComments();

    Comment getCommentById(Long id);

    Comment saveComment(Comment comment);

    Iterable<Comment> saveCommentList(Iterable<Comment> commentIterable);

    void deleteComment(Long id);

    Comment getEmptyComment();
}
