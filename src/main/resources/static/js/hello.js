$(document).ready(function () {
    var member = $('#member');
    var stadium = $('#stadium');
    var manager = $('#manager');
    member.bind('click', function () {
        memberLogin();
    });
    stadium.bind('click', function () {
        stadiumLogin();
    });
    manager.bind('click', function () {
        managerLogin();
    });
});

function memberLogin() {
    window.location.href = "/views/login.html";
}

function stadiumLogin() {
    
}

function managerLogin() {
    
}