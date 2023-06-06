<#-- @ftlvariable name="title" type="java.lang.String" -->
<#-- @ftlvariable name="text" type="java.lang.String" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <script src="/js/app.js"></script>
    <div class="addArticle">
        <form method="post" action="" name="articleForm">
            <input type="hidden" name="action" value="addArticle"/>

            <div class="field">
                <div class="name">
                    <label for="title">Title of the article: </label>
                </div>
                <div class="value">
                    <input id="title" name="title"/>
                </div>
            </div>

            <div>
                <label for="text">Text of the article:</label>
            </div>
            <textarea id="text" name="text" rows="10" cols="60" form="articleForm"></textarea>

            <div class="error"></div>

            <div class="button-field">
                <input type="submit" value="Create">
            </div>
        </form>
    </div>

    <script>
        $(function () {
            $(".addArticle form").submit(function () {
                const title = $(this).find("input[name='title']").val();
                const text = $(this).find("textarea[name='text']").val();
                const $error = $(this).find(".error");

                ajax({action: "addArticle", title: title, text: text},
                    function (response) {
                        if (response["error"]) {
                            $error.text(response["error"]);
                        }
                    });

                return false;
            });
        })
    </script>
</@c.page>