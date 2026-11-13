<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="newMessage form-box">
        <div class="header">New Message</div>
            <div class="body">
                <form class="message-form" method="post" action="">
                    <input type="hidden" name="action" value="sendMessage"/>
                    <div class="field">
                        <div class="name">
                            <label for="targetUserId">Target User</label>
                        </div>
                        <div class="value">
                            <select id="targetUserId" name="targetUserId"/>
                            <#list userLogins as uid, ulogin>
                                 <#if user.id?c != uid>
                                    <option value=${uid}>${ulogin}</option>
                                 </#if>
                            </#list>
                            </select>
                        </div>
                    </div>
                    <div class="field">
                         <div class="name">
                             <label for="text">Text</label>
                         </div>
                         <div class="value">
                             <input id="text" name="text"/>
                         </div>
                    </div>
                    <div class="button-field">
                        <input type="submit" value="sendMessage">
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="talks datatable">
        <div class="caption">Talks</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>User Source</th>
                <th>User Target</th>
                <th>User Text</th>
                <th>Created</th>
            </tr>
            </thead>
            <tbody>
                <#list talks as t>
                    <tr>
                        <td class="talk-id">${t.id}</td>
                        <td class="talk-sourceUserId">${userLogins[t.sourceUserId?c]}</td>
                        <td class="talk-targetUserId">${userLogins[t.targetUserId?c]}</td>
                        <td class="talk-text">${t.text}</td>
                        <td class="talk-creationTime">${t.creationTime?datetime}</td>
                    </tr>
                <#else>
                    <tr class="noData">
                        <td colspan="5">No talks</td>
                    </tr>
                </#list>
            </tbody>
        </table>
    </div>
</@c.page>
