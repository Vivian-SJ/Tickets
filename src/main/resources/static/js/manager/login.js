$(document).ready(function () {
    $('#login').click(function (event) {
        event.preventDefault();
        login();
    });
});

function login() {
    var id = $('#id');
    var password = $('#password');
    var message = $('#message');
    if (id.val().length === 0) {
        message.text("ID不能为空");
        message.show();
        return;
    } else if (password.val().length === 0) {
        message.text("密码不能为空");
        message.show();
        return;
    }
    $.ajax("http://localhost:8080/tickets/manager/login",
        {
            method: 'post',
            data: {
                id: id.val(),
                password: password.val()
            },
            dataType: 'json',
            success: function (result) {
                handleResult(result);
            },
            error: function () {
                console.log('error')
            }
        }
    );
}

function handleResult(result) {
    var resultState = result['result'];
    var message = $('#message');
    if (resultState === true) {
        localStorage.setItem('managerId','1');
        window.location.href = "homepage.html";
    } else {
        message.text(result.message);
        message.show();
    }
}