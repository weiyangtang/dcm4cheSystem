/**
 * 用户页面js
 */

$("#form-add").validate({
    rules: {
        userId: {
            required: true,
        },
        userName: {
            required: true,
        },
        deptName: {
            required: true,
        },
        userPassword: {
            required: true,
            minlength: 5,
            maxlength: 20
        },
        email: {
            required: true,
            email: true,
            remote: {
                url: rootPath + "/admin/userManage/checkEmailUnique",
                type: "post",
                dataType: "json",
                data: {
                    name: function () {
                        return $.trim($("#email").val());
                    }
                },
                dataFilter: function (data, type) {
                    if (data == "0") return true;
                    else return false;
                }
            }
        },
        phonenumber: {
            required: true,
            isPhone: true,
            remote: {
                url: rootPath + "system/user/checkPhoneUnique",
                type: "post",
                dataType: "json",
                data: {
                    name: function () {
                        return $.trim($("#phonenumber").val());
                    }
                },
                dataFilter: function (data, type) {
                    if (data == "0") return true;
                    else return false;
                }
            }
        },
    },
    messages: {
        "userId": {
            remote: ""
        },
        "userName": {
            remote: ""
        },
        "email": {
            remote: "Email已经存在"
        },
        "phonenumber": {
            remote: "手机号码已经存在"
        }
    },
    submitHandler: function (form) {
        add();
    }
});

/**
 * 用户添加方法
 */
function add() {
    var dataFormJson = $("#form-add").serialize();
    $.ajax({
        cache: true,
        type: "POST",
        url: rootPath + "/projectUser/add",
        data: dataFormJson,
        async: false,
        error: function (request) {
            $.modal.alertError(request.msg);
        },
        success: function (data) {
            $.operate.saveSuccess(data);
        }
    });
}

