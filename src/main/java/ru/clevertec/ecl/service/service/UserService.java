package ru.clevertec.ecl.service.service;

import ru.clevertec.ecl.repository.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User findUserById(Long id);

    List<User> findAllUser(Integer page, Integer pageSize);

    User updateUser(User user);

    void deleteUser(Long id);
}
