window.notify = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "success"
    });
}

window.ajax = function (dataList, successFunction = () => {}) {
    $.ajax({
        type: "POST",
        url: "",
        data: dataList,
        dataType: "json",
        success: function (response) {
            successFunction(response);
            if(response["redirect"]) {
                location.href = response["redirect"];
            }
        }
    });
}
