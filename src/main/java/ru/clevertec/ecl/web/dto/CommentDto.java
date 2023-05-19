package ru.clevertec.ecl.web.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String text;
    private Long userId;
    private Long newsId;
}
