var orderBean = {};
var showName = "";
var stadiumName = "";
var seatName = "";
var orderId = -1;
$(document).ready(function () {
    var request = getRequest();
    orderId = request['orderId'];
    $.ajax({
        url: 'http://localhost:8080/tickets/member/order/' + orderId,
        method: 'get',
        dataType: 'json',
        success: function (data) {
            console.log('order finish');
            orderBean = data;
            getShowName();
        },
        error: function () {
            console.log('error');
        }
    });
});

function getShowName() {
    console.log('here');
    $.ajax({
        url: 'http://localhost:8080/tickets/show/' + orderBean['showId'],
        method: 'get',
        dataType:'json',
        success: function (data) {
            showName = data['name'];
            // console.log('showName ' + showName);
            getStadiumName();
        },
        error: function () {
            console.log('error');
        }
    });
}

function getStadiumName() {
    $.ajax({
        url: 'http://localhost:8080/tickets/stadium/info/' + orderBean['stadiumId'],
        method: 'get',
        dataType: 'json',
        success: function (data) {
            stadiumName = data['name'];
            // console.log('stadiumName '+stadiumName);
            getSeatName();
        },
        error: function () {
            console.log('error');
        }
    });
}

function getSeatName() {
    $.ajax({
        url: 'http://localhost:8080/tickets/seat/' + orderBean['seatId'],
        method: 'get',
        dataType: 'json',
        success: function (data) {
            seatName = data['name'];
            // console.log('seatName finish');
            // console.log('seatName '+seatName);
            setPage();
        },
        error: function () {
            console.log('error');
        }
    });
}

function setPage() {
    $('#name').text(showName);
    $('#info-form .show-name').text(showName);
    $('#info-form .stadium-name').text(stadiumName);
    if (orderBean['type']==='立即购买') {
        $('#info-form .show-type').text('未选座');
    } else {
        $('#info-form .show-type').text(seatName+orderBean['ticketAmount']+'张');
    }
    $('#info-form .show-time').text(getDate(orderBean['time']));
    $('#expectedPrice').text(orderBean['expectedPrice']);
    var discount = orderBean['discount'];
    var couponAmount = orderBean['couponIds'].length;
    if (discount!==1 && couponAmount!==0) {
        $('#discount').text(orderBean['discount']+'折'+' '+couponAmount+'张优惠券');
    }else if (discount===1 && couponAmount===0) {
        $('#discount').text('无');
    } else if (discount===1 && couponAmount!==0) {
        $('#discount').text(couponAmount+'张优惠券');
    } else if (discount!==1 && couponAmount===0) {
        $('#discount').text(orderBean['discount']+'折')
    }
    $('#actualPrice').text(orderBean['actualPrice']);
    pay();
}

function pay() {
    $('#pay').click(function () {
        $.ajax({
            url: 'http://localhost:8080/tickets/member/ticket/pay',
            method: 'post',
            dataType: 'json',
            data: {
                orderId : orderId
            },
            success: function (data) {
                if (data['result']===true) {
                    $('#pay').css('display', 'none');
                    $('#pay-label').css('display', 'block');
                } else {
                    alert(data['message']);
                }
            },
            error: function () {
                console.log('error');
            }
        })
    })
}

function getRequest() {
    var url = location.search; //获取url中"?"符及以后的字串
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