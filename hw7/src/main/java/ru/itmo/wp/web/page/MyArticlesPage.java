package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyArticlesPage {
    private static final ArticleService articleService = new ArticleService();

    public void switchVisibility(HttpServletRequest request, Map<String, Object> view) {
        Article article = articleService.find(Long.parseLong(request.getParameter("articleId")));
        User sessionUser = (User) request.getSession().getAttribute("user");
        boolean switchFlag = Boolean.parseBoolean(request.getParameter("switchFlag"));
        if (article != null && sessionUser != null && sessionUser.getId() == article.getUserId()) {
            if (article.getHidden() != switchFlag) {
                articleService.switchVisibility(article, switchFlag);
            }
            view.put("articleId", article.getId());
            view.put("buttonText", Boolean.parseBoolean(request.getParameter("switchFlag")) ? "Show" : "Hide");
        }
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("user") == "null") {
            request.getSession().setAttribute("message", "Unauthorized users do not have article pages");
            throw new RedirectException("/index");
        } else {
            view.put("articles", articleService.findAllByUser(((User)request.getSession().getAttribute("user")).getId()));
        }
    }
}
