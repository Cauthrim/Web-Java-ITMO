<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <script src="/js/app.js"></script>
    <div class="datatable">
        <div class="caption">Article</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Visibility</th>
            </tr>
            </thead>
            <tbody>
            <#list articles as article>
                <tr id=${article.id}>
                    <td>${article.id}</td>
                    <td>${article.title}</td>
                    <td><button type="button" data-articleId=${article.id}>${article.hidden?string("Show", "Hide")}</button></td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <script>
        $(".datatable button").click(function () {
            const flag = $(this).text() === "Hide" ? "true" : "false";
            ajax({action: "switchVisibility", articleId: $(this).attr("data-articleId"), switchFlag: flag},
                function (response) {
                    $(".datatable button[data-articleId="+response["articleId"]+"]").text(response["buttonText"]);
                })
        });
    </script>
</@c.page>