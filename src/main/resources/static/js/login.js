$('#submit').click(function (event) {
    event.preventDefault();
    login();
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
    // $.post("/tickets/login",
    //     {
    //         email: email.val(),
    //         password: password.val()
    //     },
    //
    //     function handleResult(result) {
    //         var resultState = result.result;
    //         if (resultState) {
    //             message.text("ok");
    //             message.show();
    //         } else {
    //             message.text(result.message);
    //             message.show();
    //         }
    //     });
}

function handleResult(result) {
    var resultState = result.result;
    var message = $('#message');
    if (resultState === true) {
        message.text("ok");
        message.show();
    } else {
        message.text(result.message);
        message.show();
    }
}