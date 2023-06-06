<#import "commons.ftlh" as c>

<@c.page>
    <#if user?has_content>
        <div class="user_card">
            <ul>
                <li>id: ${user.id}</li>
                <li>login: ${user.login}</li>
                <li>creation: ${user.creationTime}</li>
            </ul>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>