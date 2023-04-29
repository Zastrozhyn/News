package ru.clevertec.ecl.repository.dao;

import ru.clevertec.ecl.repository.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
