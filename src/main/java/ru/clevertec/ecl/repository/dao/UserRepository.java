package ru.clevertec.ecl.repository.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import ru.clevertec.ecl.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("prod")
@Qualifier("userDao")
public interface UserRepository extends JpaRepository<User, Long> {
}
