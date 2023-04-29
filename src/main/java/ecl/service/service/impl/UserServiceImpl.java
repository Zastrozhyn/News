package ecl.service.service.impl;

import ecl.repository.dao.UserRepository;
import ecl.repository.entity.User;
import ecl.service.exception.EntityException;
import ecl.service.service.UserService;
import ecl.service.util.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static ecl.service.exception.ExceptionCode.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }

    @Override
    public User createUser(User user) {
        if (!userValidator.isUserValid(user)) {
            throw new EntityException(NOT_VALID_USER_NAME.getErrorCode());
        }
        return userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityException(USER_NOT_FOUND.getErrorCode()));
    }

    @Override
    public List<User> findAllUser(Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        if (!userValidator.isUserValid(user)) {
            throw new EntityException(NOT_VALID_USER_NAME.getErrorCode());
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityException(USER_NOT_FOUND.getErrorCode());
        }
        userRepository.deleteById(id);
    }
}
