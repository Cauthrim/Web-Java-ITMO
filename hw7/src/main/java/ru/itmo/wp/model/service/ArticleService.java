package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.DisplayArticle;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class ArticleService {
    private static final ArticleRepository articleRepository = new ArticleRepositoryImpl();
    private static final UserService userService = new UserService();

    public void validateArticle(User sessionUser, Article article) throws ValidationException {
        if (sessionUser.getId() != article.getUserId()) {
            throw new ValidationException("Author and authorized user do not match");
        }
        if (article.getTitle() == null || article.getTitle().strip().isEmpty()) {
            throw new ValidationException("Empty title is not allowed");
        }
        if (article.getTitle().length() > 255) {
            throw new ValidationException("Title of an article cannot be longer than 255 characters");
        }
        if (article.getText() == null || article.getText().strip().isEmpty()) {
            throw new ValidationException("Empty articles are not allowed");
        }
        if (article.getText().length() > 255) {
            throw new ValidationException("Text of an article cannot be longer than 255 characters");
        }
    }

    public Article find(long id) {
        return articleRepository.find(id);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public List<Article> findAllByUser(long userId) {
        return articleRepository.findAllByUser(userId);
    }

    public List<DisplayArticle> findAllForDisplay() {
        List<Article> articles = findAll();
        List<DisplayArticle> displayArticles = new ArrayList<>();
        for (Article article : articles) {
            displayArticles.add(new DisplayArticle(
                    article.getId(),
                    userService.find(article.getUserId()).getLogin(),
                    article.getTitle(),
                    article.getText(),
                    article.getCreationTime(),
                    article.getHidden()
            ));
        }
        return displayArticles;
    }

    public void switchVisibility(Article article, boolean switchFlag) {
        articleRepository.switchVisibility(article, switchFlag);
    }

    public void addArticle(Article article) {
        articleRepository.save(article);
    }
}
