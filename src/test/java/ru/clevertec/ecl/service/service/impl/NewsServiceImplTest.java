package ru.clevertec.ecl.service.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.repository.dao.NewsRepository;
import ru.clevertec.ecl.repository.entity.News;
import ru.clevertec.ecl.service.exception.EntityException;
import ru.clevertec.ecl.service.exception.ExceptionCode;
import ru.clevertec.ecl.service.service.NewsService;
import ru.clevertec.ecl.service.util.validator.NewsValidator;
import ru.clevertec.ecl.web.dto.SearchFilter;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NewsServiceImplTest {

    private static final long NEWS_ID = 1;
    private static final int EXPECTED_SIZE = 1;
    private static final long NON_EXISTING_ID = 10;
    private static final String TITLE = "title";
    private static final String TEXT = "text";
    private static final String NOT_VALID_TEXT = "e";
    private static News news;
    private static Pageable pageable;
    private NewsRepository newsRepository;
    private NewsService newsService;

    @BeforeAll
    static void init() {
        news = new News();
        news.setTitle(TITLE);
        news.setText(TEXT);
        pageable = Pageable.ofSize(10);
    }

    @BeforeEach
    void setup() {
        NewsValidator validator = new NewsValidator();
        newsRepository = Mockito.mock(NewsRepository.class);
        newsService = new NewsServiceImpl(validator, newsRepository);
    }

    @AfterEach
    void afterEachTest(){
        verifyNoMoreInteractions(newsRepository);
    }

    @Test
    void checkFindNewsByIdShouldReturnNews() {
        when(newsRepository.findById(NEWS_ID)).thenReturn(Optional.of(news));
        assertThat(newsService.findNewsById(NEWS_ID)).isEqualTo(news);
        verify(newsRepository).findById(NEWS_ID);
    }

    @Test
    void checkFindNewsByIdShouldThrowException() {
        when(newsRepository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());
        EntityException actualException = assertThrows(EntityException.class,
                () -> newsService.findNewsById(NON_EXISTING_ID));
        int expectedErrorCode = ExceptionCode.NEWS_NOT_FOUND.getErrorCode();
        assertThat(actualException.getErrorCode()).isEqualTo(expectedErrorCode);
        verify(newsRepository).findById(NON_EXISTING_ID);
    }

    @Test
    void checkFindAllShouldReturn1() {
        when(newsRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(news)));
        assertThat(newsService.findAllNews(pageable)).hasSize(EXPECTED_SIZE);
        verify(newsRepository).findAll(pageable);
    }

    @Test
    void checkFindNewsByFilterShouldReturn1() {
        SearchFilter filter = new SearchFilter(TEXT, TITLE);
        when(newsRepository.findAllByTextContainingIgnoreCaseOrTitleContainingIgnoreCase(pageable, TEXT, TITLE))
                .thenReturn(new PageImpl<>(List.of(news)));
        assertThat(newsService.findAllByFilter(pageable, filter)).hasSize(EXPECTED_SIZE);
        verify(newsRepository).findAllByTextContainingIgnoreCaseOrTitleContainingIgnoreCase(pageable, TEXT, TITLE);
    }

    @Test
    void checkCreateNewsShouldReturnNews() {
        when(newsRepository.save(news)).thenReturn(news);
        assertThat(newsService.createNews(news)).isEqualTo(news);
        verify(newsRepository).save(news);
    }

    @Test
    void checkCreateNewsShouldThrowException() {
        News news2 = new News();
        news2.setText(NOT_VALID_TEXT);
        EntityException actualException = assertThrows(EntityException.class,
                () -> newsService.createNews(news2));
        int expectedErrorCode = ExceptionCode.NOT_VALID_NEWS.getErrorCode();
        assertThat(actualException.getErrorCode()).isEqualTo(expectedErrorCode);
    }
}