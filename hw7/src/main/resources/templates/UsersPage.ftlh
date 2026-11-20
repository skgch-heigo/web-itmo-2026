<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <#if user?? && user.admin><th>Enable/Disable</th></#if>
            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as u>
                <tr>
                    <td class="id">${u.id}</td>
                    <td class="login">${u.login}</td>
                    <td class="creationTime">${u.creationTime}</td>
                    <#if user?? && user.admin>
                    <td><button class="enableDisable" data-id="${u.id}" data-disabled="${u.disabled?c}">
                                        <#if u.disabled>Enable<#else>Disable</#if></button></td>
                    </#if>

                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
        <form id="disableEnableForm" method="post" action="/users/setDisabled" hidden>
            <input type="hidden" name="userId"   id="disableEnableFormUserId">
            <input type="hidden" name="status" id="disableEnableFormStatus">
        </form>
    </div>
    <script>
        $(function () {
            $(".users .enableDisable").click(function () {
                const userId = $(this).attr("data-id");
                const status = $(this).attr("data-disabled") === "true";

                const newStatus = !status;
                $("#disableEnableFormUserId").val(userId);
                $("#disableEnableFormStatus").val(newStatus);
                $("#disableEnableForm").submit();
            });
        });
    </script>
</@c.page>
