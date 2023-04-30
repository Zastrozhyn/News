package ru.clevertec.ecl.web.controller;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.service.service.NewsService;
import ru.clevertec.ecl.web.dto.NewsDto;
import ru.clevertec.ecl.web.mapper.NewsMapper;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsMapper mapper = Mappers.getMapper(NewsMapper.class);
    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsDto createNews(@RequestBody NewsDto newsDto) {
        return mapper.mapToDto(newsService.createNews(mapper.mapToEntity(newsDto)));
    }

    @GetMapping("/{id}")
    public NewsDto findNewsById(@PathVariable Long id) {
        return mapper.mapToDto(newsService.findNewsById(id));
    }

    @GetMapping()
    public List<NewsDto> findAllNews(Pageable pageable) {
        return mapper.mapToDto(newsService.findAllNews(pageable));
    }

    @DeleteMapping(value = "/{newsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable(name = "newsId") Long id) {
        newsService.deleteNews(id);
    }
}
