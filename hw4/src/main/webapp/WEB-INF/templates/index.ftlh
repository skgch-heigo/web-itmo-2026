<#import "./common.ftlh" as c>

<@c.page>
    <div id="blog" class="topic">
    <#list posts?reverse as post>
            <@c.postArticle post/>
            </#list>
            </div>
</@c.page>
