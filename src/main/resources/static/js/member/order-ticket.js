var memberBean;
var memberId = localStorage.getItem('memberId');
console.log(memberId);
var getInfoUrl = 'http://localhost:8080/tickets/member/info' + '/' + memberId;
$.ajax(
    {
        url: getInfoUrl,
        method: 'get',
        dataType: 'json',
        success: function (data) {
            memberBean = data;
        },
        error: function () {
            console.log("error");
        }
    });
var request = getRequest();
var showId = request['id'];
var stadiumId = request['stadiumId'];
var coupons = [];
$.ajax({
    url: 'http://localhost:8080/tickets/member/coupons/' + memberId,
    method: 'get',
    dataType: 'json',
    success: function (data) {
        coupons = data;
        // console.log(coupons);
    }
});
$(document).ready(function () {
    while (memberBean===undefined){}
    if (showId !== undefined) {
        $('#login').innerHTML = '退出登录';
        $('#register').css('display', 'none');
    }
    var commonUrl = 'http://localhost:8080/tickets/show';
    var getShowInfoUrl = commonUrl + '/' + showId;
    $.ajax({
        url: getShowInfoUrl,
        method: 'get',
        dataType: 'json',
        success: function (data) {
            setPage(data);
        },
        error: function () {
        }
    })
});

function setPage(data) {
    $('#name').text(data['name']);
    $('#bigName').text(data['name']);
    $('.time').text(getDate(data['time']));
    var ul = $('#price');
    var seatNameAndPrice = data['showSeatBean']['seatNameAndPrice'];
    var seatNameAndId = data['showSeatBean']['seatNameAndId'];
    // console.log(seatNameAndPrice);
    var keys = Object.keys(seatNameAndPrice);
    var len = keys.length;
    var values = Object.values(seatNameAndPrice);
    // console.log(len);
    for (var i = len - 1; i >= 0; i--) {
        var li = document.createElement('li');
        $(li).addClass('price');
        var button = document.createElement('button');
        $(button).addClass('btn-info');
        $(button).attr('style', 'margin-top: 0;');
        $(button).attr('id', seatNameAndId[keys[i]]);
        $(button).attr('value', values[i]);
        button.innerHTML = keys[i] + values[i] + '元';
        li.append(button);
        // console.log(li);
        ul.append(li);
    }

    //设置购票信息界面的内容
    var rank = memberBean['rank'];
    var discount = getDiscount(rank);
    console.log('discount' + discount);
    $('#discount').text(discount);
    if (discount === 1) {
        $('#discount').parent().css('display', 'none');
    }
    var couponsList = $('#coupons');
    for (var i = 0; i < coupons.length; i++) {
        var li = document.createElement('li');
        var value = coupons[i]['value'];
        if (i === 0) {
            li.innerHTML = '<label style="margin-left: -82px;"><input name="coupon" type="checkbox">' + value + '元优惠券</label>';
        } else {
            li.innerHTML = '<label><input name="coupon" type="checkbox">' + value + '元优惠券</label>';
        }
        var input = $(li).children().children('input');
        $(input).attr('id', coupons[i]['id']);
        $(input).attr('value', value);
        couponsList.append(li);
    }
    buyTicket(data);
}

function buyTicket(data) {
    var seatId;
    var seatPrice = 0;
    $('.btn-info').click(function (event) {
        event.preventDefault();
        $(event.target).css('background-color', '#4aaf15');
        seatId = event.target.getAttribute('id');
        seatPrice = event.target.getAttribute('value');
        console.log(seatId);
    });
    $('#select').click(function (event) {
        event.preventDefault();
        $('#selectedBlock').css('display', 'block');
        data['seatId'] = seatId;
        data['seatPrice'] = seatPrice;
        calculatePrice(data);
    });
    $('#unSelect').click(function (event) {
        event.preventDefault();
        $('#selectedBlock').css('display', 'block');
        var amountSpan = $('#amount').next();
        amountSpan.text('最多可选20张');
        calculatePrice(data);
    })
}

