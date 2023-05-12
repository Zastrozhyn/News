package ru.clevertec.ecl.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.repository.dao.CommentRepository;
import ru.clevertec.ecl.repository.entity.Comment;
import ru.clevertec.ecl.repository.entity.News;
import ru.clevertec.ecl.repository.entity.User;
import ru.clevertec.ecl.service.exception.EntityException;
import ru.clevertec.ecl.service.service.CommentService;
import ru.clevertec.ecl.service.service.NewsService;
import ru.clevertec.ecl.service.service.UserService;
import ru.clevertec.ecl.service.util.validator.CommentValidator;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.service.exception.ExceptionCode;
import ru.clevertec.ecl.web.dto.SearchFilter;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentValidator commentValidator;
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final NewsService newsService;

    @Autowired
    public CommentServiceImpl(CommentValidator commentValidator, @Qualifier("commentDaoProxy")CommentRepository commentRepository,
                              UserService userService, NewsService newsService) {
        this.commentValidator = commentValidator;
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.newsService = newsService;
    }

    @Override
    @Transactional
    public Comment createComment(Comment comment, Long userId, Long newsId) {
        if (!commentValidator.isCommentValid(comment)) {
            throw new EntityException(ExceptionCode.NOT_VALID_COMMENT.getErrorCode());
        }
        User user = userService.findUserById(userId);
        News news = newsService.findNewsById(newsId);
        comment.setNewsId(news.getId());
        comment.setUserId(user.getId());
        news.addComment(comment);
        newsService.updateNews(news);
        return comment;
    }

    @Override
    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new EntityException(ExceptionCode.COMMENT_NOT_FOUND.getErrorCode()));
    }

    @Override
    public List<Comment> findAllComment(Pageable pageable) {
        return commentRepository.findAll(pageable).getContent();
    }

    @Override
    public Comment updateComment(Comment updatedComment) {
        if (!commentValidator.isCommentValid(updatedComment)) {
            throw new EntityException(ExceptionCode.NOT_VALID_COMMENT.getErrorCode());
        }
        Comment comment = findCommentById(updatedComment.getId());
        comment.setText(updatedComment.getText());
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, Long newsId) {
        Comment comment = findCommentById(commentId);
        News news = newsService.findNewsById(newsId);
        news.removeComment(comment);
        commentRepository.deleteById(commentId);
        newsService.updateNews(news);
    }

    @Override
    public List<Comment> findByFilter(SearchFilter filter, Pageable pageable) {
        return commentRepository.findAllByTextContainingIgnoreCase(pageable, filter.text()).getContent();
    }
}
