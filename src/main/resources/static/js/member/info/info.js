$(document).ready(function () {
    var memberId = localStorage.getItem('memberId');
    var commonUrl = 'http://localhost:8080/tickets/member';
    console.log(memberId);
    var getInfoUrl = commonUrl + '/info' + '/' + memberId;
    //自动填充内容
    $.ajax(
        {
            url: getInfoUrl,
            method: 'get',
            dataType: 'json',
            success: function (data) {
                $('#email').text(data['email']);
                $('#name').val(data['name']);
                $('#password').val(data['password']);
                $('#gender').val(data['gender']);
                $('#rank').val(data['rank']);
                $('#credit').val(data['credit']);
            },
            error: function () {
                console.log("error");
            }
        }
    );

    //点击密码框要显示再次输入密码
    $('#password').click(function (event) {
        event.preventDefault();
        $('#passwordAgainArea').removeAttr('hidden');
    });

    //取消会员
    var cancelBtn = $('#cancel-member');
    cancelBtn.click(function (event) {
        event.preventDefault();
        var url = commonUrl + '/cancel/' + memberId;
        console.log(url);
        $.ajax(
            {
                url: url,
                method: 'get',
                // contentType: 'application/json',
                // data: JSON.stringify(memberBean),
                dataType: 'json',
                success: function (data) {
                    // var data = JSON.parse(result);
                    cancel(data);
                },
                error: function (result) {
                    console.log("error");
                }
            });
    });

    //更新信息并保存
    $('#submit').click(function (event) {
        event.preventDefault();
        var name = $('#name');
        var password = $('#password');
        var passwordAgain = $('#passwordAgain');
        var gender = $('#gender');
        var message = $('#message');
        if (name.val().length === 0 || password.val().length === 0 || passwordAgain.val().length === 0) {
            message.text("请完整填写信息");
            message.show();
            return;
        }
        if (password.val() !== passwordAgain.val()) {
            message.text("两次密码不一致,请重新输入");
            message.show();
            passwordAgain.val("");
            return;
        }
        var memberBean = {
            name: name.val(),
            password: password.val(),
            gender: gender.val()
        };
        var modifyUrl = commonUrl + '/info' + '/modify/' + memberId;
        $.ajax(
            {
                url: modifyUrl,
                method: 'post',
                contentType: 'application/json',
                data: JSON.stringify(memberBean),
                dataType: 'json',
                success:function (result) {
                    if (result.result === true) {
                        window.location.href="info.html";
                    } else {
                        message.text(result.message);
                    }
                },
                error:function (result) {
                    console.log("error");
                    message.text(result.message);
                }
            });
    })
});

function cancel(data) {
    if (data['result'] === true) {
        console.log("success");
        $('#valid').text('失效');
        $('#cancel-member').css('display', 'none')
    }
}