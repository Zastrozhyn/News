package ru.clevertec.ecl.service.integrationtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.repository.entity.News;
import ru.clevertec.ecl.service.exception.EntityException;
import ru.clevertec.ecl.service.exception.ExceptionCode;
import ru.clevertec.ecl.service.service.NewsService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NewsServiceImplIntegrationTest extends BaseIntegrationTest {

    private static final long EXISTING_NEWS_ID = 2;
    private static final long NON_EXISTING_NEWS_ID = 100;
    private static final int PAGE = 0;
    private static final int PAGE_SIZE = 30;
    private static final Integer AMOUNT_OF_NEWS_IN_DB = 20;

    private final NewsService service;

    @Autowired
    public NewsServiceImplIntegrationTest(NewsService service) {
        this.service = service;
    }


    @Test
    void findAllShouldReturn20() {
        List<News> news = service.findAllNews(PageRequest.of(PAGE, PAGE_SIZE));
        assertThat(news).hasSize(AMOUNT_OF_NEWS_IN_DB);
    }

    @Test
    void checkFindByIdShouldThrowException() {
        int expectedErrorCode = ExceptionCode.NEWS_NOT_FOUND.getErrorCode();
        EntityException actualException = assertThrows(EntityException.class, () -> service.findNewsById(NON_EXISTING_NEWS_ID));
        assertThat(actualException.getErrorCode()).isEqualTo(expectedErrorCode);
    }

    @Test
    void checkFindByIdShouldReturnNotNull() {
        News news = service.findNewsById(EXISTING_NEWS_ID);
        assertThat(news).isNotNull();
    }

    @Test
    void checkSaveUserShouldThrowException() {
        int expectedErrorCode = ExceptionCode.NOT_VALID_NEWS.getErrorCode();
        News news = new News();
        EntityException actualException
                = assertThrows(EntityException.class, () -> service.createNews(news));
        assertThat(actualException.getErrorCode()).isEqualTo(expectedErrorCode);
    }
}
