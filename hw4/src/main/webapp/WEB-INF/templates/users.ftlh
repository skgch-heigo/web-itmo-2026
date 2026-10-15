<#import "./common.ftlh" as c>

<@c.page>
    <div id="datatable" class="datatable">
                <div class="caption">Users</div>
                <table>
                    <thead>
                    <tr>
                        <th>User</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list users as u>
                                <tr>
                                <td>
                                    <#if user?? && user.id == u.id>*</#if>
                                    <@c.userlink u/>
                                </td>
                                </tr>
                            </#list>
                    </tbody>
                </table>
            </div>
</@c.page>