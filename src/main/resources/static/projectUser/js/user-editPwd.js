$("#form-edit").validate({
    rules: {
        password: {
            required: true,
            minlength: 5,
            maxlength: 20
        },
        confirm: {
            required: true,
            minlength: 5,
            maxlength: 20,
            equalTo: "#password"
        }
    },
    messages: {
        password: {
            required: "请输入新密码",
            minlength: "密码不能小于6个字符",
            maxlength: "密码不能大于20个字符"
        },
        confirm: {
            required: "请再次输入新密码",
            equalTo: "两次密码输入不一致"
        }
    },
    submitHandler: function (form) {
        console.log("edit");
        edit();
    }
});


function edit() {
    // var dataFormJson = new FormData($("#form-edit"));
    var userPassword = $("#password").val();
    var userId = $("#userId").val();
    $.ajax({
        type: "POST",
        url: rootPath + "/admin/userManage/editPwd",
        data: {
            'userPassword': userPassword,
            'userId': userId
        },
        async: false,
        error: function (request) {
            $.modal.alertError("系统错误");
        },
        success: function (data) {
            console.log("成功了")
            $.operate.saveSuccess(data);
        }
    });
}
