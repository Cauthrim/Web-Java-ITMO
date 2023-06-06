<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-logo-with-telegram.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user true/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <ul>
                <#list menus as menu>
                    <li>
                        <@menuItem menu/>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <#list posts?reverse as post>
            <@sidePostDisplay post/>
        </#list>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2021 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro menuItem selectedMenu>
<#-- @ftlvariable name="selectedMenu" type="ru.itmo.web.hw4.model.Menu" -->
    <a href="${selectedMenu.href}">
        <#if currentMenu == selectedMenu.reference>
            <span class="currentMenu">${selectedMenu.name}</span>
        <#else>
            ${selectedMenu.name}
        </#if>
    </a>
</#macro>

<#macro userlink user nameOnly>
<#-- @ftlvariable name="user" type="ru.itmo.web.hw4.model.User" -->
<#-- @ftlvariable name="nameOnly" type="java.lang.Boolean" -->
<#-- @ftlvariable name="nickColor" type="java.lang.String" -->
    <#if nameOnly == true>
        <a href="/user?handle=${user.handle}">${user.name}</a>
    <#else>
        <#switch user.color>
            <#case "RED">
                <#assign nickColor="red"/>
                <#break>
            <#case "GREEN">
                <#assign nickColor="green"/>
                <#break>
            <#case "BLUE">
                <#assign nickColor="blue"/>
                <#break>
        </#switch>
        <a style="color:${nickColor}" class="coloredUser" href="/user?handle=${user.handle}">${user.name}</a>
    </#if>
</#macro>

<#macro generalPostDisplay post shorten>
<#-- @ftlvariable name="post" type="ru.itmo.web.hw4.model.Post" -->
<#-- @ftlvariable name="shorten" type="java.lang.Boolean" -->
<#-- @ftlvariable name="u" type="ru.itmo.web.hw4.model.User" -->
    <#assign u = findBy(users, "id", post.user_id)/>
    <article>
        <div class="title">${post.title}</div>
        <div class="information">By
            <#if u??>
                <@userlink u false/>
            <#else>
                Unknown
            </#if>
        </div>
        <div class="body">
            <p>
                <#if !shorten || post.text?length <= 350>
                    ${post.text}
                <#else>
                    ${post.text[0..349]}
                    …
                </#if>
            </p>
        </div>
    </article>
</#macro>

<#macro sidePostDisplay post>
<#-- @ftlvariable name="post" type="ru.itmo.web.hw4.model.Post" -->
    <section>
        <div class="header">Post#${post.id}</div>
        <@generalPostDisplay post true/>
        <div class="footer">
            <a href="/post?post_id=${post.id}">View all</a>
        </div>
    </section>
</#macro>

<#macro mainPostDisplay post shorten>
    <@generalPostDisplay post shorten/>
    <article>
    <ul class="attachment">
        <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
        <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
    </ul>
    <div class="footer">
        <div class="left">
            <img src="../img/voteup.png" title="Vote Up" alt="Vote Up"/>
            <span class="positive-score">+173</span>
            <img src="../img/votedown.png" title="Vote Down" alt="Vote Down"/>
        </div>
        <div class="right">
            <img src="../img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
            2 days ago
            <img src="../img/comments_16x16.png" title="Comments" alt="Comments"/>
            <a href="#">68</a>
        </div>
    </div>
    </article>
</#macro>

<#macro userRow user>
<#-- @ftlvariable name="user" type="ru.itmo.web.hw4.model.User" -->
    <tr>
        <td>${user.id}</td>
        <td><@userlink user false/></td>
        <td>${user.name}</td>
        <td>${countBy(posts, "user_id", user.id)}</td>
    </tr>
</#macro>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function findAll items key value>
    <#assign res = []/>
    <#list items as item>
        <#if item[key] == value>
            <#assign res = res + [ item ]/>
        </#if>
    </#list>
    <#return res/>
</#function>

<#function findPrevNext items key value step>
    <#list items as item>
        <#if item[key] == value>
            <#assign ind = item?index/>
        </#if>
    </#list>
    <#if items[ind+step]?has_content && items[ind+step][key]?is_string>
        <#return items[ind+step]/>
    </#if>
</#function>

<#function countBy items key value>
    <#assign counter = 0>
    <#list items as item>
        <#if item[key] == value>
            <#assign counter = counter + 1>
        </#if>
    </#list>
    <#return counter/>
</#function>

<#function tryID items ids params>
    <#list params as param>
        <#if param?has_content>
            <#return findBy(items, ids[param?index], params[param?index])/>
        </#if>
    </#list>
</#function>