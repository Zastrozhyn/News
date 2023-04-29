package ru.clevertec.ecl.service.service.impl;

import ru.clevertec.ecl.repository.dao.NewsRepository;
import ru.clevertec.ecl.repository.entity.News;
import ru.clevertec.ecl.service.exception.EntityException;
import ru.clevertec.ecl.service.service.NewsService;
import ru.clevertec.ecl.service.util.validator.NewsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.service.exception.ExceptionCode;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final NewsValidator newsValidator;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository, NewsValidator newsValidator) {
        this.newsRepository = newsRepository;
        this.newsValidator = newsValidator;
    }

    @Override
    public News createNews(News news) {
        if (!newsValidator.isNewsValid(news)) {
            throw new EntityException(ExceptionCode.NOT_VALID_NEWS.getErrorCode());
        }
        return newsRepository.save(news);
    }

    @Override
    public News findNewsById(Long id) {
        return newsRepository.findById(id).orElseThrow(() -> new EntityException(ExceptionCode.NEWS_NOT_FOUND.getErrorCode()));
    }

    @Override
    public List<News> findAllNews(Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public News updateNews(News news) {
        if (!newsValidator.isNewsValid(news)) {
            throw new EntityException(ExceptionCode.NOT_VALID_NEWS.getErrorCode());
        }
        return newsRepository.save(news);
    }

    @Override
    public void deleteNews(Long id) {
        if (!newsRepository.existsById(id)) {
            throw new EntityException(ExceptionCode.NEWS_NOT_FOUND.getErrorCode());
        }
        newsRepository.deleteById(id);
    }
}
