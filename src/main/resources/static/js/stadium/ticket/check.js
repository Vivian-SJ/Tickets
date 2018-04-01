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

    $('#searchBtn').click(function (event) {
        event.preventDefault();
        var id = $('#search').val();
        $.ajax({
            url: 'http://localhost:8080/tickets/stadium/search/'+id,
            method: 'get',
            dataType:'json',
            success:function (order) {
                if (order===null) {
                    alert('无效的订单');
                }
                setPage(order);
            },
            error: function () {
                alert('无效的订单');
                console.log('error');
            }
        })
    })
});

function setPage(order) {
    $('.order-box').css('display','block');
    $('.order-date').text(getDate(order['time']));
    $('.order-id').text(order['id']);
    $('.show-name').text(order['showName']);
    $('.stadium-name').text(order['stadiumName']);
    $('.seat-info span').text(order['seatName']);
    $('.show-time').text(getTime(order['showTime']));
    $('.order-price').text('￥'+order['actualPrice']);
    $('#check').click(function (event) {
        event.preventDefault();
        $.ajax({
            url: 'http://localhost:8080/tickets/stadium/check',
            method:'post',
            dataType:'json',
            data:{
                orderId: order['id']
            },
            success: function (resultBean) {
                if (resultBean['result']===true) {
                    alert('检票成功!');
                    window.location.reload();
                } else {
                    alert(resultBean['message']);
                }
            },
            error: function () {
                console.log('error');
            }
        })
    })
}

function getTime(timestamp) {
    var date = new Date(timestamp);
    Y = date.getFullYear() + '-';
    M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    D = date.getDate() + ' ';
    h = date.getHours() + ':';
    m = date.getMinutes() + ':';
    s = date.getSeconds();
    return Y + M + D + h + m + "00";
}

function getDate(timestamp) {
    var date = new Date(timestamp);
    Y = date.getFullYear() + '-';
    M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    D = date.getDate() + ' ';
    return Y + M + D;
}