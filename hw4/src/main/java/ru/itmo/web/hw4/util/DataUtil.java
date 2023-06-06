package ru.itmo.web.hw4.util;

import ru.itmo.web.hw4.model.Menu;
import ru.itmo.web.hw4.model.Post;
import ru.itmo.web.hw4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", User.UserColor.BLUE),
            new User(6, "pashka", "Pavel Mavrin", User.UserColor.RED),
            new User(9, "geranazavr555", "Georgiy Nazarov", User.UserColor.GREEN),
            new User(11, "tourist", "Gennady Korotkevich", User.UserColor.RED)
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "LongRead", "Этот раунд будет рейтинговым для участников с рейтингом менее 2100. Соревнование" +
                    " будет проводиться по немного расширенным правилам ICPC. Штраф за каждую неверную посылку до" +
                    " посылки, являющейся полным решением, равен 10 минутам. После окончания раунда будет период времени" +
                    " длительностью в 12 часов, в течение которого вы можете попробовать взломать абсолютно любое" +
                    " решение (в том числе свое). Причем исходный код будет предоставлен не только для чтения, но и для" +
                    " копирования.", 1),
            new Post(2, "Announcement", "Сегодняшняя лекция отменяется.", 9),
            new Post(3, "Somber Statement", "Пожалуйста, решайте домашки", 1),
            new Post(4, "Joke", "Надел мужик шляпу, а она ему как раз.", 6)
    );

    private static final List<Menu> MENUS = Arrays.asList(
            new Menu("/index", "Home", "index"),
            new Menu("/misc/help", "Help", "help"),
            new Menu("/users", "Users", "users"),
            new Menu("/contests", "Contests", "contests")
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        String uri = request.getRequestURI();
        String currentPage = uri.substring(uri.lastIndexOf("/") + 1);

        data.put("users", USERS);
        data.put("menus", MENUS);
        data.put("posts", POSTS);
        data.put("currentMenu", currentPage);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }
}
