package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;
import java.util.List;

public interface ArticleRepository {
    Article find(long id);

    List<Article> findAll();

    List<Article> findAllByUser(long userId);

    void switchVisibility(Article article, boolean switchFlag);

    void save(Article article);
}
