$("#updateForm").validate({
    rules: {
        userName: {
            required: true,
            minlength: 2,
            maxlength: 20
        },
        userPassword: {
            required: true,
            minlength: 5,
            maxlength: 20
        },
        confirm: {
            required: true,
            minlength: 5,
            maxlength: 20,
            equalTo: "#userPassword"
        }
    },
    messages: {
        userName: {
            required: "请输入新用户名",
            minlength: "用户名不能少于2个字符",
            maxlength: "用户名不能大于20个字符"
        },
        userPassword: {
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
        upload();
    }
});

function headerPic() {

    // 获取上传文件对象
    var file = $('#pic')[0].files[0];
    // 读取文件URL
    var reader = new FileReader();
    reader.readAsDataURL(file);
    // 阅读文件完成后触发的事件
    reader.onload = function () {
        // 读取的URL结果：this.result
        $("#headerPicture").attr("src", this.result);
    }
}

function upload() {
    // var dataFormJson = new FormData($("#form-edit"));
    var userName = $("#userName").val();
    var userPassword = $("#userPassword").val();
    var formData = new FormData(document.getElementById("updateForm"));
    $.ajax({
        type: 'post',
        url: '/admin/updatePersonalInfo',
        async: false,
        data: formData,
        cache: false,
        contentType: false,
        processData: false,
        success: function (result) {
            layer.msg("个人信息修改成功");
            history.go(0);//清除浏览器缓存,刷新,请求后台

        },
        error: function () {
            layer.msg('ERROR!');
        }
    });
    // $.ajax({
    //     type: "POST",
    //     url: rootPath + "/admin/userManage/editPwd",
    //     data: {
    //         'userPassword': userPassword,
    //         'userId': userId
    //     },
    //     async: false,
    //     error: function (request) {
    //         $.modal.alertError("系统错误");
    //     },
    //     success: function (data) {
    //         console.log("成功了")
    //         $.operate.saveSuccess(data);
    //     }
    // });
}