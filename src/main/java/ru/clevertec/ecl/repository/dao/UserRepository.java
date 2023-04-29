package ru.clevertec.ecl.repository.dao;

import ru.clevertec.ecl.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
