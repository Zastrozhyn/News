package ecl.service.service;

import ecl.repository.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment createUser(Comment comment);

    Comment findUserById(Long id);

    List<Comment> findAllUser(Integer page, Integer pageSize);

    Comment updateUser(Comment comment);

    void deleteUser(Long id);
}
