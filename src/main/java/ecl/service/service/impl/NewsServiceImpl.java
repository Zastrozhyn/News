package ecl.service.service.impl;

import ecl.repository.dao.NewsRepository;
import ecl.repository.entity.News;
import ecl.service.exception.EntityException;
import ecl.service.service.NewsService;
import ecl.service.util.validator.NewsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static ecl.service.exception.ExceptionCode.*;

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
            throw new EntityException(NOT_VALID_NEWS.getErrorCode());
        }
        return newsRepository.save(news);
    }

    @Override
    public News findNewsById(Long id) {
        return newsRepository.findById(id).orElseThrow(() -> new EntityException(NEWS_NOT_FOUND.getErrorCode()));
    }

    @Override
    public List<News> findAllNews(Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public News updateNews(News news) {
        if (!newsValidator.isNewsValid(news)) {
            throw new EntityException(NOT_VALID_NEWS.getErrorCode());
        }
        return newsRepository.save(news);
    }

    @Override
    public void deleteNews(Long id) {
        if (!newsRepository.existsById(id)) {
            throw new EntityException(NEWS_NOT_FOUND.getErrorCode());
        }
        newsRepository.deleteById(id);
    }
}
