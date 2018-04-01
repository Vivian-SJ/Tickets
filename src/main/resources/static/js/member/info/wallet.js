$(document).ready(function () {
    var loginAndRegisterArea = $('nav .navbar-right');
    var login = loginAndRegisterArea.children().children()[0];
    var register = loginAndRegisterArea.children().children()[1];
    if (localStorage.getItem('memberId')!==null) {
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
            localStorage.removeItem('memberId');
        }
        window.location.href='../login.html';
    });

    var url = 'http://localhost:8080/tickets/member/info/' + localStorage.getItem('memberId');
    $.ajax({
        url: url,
        method: 'get',
        dataType: 'json',
        success: function (data) {
            setPage(data);
        },
        error: function () {
            console.log('error')
        }
    })
});

function setPage(memberBean) {
    $('#sum_consumption').text('￥' + memberBean['sum_consumption']);
    $('#money_available').text('￥' + memberBean['money_available']);
}