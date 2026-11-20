window.notify = function (message, type) {
    $.notify(message, {
        position: "right bottom",
        className: type
    });
}
