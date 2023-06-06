package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.DisplayArticle;
import ru.itmo.wp.model.service.ArticleService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/** @noinspection unused*/
public class IndexPage {
    private static final ArticleService articleService = new ArticleService();
    private void action(HttpServletRequest request, Map<String, Object> view) {
        putMessage(request, view);
    }

    private void findAllArticles(HttpServletRequest request, Map<String, Object> view) {
        List<DisplayArticle> articles = articleService.findAllForDisplay();
        articles.sort((o1, o2) -> {
            if (o1.getCreationTime().equals(o2.getCreationTime())) {
                return 0;
            }
            return o1.getCreationTime().after(o2.getCreationTime()) ? 1 : -1;
        });
        view.put("articles", articles);
    }

    private void putMessage(HttpServletRequest request, Map<String, Object> view) {
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }
}
