package ru.clevertec.ecl.repository.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.repository.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("prod")
@Qualifier("commentDao")
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByTextContainingIgnoreCase(Pageable pageable, String text);
}
