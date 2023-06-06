<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.domain.DisplayTalk[]" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="targetUserLogin" type="java.lang.String" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div>
        <form method="post" action="" id="talkForm" class="talkForm">
            <input type="hidden" name="action" value="addTalk"/>

            <label for="users">Choose the receiver:</label>
            <select name="targetUserLogin" id="users" form="talkForm">
                <#list users as user>
                    <option value="${user.login}">${user.login}</option>
                </#list>
            </select>

            <div>
                <label for="text">Text of the message:</label>
            </div>
            <textarea id="text" name="text" rows="10" cols="60" form="talkForm"></textarea>
            <div class="button-field">
                <input type="submit" value="Send Message">
            </div>
            <#if error??>
                <div class="error">
                    ${error!}
                </div>
            </#if>
        </form>
    </div>
    <div class="datatable">
        <div class="caption">Messages</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Sender</th>
                <th>Receiver</th>
                <th>Text</th>
                <th>Date</th>
            </tr>
            </thead>
            <tbody>
            <#list talks as talk>
                <tr>
                    <td>${talk.id}</td>
                    <td>${talk.sourceUserName}</td>
                    <td>${talk.targetUserName}</td>
                    <td>${talk.text}</td>
                    <td>${talk.creationTime}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>