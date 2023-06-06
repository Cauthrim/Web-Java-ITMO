package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ArticlePage {
    private final ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("message", "Unauthorized users cannot access article page");
            throw new RedirectException("/index");
        }
    }

    public void addArticle(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User sessionUser = (User) request.getSession().getAttribute("user");
        Article article = new Article();
        article.setUserId(sessionUser.getId());
        article.setTitle(request.getParameter("title"));
        article.setText(request.getParameter("text"));
        article.setHidden(false);
        articleService.validateArticle(sessionUser, article);
        articleService.addArticle(article);

        request.getSession().setAttribute("message", "Article created successfully");
        throw new RedirectException("/index");
    }
}
