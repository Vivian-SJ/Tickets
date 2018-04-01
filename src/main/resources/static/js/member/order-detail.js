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
        window.location.href='login.html';
    });

    var request = getRequest();
    var id = request['id'];
    var url = 'http://localhost:8080/tickets/member/order/' + id;
    $.ajax({
        url: url,
        method:'get',
        dataType:'json',
        success:function (data) {
            setPage(data);
        },
        error:function () {
            console.log('error');
        }
    })
});

function setPage(orderBean) {
    $('#id').text(orderBean['showId']);
    $('#bigName').text(orderBean['showName']);
    $('.time').text(getTime(orderBean['showTime']));
    $('.place').text(orderBean['stadiumName']);
    $('.status').text(orderBean['orderStatus']);
    $('.ps').text(orderBean['ps']);
    if (orderBean['orderStatus']==='待支付') {
        var li = document.createElement('li');
        li.innerHTML = '<button name="payButton" class="btn-sm detail-pay-btn">去支付</button>';
        $('.status').parent().append(li);
    }
    $('[name=payButton]').click(function (event) {
        event.preventDefault();
        window.location.href = 'pay.html?orderId='+orderBean['id'];
    })
}

function getRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = {};
    if (url.indexOf("?") !== -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = decodeURIComponent(strs[i].split("=")[1]);
        }
    }
    return theRequest;
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