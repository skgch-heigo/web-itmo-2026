<#import "./common.ftlh" as c>

<@c.page>
    <#assign post = c.findBy(posts, "id", post_id)!/>

    <#if post?has_content>
        <@c.postArticle post true/>
    <#else>
        No such post
    </#if>
</@c.page>