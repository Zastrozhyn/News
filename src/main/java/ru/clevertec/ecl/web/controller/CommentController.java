package ru.clevertec.ecl.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.annotation.Log;
import ru.clevertec.ecl.service.service.CommentService;
import ru.clevertec.ecl.web.dto.CommentDto;
import ru.clevertec.ecl.web.dto.SearchFilter;
import ru.clevertec.ecl.web.mapper.CommentMapper;

import java.util.List;

@Log
@Tag(name = "Comment api", description = "Comment management")
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
    public List<CommentDto> findAllComment(@RequestParam(required = false, name = "search") String search,
                                           @RequestParam(required = false, name = "text") String text,
                                           Pageable pageable) {
        if(search != null){
            SearchFilter filter = new SearchFilter(text, null);
            return mapper.mapToDto(commentService.findByFilter(filter, pageable));
        }
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
