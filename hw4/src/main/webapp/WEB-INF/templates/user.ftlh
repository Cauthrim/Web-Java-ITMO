<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#assign u = c.tryID(users, ["handle", "id"], [handle!, user_id!])!/>

    <#if u?has_content>
        <div class="user_card">
            <img class="picture" src="../../img/no-title.jpg" alt="no-title" title="no-title"/>
            <h2><@c.userlink u false/></h2>
            <ul>
                <li>id: ${u.id}</li>
                <li>handle: ${u.handle}</li>
                <li>name: ${u.name}</li>
                <li><a href="/posts?user_id=${u.id}">number of posts: ${c.countBy(posts, "user_id", u.id)}</a></li>
            </ul>
        </div>

    <#-- @ftlvariable name="next" type="ru.itmo.web.hw4.model.User" -->
    <#-- @ftlvariable name="prev" type="ru.itmo.web.hw4.model.User" -->
        <div class="arrows">
            <#assign prev = c.findPrevNext(users, "handle", u.handle, -1)!>
            <#if prev?has_content>
                <a href="/user?handle=${prev.handle}">←</a>
            <#else>
                ←
            </#if>
            <#assign next = c.findPrevNext(users, "handle", u.handle, 1)!>
            <#if next?has_content>
                <a href="/user?handle=${next.handle}">→</a>
            <#else>
                →
            </#if>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>
