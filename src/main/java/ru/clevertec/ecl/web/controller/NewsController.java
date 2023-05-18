package ru.clevertec.ecl.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.annotation.Log;
import ru.clevertec.ecl.repository.entity.Comment;
import ru.clevertec.ecl.service.service.CommentService;
import ru.clevertec.ecl.service.service.NewsService;
import ru.clevertec.ecl.web.dto.NewsDto;
import ru.clevertec.ecl.web.dto.SearchFilter;
import ru.clevertec.ecl.web.mapper.NewsMapper;

import java.util.List;

@Log
@Tag(name = "News api", description = "News management")
@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsMapper mapper = Mappers.getMapper(NewsMapper.class);
    private final NewsService newsService;
    private final CommentService commentService;

    @Autowired
    public NewsController(NewsService newsService, CommentService commentService) {
        this.newsService = newsService;
        this.commentService = commentService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsDto createNews(@RequestBody NewsDto newsDto) {
        return mapper.mapToDto(newsService.createNews(mapper.mapToEntity(newsDto)));
    }

    @PostMapping("/{newsId}")
    @ResponseStatus(HttpStatus.CREATED)
    public NewsDto createComment(@RequestParam(name = "userId") Long userId,
                                 @RequestParam(name = "text") String text,
                                 @PathVariable Long newsId) {
        commentService.createComment(new Comment(text), userId, newsId);
        return mapper.mapToDto(newsService.findNewsById(newsId));
    }

    @PatchMapping("/{newsId}")
    public NewsDto deleteComment(@RequestParam(name = "commentId") Long commentId,
                                 @PathVariable Long newsId) {
        commentService.deleteComment(commentId, newsId);
        return mapper.mapToDto(newsService.findNewsById(newsId));
    }

    @GetMapping("/{id}")
    public NewsDto findNewsById(@PathVariable Long id) {
        return mapper.mapToDto(newsService.findNewsById(id));
    }

    @GetMapping()
    public List<NewsDto> findAllNews(@RequestParam(required = false, name = "search") String search,
                                     @RequestParam(required = false, name = "text") String text,
                                     @RequestParam(required = false, name = "title") String title,
                                     Pageable pageable) {
        if(search != null){
            SearchFilter filter = new SearchFilter(text, title);
            return mapper.mapToDto(newsService.findAllByFilter(pageable, filter));
        }
        return mapper.mapToDto(newsService.findAllNews(pageable));
    }

    @DeleteMapping(value = "/{newsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNews(@PathVariable(name = "newsId") Long newsId) {
        newsService.deleteNews(newsId);
    }

    @PutMapping("/{newsId}")
    public NewsDto updateNews(@PathVariable(name = "newsId") Long newsId,
                              @RequestBody NewsDto newsDto){
        newsDto.setId(newsId);
        return mapper.mapToDto(newsService.updateNews(mapper.mapToEntity(newsDto)));
    }
}
