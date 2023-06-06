<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<@c.page>
<#-- @ftlvariable name="userPosts" type="ru.itmo.web.hw4.model.Post[]" -->
    <#if user_id?has_content>
        <#assign userPosts = c.findAll(posts, "user_id", user_id)!/>
    </#if>

    <#if userPosts?has_content>
        <#list userPosts?reverse as post>
            <@c.mainPostDisplay post false/>
        </#list>
    <#else>
        No posts for this user
    </#if>
</@c.page>
