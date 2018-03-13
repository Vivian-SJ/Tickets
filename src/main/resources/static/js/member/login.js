$(document).ready(function () {
    $('#login').click(function (event) {
        event.preventDefault();
        login();
    });
    $('#register').click(function (event) {
        event.preventDefault();
        register();
    });
});

function login() {
    var email = $('#email');
    var password = $('#password');
    var message = $('#message');
    if (email.val().length === 0) {
        message.text("邮箱不能为空");
        message.show();
        return;
    } else if (password.val().length === 0) {
        message.text("密码不能为空");
        message.show();
        return;
    }
    $.ajax("/tickets/login",
        {
            method: 'post',
            data: {
                email: email.val(),
                password: password.val()
            },
            dataType: 'json',
            success: function (result) {
                handleResult(result);
            },
            error: function (result) {
                message.text(result.message);
                message.show();
            }
        }
    );
}

function register() {
    console.log("register");
    window.location.href = "register.html";
}

function handleResult(result) {
    // var data = JSON.parse(result);
    var resultState = result['resultBean']['result'];
    var message = $('#message');
    if (resultState === true) {
        // message.text("ok");
        // message.show();
        localStorage.setItem('memberId',result['id']);
        window.location.href = "homepage.html";
    } else {
        message.text(result['resultBean'].message);
        message.show();
    }
}