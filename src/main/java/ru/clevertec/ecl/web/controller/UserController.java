package ru.clevertec.ecl.web.controller;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.service.service.CommentService;
import ru.clevertec.ecl.web.dto.CommentDto;
import ru.clevertec.ecl.web.dto.NewsDto;
import ru.clevertec.ecl.web.mapper.CommentMapper;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class UserController {

    private final CommentMapper mapper = Mappers.getMapper(CommentMapper.class);
    private final CommentService commentService;

    @Autowired
    public UserController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@RequestBody CommentDto commentDto) {
        return mapper.mapToDto(commentService.createComment(mapper.mapToEntity(commentDto)));
    }

    @GetMapping("/{id}")
    public CommentDto findCommentById(@PathVariable Long id) {
        return mapper.mapToDto(commentService.findCommentById(id));
    }

    @GetMapping()
    public List<NewsDto> findAllComments() {
        return null;
    }

    @DeleteMapping(value = "/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable(name = "commentId") Long id) {
        commentService.deleteComment(id);
    }
}
