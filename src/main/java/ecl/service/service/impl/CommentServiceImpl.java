package ecl.service.service.impl;

import ecl.repository.dao.CommentRepository;
import ecl.repository.entity.Comment;
import ecl.service.exception.EntityException;
import ecl.service.service.CommentService;
import ecl.service.util.validator.CommentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static ecl.service.exception.ExceptionCode.*;

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
            throw new EntityException(NOT_VALID_COMMENT.getErrorCode());
        }
        return commentRepository.save(comment);
    }

    @Override
    public Comment findUserById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new EntityException(COMMENT_NOT_FOUND.getErrorCode()));
    }

    @Override
    public List<Comment> findAllUser(Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public Comment updateUser(Comment comment) {
        if (!commentValidator.isCommentValid(comment)) {
            throw new EntityException(NOT_VALID_COMMENT.getErrorCode());
        }
        return commentRepository.save(comment);
    }

    @Override
    public void deleteUser(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new EntityException(COMMENT_NOT_FOUND.getErrorCode());
        }
        commentRepository.deleteById(id);
    }
}
