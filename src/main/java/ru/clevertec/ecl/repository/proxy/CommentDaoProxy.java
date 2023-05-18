package ru.clevertec.ecl.repository.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.repository.cache.MyCache;
import ru.clevertec.ecl.repository.dao.CommentRepository;
import ru.clevertec.ecl.repository.entity.Comment;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Component
@Profile("dev")
@Qualifier("commentDao")
public class CommentDaoProxy implements CommentRepository {

    private final CommentRepository commentRepository;
    private final MyCache<Comment> cache;

    @Autowired
    public CommentDaoProxy(CommentRepository commentRepository, MyCache<Comment> cache) {
        this.commentRepository = commentRepository;
        this.cache = cache;
    }

    @Override
    public Page<Comment> findAllByTextContainingIgnoreCase(Pageable pageable, String text) {
        return commentRepository.findAllByTextContainingIgnoreCase(pageable, text);
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Comment> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Comment> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Comment> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Comment getOne(Long aLong) {
        return null;
    }

    @Override
    public Comment getById(Long aLong) {
        return null;
    }

    @Override
    public Comment getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Comment> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Comment> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Comment> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Comment> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Comment> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Comment> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Comment, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Comment> S save(S entity) {
        Comment comment = commentRepository.save(entity);
        cache.put(comment.getId(), comment);
        return (S) comment;
    }

    @Override
    public <S extends Comment> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Comment> findById(Long aLong) {
        Comment comment = cache.get(aLong);
        if(comment == null){
            comment = commentRepository.findById(aLong).orElse(null);
            if (comment == null){
                return Optional.empty();
            }
            cache.put(aLong, comment);
        }
        return Optional.of(comment);
    }

    @Override
    public boolean existsById(Long aLong) {
        return commentRepository.existsById(aLong);
    }

    @Override
    public List<Comment> findAll() {
        return null;
    }

    @Override
    public List<Comment> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {
        commentRepository.deleteById(aLong);
    }

    @Override
    public void delete(Comment entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Comment> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Comment> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Comment> findAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }
}
