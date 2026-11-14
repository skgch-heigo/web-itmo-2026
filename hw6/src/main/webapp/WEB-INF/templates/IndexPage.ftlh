<#import "commons.ftlh" as c>

<@c.page>
    <div class="articleList">
        <p class="noData">No data</p>
        <template>
            <article>
                <div class="title"></div>
                <div class="information"></div>
                <div class="body">
                    <p></p>
                </div>
                <div class="footer">
                    <div class="left">
                        <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                        <span class="positive-score">+0</span>
                        <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                    </div>
                    <div class="right">
                        <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                        <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                        <a href="#">0</a>
                    </div>
                </div>
            </article>
            </template>
    </div>
    <script>
    $(function () {
        const $articleList = $(".articleList");
        $articleList.find(".noData").text("Loading...");
        ajax({action: "findAll", dataType: "json",
            success: function (response) {
                $articleList.find(".noData").remove();

                response["articles"].forEach(function(article) {
                    const $tpl = $($articleList.find("template").prop("content")).clone();
                    $tpl.find(".title").text(article.title);
                    $tpl.find(".information").text(response["userLogins"][article.userId]);
                    $tpl.find(".body p").text(article.text);
                    $articleList.append($tpl);
                });
            }
        });
    });
    </script>
</@c.page>
