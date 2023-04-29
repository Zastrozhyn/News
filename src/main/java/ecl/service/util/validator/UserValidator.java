package ecl.service.util.validator;

import ecl.repository.entity.User;
import org.springframework.stereotype.Component;

import static ecl.service.util.validator.ValidationConstant.*;

@Component
public class UserValidator {
    public boolean isUserValid(User user) {
        return isUserNameValid(user.getName());
    }

    private boolean isUserNameValid(String name) {
        return name != null && name.length() >= MIN_USER_NAME_LENGTH && name.length() <= MAX_USER_NAME_LENGTH;
    }
}
