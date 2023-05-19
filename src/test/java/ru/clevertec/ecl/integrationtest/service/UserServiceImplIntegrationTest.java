package ru.clevertec.ecl.integrationtest.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.integrationtest.BaseIntegrationTest;
import ru.clevertec.ecl.repository.entity.User;
import ru.clevertec.ecl.service.exception.EntityException;
import ru.clevertec.ecl.service.exception.ExceptionCode;
import ru.clevertec.ecl.service.service.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceImplIntegrationTest extends BaseIntegrationTest {

    private static final String NOT_VALID_NAME = "a";
    private static final long EXISTING_USER_ID = 2;
    private static final long NON_EXISTING_USER_ID = 100;
    private static final int PAGE = 0;
    private static final int PAGE_SIZE = 10;
    private static final Integer AMOUNT_OF_USER_IN_DB = 3;

    private final UserService service;

    @Autowired
    UserServiceImplIntegrationTest(UserService service) {
        this.service = service;
    }

    @Test
    void findAllShouldReturn3() {
        List<User> users = service.findAllUser(PageRequest.of(PAGE, PAGE_SIZE));
        assertThat(users).hasSize(AMOUNT_OF_USER_IN_DB);
    }

    @Test
    void checkFindByIdShouldThrowException() {
        int expectedErrorCode = ExceptionCode.USER_NOT_FOUND.getErrorCode();
        EntityException actualException = assertThrows(EntityException.class, () -> service.findUserById(NON_EXISTING_USER_ID));
        assertThat(actualException.getErrorCode()).isEqualTo(expectedErrorCode);
    }

    @Test
    void checkFindByIdShouldReturnNotNull() {
        User user = service.findUserById(EXISTING_USER_ID);
        assertThat(user).isNotNull();
    }

    @Test
    void checkSaveUserShouldThrowException() {
        int expectedErrorCode = ExceptionCode.NOT_VALID_USER_NAME.getErrorCode();
        User user = new User();
        user.setName(NOT_VALID_NAME);
        EntityException actualException
                = assertThrows(EntityException.class, () -> service.createUser(user));
        assertThat(actualException.getErrorCode()).isEqualTo(expectedErrorCode);
    }

}