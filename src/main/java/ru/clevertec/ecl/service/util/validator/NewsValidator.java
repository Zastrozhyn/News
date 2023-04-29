package ru.clevertec.ecl.service.util.validator;

import ru.clevertec.ecl.repository.entity.News;
import org.springframework.stereotype.Component;

import static ru.clevertec.ecl.service.util.validator.ValidationConstant.*;

@Component
public class NewsValidator {
    public boolean isNewsValid(News news) {
        return isNewsTextValid(news.getText()) && isNewsTitledValid(news.getTitle());
    }

    private boolean isNewsTextValid(String text) {
        return text != null && text.length() >= MIN_NEWS_TEXT_LENGTH && text.length() <= MAX_NEWS_TEXT_LENGTH;
    }

    private boolean isNewsTitledValid(String title) {
        return title != null && title.length() >= MIN_NEWS_TITLE_LENGTH && title.length() <= MAX_NEWS_TITLE_LENGTH;
    }
}
