package ru.clevertec.ecl.service.util.validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.ecl.repository.entity.Comment;
import ru.clevertec.ecl.repository.entity.News;

import static org.junit.jupiter.api.Assertions.*;

class NewsValidatorTest {

    private static NewsValidator validator;
    private static final String NOT_VALID_TEXT_OR_TITLE = "a";
    private static final String VALID_TEXT_OR_TITLE = "awer";

    @BeforeAll
    static void setUp() {
        validator = new NewsValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"text", "another-text", "QWERTY", "qa"})
    void checkIsNewsValid(String text) {
        News news = new News();
        news.setTitle(text);
        news.setText(text);
        assertTrue(validator.isNewsValid(news));
    }

    @Test
    void checkIsTagNotValidByText() {
        News news = new News();
        news.setText(NOT_VALID_TEXT_OR_TITLE);
        news.setTitle(VALID_TEXT_OR_TITLE);
        assertFalse(validator.isNewsValid(news));
    }


    @Test
    void checkIsTagNotValidByTitle() {
        News news = new News();
        news.setText(VALID_TEXT_OR_TITLE);
        news.setTitle(NOT_VALID_TEXT_OR_TITLE);
        assertFalse(validator.isNewsValid(news));
    }

    @Test
    void checkIsTagNotValidByNullTitle() {
        News news = new News();
        news.setText(VALID_TEXT_OR_TITLE);
        assertFalse(validator.isNewsValid(news));
    }

    @Test
    void checkIsTagNotValidByNullText() {
        News news = new News();
        news.setTitle(VALID_TEXT_OR_TITLE);
        assertFalse(validator.isNewsValid(news));
    }

}