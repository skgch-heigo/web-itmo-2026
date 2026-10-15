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
        <#assign handle = u["handle"]!/>
        <#assign next_u = c.nextBy(users, "handle", handle)!/>
        <#assign prev_u = c.prevBy(users, "handle", handle)!/>
        <div class="userbox">
            <div class="userInfo">
            <h2><@c.userlink u/></h2>
            <ul>
                 <li>id: ${u.id}</li>
                 <li>handle: ${u.handle}</li>
                 <li>name: ${u.name}</li>
            </ul>
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
