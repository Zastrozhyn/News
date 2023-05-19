package ru.clevertec.ecl.service.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.repository.dao.UserRepository;
import ru.clevertec.ecl.repository.entity.User;
import ru.clevertec.ecl.service.exception.EntityException;
import ru.clevertec.ecl.service.exception.ExceptionCode;
import ru.clevertec.ecl.service.service.UserService;
import ru.clevertec.ecl.service.util.validator.UserValidator;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private static final long USER_ID = 1;
    private static final int EXPECTED_SIZE = 1;
    private static final long NON_EXISTING_ID = 10;
    private static final String NAME = "name";
    private static final String NOT_VALID_NAME = "e";
    private static User user;
    private static Pageable pageable;
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setup() {
        UserValidator userValidator = new UserValidator();
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userValidator, userRepository);
        user = new User();
        user.setName(NAME);
        pageable = Pageable.ofSize(20);
    }

    @Test
    void checkFindUserByIdShouldReturnNews() {
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        assertThat(userService.findUserById(USER_ID)).isEqualTo(user);
        verify(userRepository).findById(USER_ID);
    }

    @Test
    void checkFindUserByIdShouldThrowException() {
        when(userRepository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());
        EntityException actualException = assertThrows(EntityException.class,
                () -> userService.findUserById(NON_EXISTING_ID));
        int expectedErrorCode = ExceptionCode.USER_NOT_FOUND.getErrorCode();
        assertThat(actualException.getErrorCode()).isEqualTo(expectedErrorCode);
        verify(userRepository).findById(NON_EXISTING_ID);
    }

    @Test
    void checkFindAllShouldReturn1() {
        when(userRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(user)));
        assertThat(userService.findAllUser(pageable)).hasSize(EXPECTED_SIZE);
        verify(userRepository).findAll(pageable);
    }

    @Test
    void checkCreateUserShouldReturnUser() {
        when(userRepository.save(user)).thenReturn(user);
        assertThat(userService.createUser(user)).isEqualTo(user);
        verify(userRepository).save(user);
    }

    @Test
    void checkCreateUserShouldThrowException() {
        User user2 = new User();
        user.setName(NOT_VALID_NAME);
        EntityException actualException = assertThrows(EntityException.class,
                () -> userService.createUser(user2));
        int expectedErrorCode = ExceptionCode.NOT_VALID_USER_NAME.getErrorCode();
        assertThat(actualException.getErrorCode()).isEqualTo(expectedErrorCode);
    }
}