<#import "./common.ftlh" as c>

<@c.page>
    <div id="blog" class="topic">
    <#list posts?reverse as post>
    <#assign u = c.findBy(users, "id", post.user_id)!/>
    <article>
                <div class="title">${post.title}</div>
                <div class="information">By <@c.userlink u/></div>
                <div class="body">
                    <p>${post.text?truncate(350)}</p>
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
            </#list>
            </div>
</@c.page>
