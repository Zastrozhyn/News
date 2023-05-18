package ru.clevertec.ecl.service.service;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.repository.entity.News;
import ru.clevertec.ecl.web.dto.SearchFilter;

import java.util.List;

public interface NewsService {
    /**
     * Create news
     * @param news
     * @return created news
     */
    News createNews(News news);

    /**
     * find news in database by id, throw entity exception, if it is not exist
     * @param newsId
     * @return news
     */
    News findNewsById(Long newsId);

    /**
     * Find a list of comments with pagination and sorting.
     * @param pageable parameter for a specific page
     * @return a list of comments.
     */
    List<News> findAllNews(Pageable pageable);

    /**
     * Update news in database
     * @param updatedNews
     * @return News
     */
    News updateNews(News updatedNews);

    /**
     * Delete news from database
     * @param newsId
     */
    void deleteNews(Long newsId);

    /**
     * Find a list of news with pagination and sorting.
     * @param pageable parameter for a specific page
     * @param filter parameter for searching by text
     * @return a list of news.
     */
    List<News> findAllByFilter(Pageable pageable, SearchFilter filter);
}
