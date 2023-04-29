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

    private final CommentRepository commentRepository;
    private final CommentValidator commentValidator;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, CommentValidator commentValidator) {
        this.commentRepository = commentRepository;
        this.commentValidator = commentValidator;
    }

    @Override
    public Comment createUser(Comment comment) {
        if (!commentValidator.isCommentValid(comment)) {
            throw new EntityException(ExceptionCode.NOT_VALID_COMMENT.getErrorCode());
        }
        return commentRepository.save(comment);
    }

    @Override
    public Comment findUserById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new EntityException(ExceptionCode.COMMENT_NOT_FOUND.getErrorCode()));
    }

    @Override
    public List<Comment> findAllUser(Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public Comment updateUser(Comment comment) {
        if (!commentValidator.isCommentValid(comment)) {
            throw new EntityException(ExceptionCode.NOT_VALID_COMMENT.getErrorCode());
        }
        return commentRepository.save(comment);
    }

    @Override
    public void deleteUser(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new EntityException(ExceptionCode.COMMENT_NOT_FOUND.getErrorCode());
        }
        commentRepository.deleteById(id);
    }
}
