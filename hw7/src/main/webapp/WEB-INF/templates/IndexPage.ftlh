<#import "commons.ftlh" as c>

<@c.page>
    <script src="/js/app.js"></script>
    <div class="articles list">
        <template>
            <article>
                <div class="title"></div>
                <div class="information">By <span class="userLogin"></span></div>
                <div class="text"></div>
                <div class="footer">
                    <div class="left">
                        <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                        <span class="positive-score">+173</span>
                        <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                    </div>
                    <div class="right">
                        <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                        <span class="creationTime"></span>
                        <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                        <a href="#">68</a>
                    </div>
                </div>
            </article>
        </template>
        <div class="noData">
            <h1>No data</h1>
        </div>
    </div>

    <script>
        $(function () {
            const $noData = $(".articles .noData");
            $noData.find("h1").text("Loading articles...");

            ajax({action: "findAllArticles"},
                function(response) {
                    $noData.hide();
                    response["articles"].forEach(function(article) {
                        if (!article["hidden"]) {
                            const $art = $($(".articles template").prop("content")).clone();
                            $art.find(".title").text(article["title"]);
                            $art.find(".text").text(article["text"]);
                            $art.find(".userLogin").text(article["userLogin"]);
                            $art.find(".creationTime").text(article["creationTime"]);
                            $(".articles").prepend($art);
                        }
                    });
                });
        })
    </script>
</@c.page>
