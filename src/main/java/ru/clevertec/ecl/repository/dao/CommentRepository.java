package ru.clevertec.ecl.repository.dao;

import ru.clevertec.ecl.repository.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
