<#import "commons.ftlh" as c>

<@c.page>
    <#if userTarget?has_content>
        <div class="userbox">
            <div class="userInfo">
            <h2>${userTarget.login}</h2>
            <#assign items=[
                        {"name": "id", "value": userTarget.id},
                        {"name": "handle", "value": userTarget.login},
                        {"name": "Creation Time", "value": userTarget.creationTime}
                        ]/>
                        <ul>
                            <#list items as item>
                                <li>
                                    ${item.name}: ${item.value}
                                </li>
                            </#list>
                        </ul>
            </div>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>
