package ru.clevertec.ecl.service.service.impl;

import ru.clevertec.ecl.repository.dao.CommentRepository;
import ru.clevertec.ecl.repository.entity.Comment;
import ru.clevertec.ecl.service.exception.EntityException;
import ru.clevertec.ecl.service.service.CommentService;
import ru.clevertec.ecl.service.util.validator.CommentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.service.exception.ExceptionCode;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentValidator commentValidator;
    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentValidator commentValidator) {
        this.commentValidator = commentValidator;
    }

    @Override
    public Comment createComment(Comment comment) {
        if (!commentValidator.isCommentValid(comment)) {
            throw new EntityException(ExceptionCode.NOT_VALID_COMMENT.getErrorCode());
        }
        return commentRepository.save(comment);
    }

    @Override
    public Comment findCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new EntityException(ExceptionCode.COMMENT_NOT_FOUND.getErrorCode()));
    }

    @Override
    public List<Comment> findAllComment(Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public Comment updateComment(Comment comment) {
        if (!commentValidator.isCommentValid(comment)) {
            throw new EntityException(ExceptionCode.NOT_VALID_COMMENT.getErrorCode());
        }
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new EntityException(ExceptionCode.COMMENT_NOT_FOUND.getErrorCode());
        }
        commentRepository.deleteById(id);
    }
}
