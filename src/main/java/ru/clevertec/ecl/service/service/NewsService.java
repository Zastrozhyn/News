package ru.clevertec.ecl.service.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.repository.entity.News;

import java.util.List;

public interface NewsService {
    News createNews(News news);

    News findNewsById(Long newsId);

    List<News> findAllNews(Pageable pageable);

    News updateNews(News updatedNews);

    void deleteNews(Long newsId);
}
