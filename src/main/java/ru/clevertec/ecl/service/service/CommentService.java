package ru.clevertec.ecl.service.service;

import ru.clevertec.ecl.repository.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment);

    Comment findCommentById(Long id);

    List<Comment> findAllComment(Integer page, Integer pageSize);

    Comment updateComment(Comment comment);

    void deleteComment(Long id);
}
