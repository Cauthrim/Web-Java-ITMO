<#import "commons.ftlh" as c>

<@c.page>
    <#if post?has_content>
        <@c.post post></@c.post>
        <#if user?has_content>
            <div class="commentForm form">
                <div class="header">Add a Comment</div>
                <div class="body">
                    <form method="post" action="">
                        <input type="hidden" name="action" value="writeComment"/>
                        <div class="field">
                            <div class="name">
                                <label for="text">Text</label>
                            </div>
                            <div class="value">
                                <textarea id="text" name="text">${comment.text!}</textarea>
                            </div>
                            <@c.error "comment.text"/>
                        </div>
                        <div class="button-field">
                            <input type="submit" value="Add">
                        </div>
                    </form>
                </div>
            </div>
        </#if>
        <#list post.comments as comment>
            <@c.comment comment></@c.comment>
        </#list>
    <#else>
        No such post
    </#if>
</@c.page>