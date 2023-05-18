package ru.clevertec.ecl.service.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.repository.entity.Comment;
import ru.clevertec.ecl.web.dto.SearchFilter;

import java.util.List;

public interface CommentService {

    /**
     * Create comment
     * @param comment
     * @param userId
     * @param newsId
     * @return created comment
     */
    Comment createComment(Comment comment, Long userId, Long newsId);

    /**
     * find comment in database by id, throw entity exception, if it is not exist
     * @param commentId
     * @return comment
     */
    Comment findCommentById(Long commentId);

    /**
     * Returns a list of comments with pagination and sorting.
     * @param pageable parameter for a specific page
     * @return a list of comments.
     */
    List<Comment> findAllComment(Pageable pageable);

    /**
     * update comment in database
     * @param updatedComment
     * @return Comment
     */
    Comment updateComment(Comment updatedComment);

    /**
     * Delete comment from database
     * @param commentId
     * @param newsId
     */
    void deleteComment(Long commentId, Long newsId);

    /**
     * Find a list of comments with pagination and sorting.
     * @param pageable parameter for a specific page
     * @param filter parameter for searching by text
     * @return a list of comments.
     */
    List<Comment> findByFilter(SearchFilter filter, Pageable pageable);
}
