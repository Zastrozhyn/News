package ru.clevertec.ecl.repository.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Component;
import ru.clevertec.ecl.repository.cache.MyCache;
import ru.clevertec.ecl.repository.dao.NewsRepository;
import ru.clevertec.ecl.repository.entity.News;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Component
@Profile("dev")
@Qualifier("newsDao")
public class NewsDaoProxy implements NewsRepository {

    private final NewsRepository newsRepository;
    private final MyCache<News> cache;

    @Value("${cache.type}")
    private String cacheType;
    @Value("${cache.capacity}")
    private int cacheCapacity;

    @Autowired
    public NewsDaoProxy(NewsRepository newsRepository, MyCache<News> cache) {
        this.newsRepository = newsRepository;
        this.cache = cache;
    }


    @Override
    public Page<News> findAllByTextContainingIgnoreCaseOrTitleContainingIgnoreCase(Pageable pageable, String text, String title) {
        return newsRepository.findAllByTextContainingIgnoreCaseOrTitleContainingIgnoreCase(pageable, text, title);
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends News> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends News> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<News> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public News getOne(Long aLong) {
        return null;
    }

    @Override
    public News getById(Long aLong) {
        return null;
    }

    @Override
    public News getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends News> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends News> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends News> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends News> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends News> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends News> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends News, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends News> S save(S entity) {
        News news = newsRepository.save(entity);
        cache.put(news.getId(), news);
        return (S) news;
    }

    @Override
    public <S extends News> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<News> findById(Long aLong) {
        News news = cache.get(aLong);
        if(news == null){
            news = newsRepository.findById(aLong).orElse(null);
            if (news == null){
                return Optional.empty();
            }
            cache.put(aLong, news);
        }
        return Optional.of(news);
    }

    @Override
    public boolean existsById(Long aLong) {
        return newsRepository.existsById(aLong);
    }

    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    public List<News> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {
        newsRepository.deleteById(aLong);
    }

    @Override
    public void delete(News entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends News> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<News> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<News> findAll(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }
}
