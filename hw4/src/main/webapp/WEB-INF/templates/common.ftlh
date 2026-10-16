<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-logo-with-telegram.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user true/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/contests", "name": "Contests"},
            {"href": "/users", "name": "Users"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <a href="${item.href}" <#if item.href == uri>class="menuItemCurrent"</#if>>${item.name}</a>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <#list posts as post>
                    <@c.postSection post/>
                    </#list>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2021 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink user nameOnly=false>
<#-- @ftlvariable name="user" type="ru.itmo.web.hw4.model.User" -->
<a href="/user?handle=${user.handle}" <#if !nameOnly>class="handleLink user${user.color}"</#if>>${user.name}</a>
</#macro>

<#macro postArticle post fullText=false>
<article>
    <#assign u = c.findBy(users, "id", post.user_id)!/>
                    <div class="title">${post.title}</div>
                    <div class="information">By <@c.userlink u/></div>
                    <div class="body">
                        <p><#if fullText>${post.text}<#else>${post.text?truncate(350)}</#if></p>
                    </div>
                    <div class="footer">
                        <div class="left">
                            <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                            <span class="positive-score">+0</span>
                            <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                        </div>
                        <div class="right">
                            <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                            =:=
                            <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                            <a href="#">0</a>
                        </div>
                    </div>
                </article>
</#macro>

<#macro postSection post>
<section>
            <div class="header">Post #${post.id}</div>
            <div class="body"> ${post.text?truncate(350)}
            </div>
            <div class="footer">
                <a href="/post?post_id=${post.id}">View all</a>
            </div>
        </section>
</#macro>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function nextBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#if item?has_next>
                <#return items[item?index + 1]/>
            </#if>
            <#return null/>
        </#if>
    </#list>
</#function>

<#function prevBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#if !item?is_first>
                <#return items[item?index - 1]/>
            </#if>
            <#return null/>
        </#if>
    </#list>
</#function>
