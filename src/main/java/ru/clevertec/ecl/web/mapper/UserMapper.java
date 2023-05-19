package ru.clevertec.ecl.web.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.ecl.repository.entity.User;
import ru.clevertec.ecl.web.dto.UserDto;

import java.util.Collection;
import java.util.List;

@Mapper
public interface UserMapper {
    User mapToEntity(UserDto model);

    UserDto mapToDto(User entity);

    List<UserDto> mapToDto(Collection<User> entities);
}
