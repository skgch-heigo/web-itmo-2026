// Всплывающее уведомление в правом нижнем углу.
window.notify = function (message, isError) {
    $.notify(message, {
        position: "right bottom",
        className: isError ? "error" : "success"
    });
};


window.ajax = function (options) {

    const {
            action = "action",
            type = "POST",
            dataType = "json",
            data = {},
            form = null
        } = options;

    $.ajax({
                method: type,
                data: {
                    action,
                    ...data
                },
                dataType,
                success: function (response) {
                    if (response["error"]) {
                        form.find(".error").text(response["error"]);
                    } else if (response["redirect"]) {
                         location.href = response["redirect"];
                    }
                }
    });
};