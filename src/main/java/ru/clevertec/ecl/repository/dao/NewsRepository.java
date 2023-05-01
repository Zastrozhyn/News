package ru.clevertec.ecl.repository.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.repository.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
    Page<News> findAllByTextContainingIgnoreCaseOrTitleContainingIgnoreCase(Pageable pageable, String text, String title);
}
