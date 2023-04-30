package ru.clevertec.ecl.web.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.ecl.repository.entity.Comment;
import ru.clevertec.ecl.web.dto.CommentDto;

import java.util.Collection;
import java.util.List;

@Mapper
public interface CommentMapper {
    Comment mapToEntity(CommentDto model);

    CommentDto mapToDto(Comment entity);

    List<CommentDto> mapToDto(Collection<Comment> entities);
}
