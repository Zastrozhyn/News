package ru.clevertec.ecl.service.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.repository.entity.User;

import java.util.List;

public interface UserService {
    /**
     * Create user
     * @param user
     * @return created user
     */
    User createUser(User user);

    /**
     * find news in database by id, throw entity exception, if it is not exist
     * @param userId
     * @return user
     */
    User findUserById(Long userId);


    /**
     * Find a list of users with pagination and sorting.
     * @param pageable parameter for a specific page
     * @return a list of users.
     */
    List<User> findAllUser(Pageable pageable);

    /**
     * Update user in database
     * @param updatedUser
     * @return User
     */
    User updateUser(User updatedUser);

    /**
     * Delete news from database
     * @param userId
     */
    void deleteUser(Long userId);
}
