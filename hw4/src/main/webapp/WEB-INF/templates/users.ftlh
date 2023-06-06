<#import "./common.ftlh" as c>

<@c.page>
    <div class="datatable">
        <div class="caption">Users</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Handle</th>
                <th>Name</th>
                <th>Number of Posts</th>
            </tr>
            </thead>
            <tbody>
            <#list users as user>
                <@c.userRow user/>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>