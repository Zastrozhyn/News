package ru.clevertec.ecl.service.util.validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.ecl.repository.entity.User;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    private static UserValidator validator;
    private static final String NOT_VALID_NAME = "a";

    @BeforeAll
    static void setUp() {
        validator = new UserValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"name", "another-name", "QWERTY", "qa"})
    void checkIsCommentValidReturnTrue(String name) {
        User user = new User();
        user.setName(name);
        assertTrue(validator.isUserValid(user));
    }

    @Test
    void checkIsCommentNotValid() {
        User user = new User();
        user.setName(NOT_VALID_NAME);
        assertFalse(validator.isUserValid(user));
    }

    @Test
    void checkIsCommentNotValidByNullName() {
        User user = new User();
        assertFalse(validator.isUserValid(user));
    }

}