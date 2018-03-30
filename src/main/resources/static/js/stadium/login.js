$(document).ready(function () {
    $('#login').click(function (event) {
        event.preventDefault();
        login();
    });
    $('#register').click(function (event) {
        event.preventDefault();
        window.location.href = "register.html";
    });
});

function login() {
    var id = $('[name=id]');
    var password = $('[name=password]');
    $.ajax({
        url: 'http://localhost:8080/tickets/stadium/login',
        method: 'post',
        data: {
            id: id.val(),
            password: password.val()
        },
        dataType: 'json',
        success: function (result) {
            if (result['resultBean']['result']===true) {
                localStorage.setItem('stadiumId', result['id']);
                window.location.href = 'homepage.html';
            } else {
                alert(result['resultBean']['message']);
            }
        },
        error: function () {
            alert('error');
            window.location.reload();
        }
    });
}