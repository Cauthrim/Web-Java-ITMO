<#import "commons.ftlh" as c>

<@c.page>
    <script src="/js/app.js"></script>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th class="adminColumn">Admin</th>
            </tr>
            </thead>
            <tbody>
            <template class="userRow">
                <tr>
                    <td class="user-id"></td>
                    <td class="user_login"></td>
                    <td class="user-admin adminColumn">
                        <span></span>
                        <a class="adminLink" href="#"></a>
                    </td>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            const $noData = $(".users .noData");
            $noData.find("td").text("Loading...");

            ajax({action: "findAll"},
                function (response) {response["users"].forEach(function (user) {
                    $noData.hide();
                    const $tr = $($(".users .userRow").prop("content")).clone();
                    $tr.find(".user-id").text(user["id"]);
                    $tr.find(".user_login").text(user["login"]);
                    $tr.find("span").text(user["admin"] ? "true" : "false");
                    $tr.find("span").attr("data-userId", user["id"]);
                    $tr.find("a").text(user["admin"] ? "disable" : "enable");
                    $tr.find("a").attr("data-userId", user["id"]);
                    $tr.find(".user-admin").attr("data-userId", user["id"]);
                    $tr.find("tr").attr("data-userId", user["id"]);
                    $(".users tbody").append($tr);
                });

                if (response["user"] == null || !response["user"]["admin"]) {
                    $(".users .adminColumn").hide();
                }


                $(".users tr td:not('.user-admin')").click(function () {
                    ajax({action: "findUser", userId: $(this).attr("data-userId")},
                        function (response) {
                            notify(response["user"].creationTime);
                        })
                });

                $(".users .user-admin a").click(function (event) {
                    event.preventDefault();
                    const flag = $(this).text() === "enable" ? "true" : "false";
                    ajax({action: "switchAdmin", userId: $(this).attr("data-userId"), switchFlag: flag},
                        function (response) {
                            $(".users .user-admin span[data-userId="+response["userId"]+"]").text(response["adminText"]);
                            $(".users .user-admin a[data-userId="+response["userId"]+"]").text(response["linkText"]);
                        })
                });
            });
        })
    </script>
</@c.page>
