<#import "./common.ftlh" as c>

<@c.page>
    <#list posts?reverse as post>
        <@c.postdisplay post true/>
    </#list>
</@c.page>
