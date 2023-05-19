package ru.clevertec.ecl.service.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.repository.dao.CommentRepository;
import ru.clevertec.ecl.repository.entity.Comment;
import ru.clevertec.ecl.repository.entity.News;
import ru.clevertec.ecl.repository.entity.User;
import ru.clevertec.ecl.service.exception.EntityException;
import ru.clevertec.ecl.service.exception.ExceptionCode;
import ru.clevertec.ecl.service.service.CommentService;
import ru.clevertec.ecl.service.service.NewsService;
import ru.clevertec.ecl.service.service.UserService;
import ru.clevertec.ecl.service.util.validator.CommentValidator;
import ru.clevertec.ecl.web.dto.SearchFilter;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

class CommentServiceImplTest {

    private static final long COMMENT_ID = 1;
    private static final long USER_ID = 1;
    private static final long NEWS_ID = 1;
    private static final int EXPECTED_SIZE = 1;
    private static final long NON_EXISTING_COMMENT_ID = 10;
    private static final String TEXT = "text";
    private static final String NOT_VALID_TEXT = "e";
    private static Comment comment;
    private static Pageable pageable;
    private CommentRepository commentRepository;
    private UserService userService;
    private NewsService newsService;
    private CommentService commentService;

    @BeforeAll
    static void init() {
        comment = new Comment(TEXT);
        pageable = Pageable.ofSize(10);
    }

    @BeforeEach
    void setup() {
        CommentValidator commentValidator = new CommentValidator();
        userService = Mockito.mock(UserService.class);
        newsService = Mockito.mock(NewsService.class);
        commentRepository = Mockito.mock(CommentRepository.class);
        commentService = new CommentServiceImpl(commentValidator, commentRepository, userService, newsService);
    }

    @AfterEach
    void afterEachTest(){
        verifyNoMoreInteractions(commentRepository);
    }

    @Test
    void checkFindCommentByIdShouldReturnComment() {
        when(commentRepository.findById(COMMENT_ID)).thenReturn(Optional.of(comment));
        assertThat(commentService.findCommentById(COMMENT_ID)).isEqualTo(comment);
        verify(commentRepository).findById(COMMENT_ID);
    }

    @Test
    void checkFindCommentByIdShouldThrowException() {
        when(commentRepository.findById(NON_EXISTING_COMMENT_ID)).thenReturn(Optional.empty());
        EntityException actualException = assertThrows(EntityException.class,
                () -> commentService.findCommentById(NON_EXISTING_COMMENT_ID));
        int expectedErrorCode = ExceptionCode.COMMENT_NOT_FOUND.getErrorCode();
        assertThat(actualException.getErrorCode()).isEqualTo(expectedErrorCode);
        verify(commentRepository).findById(NON_EXISTING_COMMENT_ID);
    }

    @Test
    void checkFindAllShouldReturn1() {
        when(commentRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(comment)));
        assertThat(commentService.findAllComment(pageable)).hasSize(EXPECTED_SIZE);
        verify(commentRepository).findAll(pageable);
    }

    @Test
    void checkCreateShouldReturnComment() {
        User user = new User();
        user.setId(USER_ID);
        News news = new News();
        news.setId(NEWS_ID);
        when(userService.findUserById(USER_ID)).thenReturn(user);
        when(newsService.findNewsById(NEWS_ID)).thenReturn(news);
        assertThat(commentService.createComment(comment, USER_ID, NEWS_ID)).isEqualTo(comment);
        verify(userService).findUserById(USER_ID);
        verify(newsService).findNewsById(NEWS_ID);
    }

    @Test
    void checkFindCommentByFilterShouldReturn1() {
        SearchFilter filter = new SearchFilter(TEXT, null);
        when(commentRepository.findAllByTextContainingIgnoreCase(pageable, TEXT)).thenReturn(new PageImpl<>(List.of(comment)));
        assertThat(commentService.findByFilter(filter, pageable)).hasSize(EXPECTED_SIZE);
        verify(commentRepository).findAllByTextContainingIgnoreCase(pageable, TEXT);
    }

    @Test
    void checkCreateShouldThrowException() {
        Comment comment1 = new Comment(NOT_VALID_TEXT);
        EntityException actualException = assertThrows(EntityException.class,
                () ->commentService.createComment(comment1, USER_ID, NEWS_ID));
        int expectedErrorCode = ExceptionCode.NOT_VALID_COMMENT.getErrorCode();
        assertThat(actualException.getErrorCode()).isEqualTo(expectedErrorCode);
    }

}