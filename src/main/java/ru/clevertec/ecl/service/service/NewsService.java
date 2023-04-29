package ru.clevertec.ecl.service.service;

import ru.clevertec.ecl.repository.entity.News;

import java.util.List;

public interface NewsService {
    News createNews(News news);

    News findNewsById(Long id);

    List<News> findAllNews(Integer page, Integer pageSize);

    News updateNews(News news);

    void deleteNews(Long id);
}
