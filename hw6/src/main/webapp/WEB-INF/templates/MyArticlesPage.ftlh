<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="myArticles datatable">
        <div class="caption">MyArticles</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Hide/Show</th>
            </tr>
            </thead>
            <tbody>
            <#list articles as a>
                <tr>
                    <td>${a.id}</td>
                    <td>${a.title}</td>
                    <td><button name="hideShow" class="hideShow" data-id="${a.id}" data-hidden="${a.hidden?c}">
                    <#if a.hidden>Show<#else>Hide</#if></button></td>
                </tr>
            <#else>
                <tr class="noData">
                    <td colspan="3">No data</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
    <script>
        $(function () {
            $(".myArticles .hideShow").click(function () {
                const articleId = $(this).attr("data-id");
                const isHidden = $(this).attr("data-hidden") === "true";

                const newHidden = !isHidden;
                const $btn = $(this);
                ajax({
                    action: "setHidden",
                    data: {
                        id: articleId,
                        hidden: newHidden
                    },
                    success: function (response) {
                        if (newHidden) {
                            $btn.text("Show");
                        } else {
                            $btn.text("Hide");
                        }

                        $btn.attr("data-hidden", newHidden);
                    }
                });
            });
        });
        </script>
</@c.page>
