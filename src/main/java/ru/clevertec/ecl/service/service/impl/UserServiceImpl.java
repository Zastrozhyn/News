package ru.clevertec.ecl.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.repository.dao.UserRepository;
import ru.clevertec.ecl.repository.entity.User;
import ru.clevertec.ecl.service.annotation.Log;
import ru.clevertec.ecl.service.exception.EntityException;
import ru.clevertec.ecl.service.service.UserService;
import ru.clevertec.ecl.service.util.validator.UserValidator;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.service.exception.ExceptionCode;

import java.util.List;

@Service
@Log
public class UserServiceImpl implements UserService {

    private final UserValidator userValidator;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserValidator userValidator, @Qualifier("userDao")UserRepository userRepository) {
        this.userValidator = userValidator;
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        if (!userValidator.isUserValid(user)) {
            throw new EntityException(ExceptionCode.NOT_VALID_USER_NAME.getErrorCode());
        }
        return userRepository.save(user);
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityException(ExceptionCode.USER_NOT_FOUND.getErrorCode()));
    }

    @Override
    public List<User> findAllUser(Pageable pageable) {
        return userRepository.findAll(pageable).getContent();
    }

    @Override
    public User updateUser(User updatedUser) {
        if (!userValidator.isUserValid(updatedUser)) {
            throw new EntityException(ExceptionCode.NOT_VALID_USER_NAME.getErrorCode());
        }
        User user = findUserById(updatedUser.getId());
        user.setName(updatedUser.getName());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityException(ExceptionCode.USER_NOT_FOUND.getErrorCode());
        }
        userRepository.deleteById(userId);
    }
}
