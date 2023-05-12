package ru.clevertec.ecl.service.integrationtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.repository.entity.Comment;
import ru.clevertec.ecl.service.exception.EntityException;
import ru.clevertec.ecl.service.exception.ExceptionCode;
import ru.clevertec.ecl.service.service.CommentService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommentServiceImplIntegrationTest extends BaseIntegrationTest{
    private static final long EXISTING_COMMENT_ID = 2;
    private static final long EXISTING_NEWS_ID = 2;
    private static final long EXISTING_USER_ID = 2;
    private static final long NON_EXISTING_COMMENT_ID = 100;
    private static final int PAGE = 0;
    private static final int PAGE_SIZE = 30;
    private static final Integer AMOUNT_OF_COMMENTS_IN_DB = 10;

    private final CommentService service;

    @Autowired
    public CommentServiceImplIntegrationTest(CommentService service) {
        this.service = service;
    }

    @Test
    void findAllShouldReturn10() {
        List<Comment> comments = service.findAllComment(PageRequest.of(PAGE, PAGE_SIZE));
        assertThat(comments).hasSize(AMOUNT_OF_COMMENTS_IN_DB);
    }

    @Test
    void checkFindByIdShouldThrowException() {
        int expectedErrorCode = ExceptionCode.COMMENT_NOT_FOUND.getErrorCode();
        EntityException actualException = assertThrows(EntityException.class, () -> service.findCommentById(NON_EXISTING_COMMENT_ID));
        assertThat(actualException.getErrorCode()).isEqualTo(expectedErrorCode);
    }

    @Test
    void checkFindByIdShouldReturnNotNull() {
        Comment comment = service.findCommentById(EXISTING_COMMENT_ID);
        assertThat(comment).isNotNull();
    }

    @Test
    void checkSaveUserShouldThrowException() {
        int expectedErrorCode = ExceptionCode.NOT_VALID_COMMENT.getErrorCode();
        Comment comment = new Comment();
        EntityException actualException
                = assertThrows(EntityException.class,
                () -> service.createComment(comment, EXISTING_USER_ID, EXISTING_NEWS_ID));
        assertThat(actualException.getErrorCode()).isEqualTo(expectedErrorCode);
    }
}
