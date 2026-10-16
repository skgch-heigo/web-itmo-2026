<#import "./common.ftlh" as c>

<@c.page>
    <#assign u = c.findBy(users, "id", user_id)!/>

    <#if u?has_content>
    <div id="blog" class="topic">
    <#list posts?filter(p -> p.user_id == u.id) as post>
            <@c.postArticle post/>
            </#list>
    </div>
    <#else>
        No such user
    </#if>
</@c.page>