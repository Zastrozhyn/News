package ru.clevertec.ecl.web.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.ecl.repository.entity.News;
import ru.clevertec.ecl.web.dto.NewsDto;

import java.util.Collection;
import java.util.List;

@Mapper
public interface NewsMapper {
    News mapToEntity(NewsDto model);

    NewsDto mapToDto(News entity);

    List<NewsDto> mapToDto(Collection<News> entities);
}