function calculatePrice(data) {
    var orderBean = {
        memberId: memberId,
        stadiumId: stadiumId,
        showId: showId,
        type: "",
        seatId: 0,
        ticketAmount: 0,
        couponIds: null,
        discount: $('#discount').text(),
        expectedPrice: 0,
        actualPrice: 0,
        orderStatus: "",
        time: null,
        ps: ""
    };
    var seatIdAndAmount = data['showSeatBean']['seatIdAndAmount'];
    var seatId = data['seatId'];
    console.log('seatId: ' + seatId);
    var amount = seatIdAndAmount[seatId];
    var ok = false;
    $('#buy').click(function (event) {
        event.preventDefault();
        //选座购买
        if (seatId !== undefined) {
            if ($('#amount').val() > amount) {
                alert("抱歉，当前只有" + amount + "张票了，请重新选择购买数量");
            } else if ($('#amount').val() > 6) {
                alert("选座购票单次购买票数不能超过6张，请重新选择购买数量");
            }
            else {
                orderBean['type'] = '选座购买';
                orderBean['seatId'] = seatId;
                var expectedPrice = data['seatPrice'] * $('#amount').val();
                orderBean['expectedPrice'] = expectedPrice;
                ok = true;
            }
        } else {
            if ($('#amount').val() > 20) {
                alert("立即购票单次购买票数不能超过20张，请重新选择购买数量");
            } else {
                orderBean['type'] = '立即购买';
                //立即购票按照最低票价计算
                var seatNameAndPrice = data['showSeatBean']['seatNameAndPrice'];
                var prices = Object.values(seatNameAndPrice);
                var names = Object.keys(seatNameAndPrice);
                var seat = 0;
                var minPrice = prices[0];
                for (var i = 1; i < prices.length; i++) {
                    if (prices[i] < minPrice) {
                        minPrice = prices[i];
                        seat = i;
                    }
                }
                var seatName = names[seat];
                console.log('seatName '+seatName);
                var seatNameAndId = data['showSeatBean']['seatNameAndId'];
                var unSelectSeatId = seatNameAndId[seatName];
                console.log('unSelectSeatId ' + unSelectSeatId);
                orderBean['seatId'] = unSelectSeatId;
                orderBean['expectedPrice'] = minPrice * $('#amount').val();
                ok = true;
            }
        }
        orderBean['ticketAmount'] = $('#amount').val();
        //coupons
        var couponIds = [];
        var couponValues = [];
        var coupons = $('[name=coupon]');
        for (var i = 0; i < coupons.length; i++) {
            if (coupons[i].checked) {
                couponIds.push($(coupons[i]).attr('id'));
                couponValues.push($(coupons[i]).attr('value'));
            }
        }
        orderBean['couponIds'] = couponIds;

        //actualPrice
        var actualPrice = orderBean['expectedPrice'] * orderBean['discount'];
        console.log(actualPrice);
        for (var i = 0; i < couponValues.length; i++) {
            console.log("couponValue" + i+ couponValues[i]);
            actualPrice = actualPrice - couponValues[i];
        }
        console.log(actualPrice);
        orderBean['actualPrice'] = actualPrice;

        //订单状态
        // orderBean['orderStatus'] = '待支付';
        //下单时间
        orderBean['time'] = new Date().getTime();
        if (ok) {
            $('#buy').attr("data-toggle", "modal");
            $('#buy').attr("data-target", "#sureModal");
            $('#content').text('订单原价为' + orderBean['expectedPrice'] + '元，折扣后为' + actualPrice + '元，确认下单吗？');
            // $('#sureModal').modal();
            $('#sureOrder').click(function () {
                orderTicket(orderBean);
            });
        }
    })
}

function orderTicket(orderBean) {
    var url = 'http://localhost:8080/tickets/member/ticket/buy';
    $.ajax({
        url: url,
        method: 'post',
        contentType: 'application/json',
        data: JSON.stringify(orderBean),
        dataType: 'json',
        success: function (data) {
            if (data['resultBean']['result'] === true) {
                // alert("成功");
                $('#payModal').modal();
                $('#surePay').click(function () {
                   window.location.href = 'pay.html?orderId='+data['id'];
                });
                $('#cancelPay').click(function () {
                    window.location.href = 'info/order.html';
                })
            } else {
                alert(data['resultBean']['message']);
            }
        },
        error: function () {
            console.log('error');
        }
    })
}

function getRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") !== -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = decodeURIComponent(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}

function getDate(timestamp) {
    var date = new Date(timestamp);
    Y = date.getFullYear() + '-';
    M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    D = date.getDate() + ' ';
    h = date.getHours() + ':';
    m = date.getMinutes() + ':';
    s = date.getSeconds();
    return Y + M + D + h + m + s;
}

function getDiscount(rank) {
    if (rank < 3) {
        return 1;
    }
    switch (rank) {
        case 3:
            return 0.9;
        case 4:
            return 0.85;
        case 5:
            return 0.8;
        case 6:
            return 0.75;
        case 7:
            return 0.7;
        default:
            return 0.7;
    }
}