package com.aston.survey.domain.services.impl;

import com.aston.survey.domain.Comment;
import com.aston.survey.domain.repositories.CommentRepository;
import com.aston.survey.domain.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Override
    public Iterable<Comment> listAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findOne(id);
    }

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Iterable<Comment> saveCommentList(Iterable<Comment> commentIterable) {
        return commentRepository.save(commentIterable);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.delete(id);
    }

    @Override
    public Comment getEmptyComment() {
        Comment emptyComment = commentRepository.getByCommentText("");

        //Below shouldn't happen if Database was seeded properly
        if(emptyComment == null) {
            emptyComment = new Comment("");
            commentRepository.save(emptyComment);
        }

        return emptyComment;
    }
}
