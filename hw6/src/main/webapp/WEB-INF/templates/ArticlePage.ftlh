<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="newArticle form-box">
        <div class="header">New Article</div>
            <div class="body">
                <form class="article-form" method="post" action="">
                    <input type="hidden" name="action" value="postArticle"/>
                    <div class="field">
                        <div class="name">
                            <label for="title">Title</label>
                        </div>
                        <div class="value">
                            <input id="title" name="title"/>
                        </div>
                    </div>
                    <div class="field">
                         <div class="name">
                             <label for="text">Text</label>
                         </div>
                         <div class="value">
                             <textarea id="text" name="text" rows="20" cols="50"></textarea>
                         </div>
                    </div>
                    <div class="error"></div>
                    <div class="button-field">
                        <input type="submit" value="Create">
                    </div>
                </form>
            </div>
        </div>
    <script>
    $(function () {
            $(".article-form").submit(function () {
                const $form = $(this);
                const title = $form.find("input[name='title']").val();
                const text = $form.find("textarea[name='text']").val();
                ajax({action: "postArticle", data: {title, text}, form: $form});
                return false;
            });
        });
    </script>
</@c.page>
