<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if handle??>
        <#assign u = c.findBy(users, "handle", handle)!/>
    </#if>
    <#if user_id??>
            <#assign u = c.findBy(users, "id", user_id)!/>
    </#if>

    <#if u?has_content>
        <#assign handle = u.handle!/>
        <#assign next_u = c.nextBy(users, "handle", handle)!/>
        <#assign prev_u = c.prevBy(users, "handle", handle)!/>
        <div class="userbox">
            <div class="userInfo">
            <h2><@c.userlink u/></h2>
            <#assign items=[
                        {"name": "id", "value": u.id},
                        {"name": "handle", "value": u.handle},
                        {"name": "name", "value": u.name},
                        {"name": "number of posts", "value": (posts?filter(p -> p.user_id == u.id))?size}
                        ]/>
                        <ul>
                            <#list items as item>
                                <li>
                                    ${item.name}: ${item.value}
                                </li>
                            </#list>
                        </ul>
                        <a href="/posts?user_id=${u.id}">Posts of ${u.handle}</a>
            </div>
            <img alt="User Image" src="/img/no-title.jpg">
        </div>
        <#if prev_u?has_content>
            <a href="/user?handle=${prev_u.handle}" class="arrow">⟵</a>
        <#else>
            ⟵
        </#if>
        <#if next_u?has_content>
             <a href="/user?handle=${next_u.handle}" class="arrow">⟶</a>
        <#else>
            ⟶
        </#if>
    <#else>
        No such user
    </#if>
</@c.page>
