$("#form-edit").validate({

    submitHandler: function (form) {
        edit();
    }
});

function edit() {
    var dataFormJson = $("#form-edit").serialize();
    $.ajax({
        type: 'post',
        url: rootPath + "/project/edit",
        data: dataFormJson,
        async: false,
        error: function (request) {
            $.modal.alertError("系统错误");
        },
        success: function (data) {
            $.operate.saveSuccess(data);
        }
    });
}