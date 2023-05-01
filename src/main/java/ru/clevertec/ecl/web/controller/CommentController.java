package ru.clevertec.ecl.web.controller;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.service.service.CommentService;
import ru.clevertec.ecl.web.dto.CommentDto;
import ru.clevertec.ecl.web.mapper.CommentMapper;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentMapper mapper = Mappers.getMapper(CommentMapper.class);
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public CommentDto findCommentById(@PathVariable Long id) {
        return mapper.mapToDto(commentService.findCommentById(id));
    }

    @GetMapping()
    public List<CommentDto> findAllComment(Pageable pageable) {
        return mapper.mapToDto(commentService.findAllComment(pageable));
    }

    @PutMapping("/{commentId}")
    public CommentDto updateComment(@PathVariable Long commentId,
                              @RequestBody CommentDto commentDto) {
        commentService.findCommentById(commentId);
        commentDto.setId(commentId);
        return mapper.mapToDto(commentService.updateComment(mapper.mapToEntity(commentDto)));
    }
}
