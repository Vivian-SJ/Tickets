$(document).ready(function () {
    var memberId = localStorage.getItem('memberId');
    var commonUrl = 'http://localhost:8080/tickets/member';
    var getCouponsUrl = commonUrl + '/coupons/' + memberId;
    var getCreditUrl = commonUrl + '/credit' + '/' + memberId;
    $.ajax({
        url: getCouponsUrl,
        method: 'get',
        dataType: 'json',
        success: function (data) {
            addCard(data);
        },
        error: function () {
            console.log("error");
        }
    });
    getCredit();
    $('[name=exchange]').click(function (event) {
        console.log("clicked");
        event.preventDefault();
        var btn = event.target;
        var value = $(this).parent().prev().prev().children('p').children('span').html();
        var exchangeUrl = commonUrl + '/coupon/exchange';
        $.ajax({
            url: exchangeUrl,
            method: 'post',
            data: {
                memberId: memberId,
                couponValue: value
            },
            dataType:'json',
            success: function (data) {
                exchange(data);
            },
            error: function () {
                console.log('error');
            }
        });
    });
});

function addCard(data) {
    var div = $('#have');
    for (var i = 0; i < data.length; i++) {
        var card = document.createElement('div');
        card.className = 'card';

        var price = document.createElement('div');
        price.className = 'price';
        var money = data[i]['value'];
        price.innerHTML = '<span>￥</span>\n' +
            '                                    <span id="value">' + money + '</span>';
        card.append(price);

        var description = document.createElement('div');
        description.className = 'description';
        description.innerHTML = '<span>优惠券</span>';
        card.append(description);

        div.append(card);
    }
}

function getCredit() {
    var getCreditUrl = "http://localhost:8080/tickets/member"+ '/credit' + '/' + localStorage.getItem('memberId');
    console.log(getCreditUrl);
    $.ajax({
        url: getCreditUrl,
        method: 'get',
        dataType: 'json',
        success: function (data) {
            console.log("get credit:"+ data);
            $('#credit').text(data);
        },
        error: function () {
            console.log("error");
        }
    });
}
function exchange(data) {
    if (data['result'] === true) {
        alert("兑换成功！");
        getCredit();
    } else {
        alert(data['message']);
    }
}