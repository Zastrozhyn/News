package ru.clevertec.ecl.service.util.validator;

import ru.clevertec.ecl.repository.entity.Comment;
import org.springframework.stereotype.Component;

import static ru.clevertec.ecl.service.util.validator.ValidationConstant.*;

@Component
public class CommentValidator {
    public boolean isCommentValid(Comment comment) {
        return isCommentTextValid(comment.getText());
    }

    private boolean isCommentTextValid(String text) {
        return text != null && text.length() >= MIN_COMMENT_TEXT_LENGTH && text.length() <= MAX_COMMENT_TEXT_LENGTH;
    }
}
