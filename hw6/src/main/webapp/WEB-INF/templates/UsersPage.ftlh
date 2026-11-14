<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
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
                <#if user?? && user.admin><th>Admin</th></#if>
            </tr>
            </thead>
            <tbody>
            <#list users as u>
                <tr>
                    <td>${u.id}</td>
                    <td>${u.login}</td>
                    <td>${u.creationTime?datetime}</td>
                    <#if user?? && user.admin>
                    <td><button name="enableDisable" class="enableDisable" data-id="${u.id}" data-admin="${u.admin?c}">
                                        <#if u.admin>Disable<#else>Enable</#if></button></td>
                    </#if>
                </tr>
            <#else>
                <tr class="noData">
                    <#if user?? && user.admin><td colspan="4">No data</td><#else><td colspan="3">No data</td></#if>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
    <script>
            $(function () {
                $(".users .enableDisable").click(function () {
                    const userId = $(this).attr("data-id");
                    const admin = $(this).attr("data-admin") === "true";

                    const newAdmin = !admin;
                    const $btn = $(this);
                    ajax({
                        action: "setAdmin",
                        data: {
                            id: userId,
                            admin: newAdmin
                        },
                        success: function (response) {
                            if (newAdmin) {
                                $btn.text("Disable");
                            } else {
                                $btn.text("Enable");
                            }

                            $btn.attr("data-admin", newAdmin);
                        }
                    });
                });
            });
            </script>
</@c.page>
