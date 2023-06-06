<#-- @ftlvariable name="post_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<@c.page>
<#-- @ftlvariable name="p" type="ru.itmo.web.hw4.model.Post" -->
    <#assign p = c.tryID(posts, ["id"], [post_id!])!>

    <#if p?has_content>
        <@c.mainPostDisplay p false/>
    <#else>
        No such post
    </#if>
</@c.page>
