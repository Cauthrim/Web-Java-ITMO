<#import "./common.ftlh" as c>

<@c.page>
    <#list posts?reverse as post>
        <@c.mainPostDisplay post true/>
    </#list>
</@c.page>
