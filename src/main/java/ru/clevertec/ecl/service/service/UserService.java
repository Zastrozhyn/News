package ru.clevertec.ecl.service.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.repository.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User findUserById(Long userId);

    List<User> findAllUser(Pageable pageable);

    User updateUser(User updatedUser);

    void deleteUser(Long userId);
}
