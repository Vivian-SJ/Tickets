$(document).ready(function () {
    var loginAndRegisterArea = $('nav .navbar-right');
    var login = loginAndRegisterArea.children().children()[0];
    var register = loginAndRegisterArea.children().children()[1];
    if (localStorage.getItem('stadiumId')!==null) {
        login.innerHTML = '退出登录';
        $(register).css('display', 'none');
        $(register).parent().css('display', 'none');
    } else {
        alert("请先登录!");
        return;
    }
    $(login).click(function (event) {
        event.preventDefault();
        if ($(login).text()==='退出登录') {
            localStorage.removeItem('stadiumId');
        }
        window.location.href='../login.html';
    });

    var url = 'http://localhost:8080/tickets/stadium/statistics/' + localStorage.getItem('stadiumId');
    $.ajax({
        url: url,
        method: 'get',
        dataType: 'json',
        success: function (data) {
            setPage(data);
        },
        error: function () {
            console.log('error');
        }
    })
});

function setPage(statisticsBean) {
    $('#sum').text('￥' + statisticsBean['totalPrice']);
    var map = statisticsBean['statusAndOrder'];
    var used = map['已使用'];
    var canceled = map['已取消'];
    $('#sum_order').text(statisticsBean['orderSum']);
    $('#used_order').text(used.length);
    $('#canceled_order').text(canceled.length);
}