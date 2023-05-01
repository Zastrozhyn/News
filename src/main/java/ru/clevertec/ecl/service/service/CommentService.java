package ru.clevertec.ecl.service.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.repository.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment, Long userId, Long newsId);

    Comment findCommentById(Long commentId);

    List<Comment> findAllComment(Pageable pageable);

    Comment updateComment(Comment updatedComment);

    void deleteComment(Long commentId, Long newsId);
}
