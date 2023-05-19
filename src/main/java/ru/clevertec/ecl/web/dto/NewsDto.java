package ru.clevertec.ecl.web.dto;

import lombok.*;
import ru.clevertec.ecl.repository.entity.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class NewsDto {
    private Long id;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String title;
    private String text;
    private List<Comment> comments = new ArrayList<>();
}
