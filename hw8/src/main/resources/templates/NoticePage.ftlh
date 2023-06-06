<#import "commons.ftlh" as c>

<@c.page>
    <div class="notice">
        <div class="body">
            <form method="post" action="">
                <div class="contentName">
                    <label for="content">Content of the notice:</label>
                </div>
                <textarea name="content" rows="10" cols="40"></textarea>
                <@c.error "noticeForm.content"/>

                <div class="button-field">
                    <input type="submit" value="Add">
                </div>
            </form>
        </div>
    </div>
</@c.page>