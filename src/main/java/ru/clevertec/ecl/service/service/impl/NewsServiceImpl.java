package ru.clevertec.ecl.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.annotation.Log;
import ru.clevertec.ecl.repository.dao.NewsRepository;
import ru.clevertec.ecl.repository.entity.News;
import ru.clevertec.ecl.service.exception.EntityException;
import ru.clevertec.ecl.service.service.NewsService;
import ru.clevertec.ecl.service.util.validator.NewsValidator;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.service.exception.ExceptionCode;
import ru.clevertec.ecl.web.dto.SearchFilter;

import java.util.List;

@Log
@Service
public class NewsServiceImpl implements NewsService {

    private final NewsValidator newsValidator;
    private final NewsRepository newsRepository;

    @Autowired
    public NewsServiceImpl(NewsValidator newsValidator, @Qualifier("newsDao")NewsRepository newsRepository) {
        this.newsValidator = newsValidator;
        this.newsRepository = newsRepository;
    }


    @Override
    public News createNews(News news) {
        if (!newsValidator.isNewsValid(news)) {
            throw new EntityException(ExceptionCode.NOT_VALID_NEWS.getErrorCode());
        }
        return newsRepository.save(news);
    }

    @Override
    @Cacheable(value = "news", key = "#newsId")
    public News findNewsById(Long newsId) {
        return newsRepository.findById(newsId).orElseThrow(() -> new EntityException(ExceptionCode.NEWS_NOT_FOUND.getErrorCode()));
    }

    @Override
    @Cacheable("news")
    public List<News> findAllNews(Pageable pageable) {
        return newsRepository.findAll(pageable).getContent();
    }

    @Override
    public News updateNews(News updatedNews) {
        if (!newsValidator.isNewsValid(updatedNews)) {
            throw new EntityException(ExceptionCode.NOT_VALID_NEWS.getErrorCode());
        }
        News news = findNewsById(updatedNews.getId());
        news.setText(updatedNews.getText());
        news.setTitle(updatedNews.getTitle());
        return newsRepository.save(news);
    }

    @Override
    @CacheEvict(value = "news", key = "#newsId", allEntries = true)
    public void deleteNews(Long newsId) {
        if (!newsRepository.existsById(newsId)) {
            throw new EntityException(ExceptionCode.NEWS_NOT_FOUND.getErrorCode());
        }
        newsRepository.deleteById(newsId);
    }

    @Override
    @Cacheable("news")
    public List<News> findAllByFilter(Pageable pageable, SearchFilter filter) {
        return newsRepository
                .findAllByTextContainingIgnoreCaseOrTitleContainingIgnoreCase(pageable, filter.text(), filter.title())
                .getContent();
    }
}
