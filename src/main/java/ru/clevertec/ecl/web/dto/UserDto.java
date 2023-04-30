package ru.clevertec.ecl.web.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
}
