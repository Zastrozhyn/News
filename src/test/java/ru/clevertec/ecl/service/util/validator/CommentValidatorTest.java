package ru.clevertec.ecl.service.util.validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.ecl.repository.entity.Comment;

import static org.junit.jupiter.api.Assertions.*;

class CommentValidatorTest {

    private static CommentValidator validator;
    private static final String NOT_VALID_TEXT = "a";

    @BeforeAll
    static void setUp() {
        validator = new CommentValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"text", "another-text", "QWERTY", "qa"})
    void checkIsCommentValidReturnTrue(String text) {
        Comment comment = new Comment();
        comment.setText(text);
        assertTrue(validator.isCommentValid(comment));
    }

    @Test
    void checkIsCommentNotValid() {
        Comment comment = new Comment();
        comment.setText(NOT_VALID_TEXT);
        assertFalse(validator.isCommentValid(comment));
    }
}