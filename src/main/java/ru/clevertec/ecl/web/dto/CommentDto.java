package ru.clevertec.ecl.web.dto;

import lombok.*;
import ru.clevertec.ecl.repository.entity.User;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private LocalDateTime creationTime;
    private String text;
    private User user;
}
