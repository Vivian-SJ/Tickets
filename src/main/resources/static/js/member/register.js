$(document).ready(function () {
    $('#submit').click(function () {
        var name = $('#name');
        var password = $('#password');
        var passwordAgain = $('#passwordAgain');
        var gender = $('#gender');
        var email = $('#email');
        var message = $('#message');
        var content = $('#content');
        var submit = $('#submit');
        if (name.val().length === 0 || email.val().length === 0 || password.val().length === 0 || passwordAgain.val().length === 0) {
            message.text("请完整填写注册信息");
            message.show();
            return;
        }
        if (password.val() !== passwordAgain.val()) {
            message.text("两次密码不一致,请重新输入");
            message.show();
            passwordAgain.val("");
            return;
        }
        // data-toggle="modal" data-target="#myModal"
        submit.attr("data-toggle", "modal");
        submit.attr("data-target", "#myModal");
        var memberBean = {
            name: name.val(),
            password: password.val(),
            gender: gender.val(),
            email: email.val()
        };
        $.ajax(
            {
                url: 'http://localhost:8080/tickets/member/register',
                method: 'post',
                contentType: 'application/json',
                data: JSON.stringify(memberBean),
                dataType: 'json',
                success:function (result) {
                    if (result.result === true) {
                        var sure = $('#sure');
                        sure.removeAttr("disabled");
                        console.log("finish");
                        content.text("邮件已发送成功");
                        $('#close').click(function () {
                            window.location.href="http://localhost:8080/views/hello.html";
                        });
                        sure.click(function () {
                            window.location.href="http://localhost:8080/views/hello.html";
                        });
                    } else {
                        content.text(result.message);
                        $('#sure').attr("disabled");
                    }
                },
                error:function (result) {
                    console.log("error");
                    content.val(result.message);
                }
            });

        function success(result) {


        }
    });
});