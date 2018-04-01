var showBean = {};
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
        $.ajax({
            url: 'http://localhost:8080/tickets/show/' + $('#search').val(),
            method: 'get',
            dataType: 'json',
            success: function (show) {
                showBean = show;
                setPage(show);
            },
            error: function () {
                console.log('error');
            }
        })
    })
});

function setPage(data) {
    $('#showBlock').css('display', 'block');
    $('#name').text(data['name']);
    $('.time').text(getTime(data['time']));
    var ul = $('#price');
    var seatNameAndPrice = data['showSeatBean']['seatNameAndPrice'];
    var seatNameAndId = data['showSeatBean']['seatNameAndId'];
    var keys = Object.keys(seatNameAndPrice);
    var len = keys.length;
    for (var i = len - 1; i >= 0; i--) {
        var li = document.createElement('li');
        $(li).addClass('price');
        var button = document.createElement('button');
        $(button).addClass('btn-info');
        $(button).attr('style', 'margin-top: 0;');
        $(button).attr('id', seatNameAndId[keys[i]]);
        $(button).attr('value', seatNameAndPrice[keys[i]]);
        button.innerHTML = keys[i] + seatNameAndPrice[keys[i]] + '元';
        li.append(button);
        // console.log(li);
        ul.append(li);
    }

    var seatId = -1;
    var seatPrice = 0;
    $('.btn-info').click(function (event) {
        event.preventDefault();
        $(event.target).css('background-color', '#4aaf15');
        seatId = event.target.getAttribute('id');
        showBean['seatId'] = seatId;
        seatPrice = event.target.getAttribute('value');
        showBean['seatPrice'] = seatPrice;
        console.log(seatId);
    });
    var isMember = false;
    $('#yesMember').click(function () {
        isMember = true;
        $('#memberIdBlock').css('display', 'block');
    });

    $('#select').click(function (event) {
        event.preventDefault();
        if (isMember) {
            $.ajax({
                url: 'http://localhost:8080/tickets/member/info/' + $('#memberId').val(),
                method: 'get',
                dataType: 'json',
                success: function (data) {
                    setBuyBlock(data)
                },
                error: function () {
                    console.log('error');
                }
            });
        } else {
            $('#selectedBlock').css('display', 'block');
            $('#discountBlock').css('display', 'none');
            $('#couponBlock').css('display', 'none');
            calculatePriceForNotMember();
        }
    });
    $('#unSelect').click(function (event) {
        event.preventDefault();
        var amountSpan = $('#amount').next();
        amountSpan.text('最多可选20张');
        if (isMember) {
            $.ajax({
                url: 'http://localhost:8080/tickets/member/info/' + $('#memberId').val(),
                method: 'get',
                dataType: 'json',
                success: function (data) {
                    setBuyBlock(data)
                },
                error: function () {
                    console.log('error');
                }
            });
        } else {
            $('#selectedBlock').css('display', 'block');
            $('#discountBlock').css('display', 'none');
            $('#couponBlock').css('display', 'none');
            calculatePriceForNotMember();
        }
    })

}

function setBuyBlock(data) {
    var rank = data['rank'];
    var discount = getDiscount(rank);
    console.log('discount' + discount);
    $('#discount').text(discount);
    if (discount === 1) {
        $('#discount').parent().css('display', 'none');
    }

    $.ajax({
        url: 'http://localhost:8080/tickets/member/coupons/' + data['id'],
        method: 'get',
        dataType: 'json',
        success: function (data) {
            getCoupons(data);
        }
    });

    $('#selectedBlock').css('display', 'block');
    calculatePriceForMember(data);
    // data['seatId'] = seatId;
    // data['seatPrice'] = seatPrice;
}

function calculatePriceForMember(data) {
    var orderBean = {
        memberId: data['id'],
        stadiumId: localStorage.getItem('stadiumId'),
        showId: $('#search').val(),
        type: "",
        seatId: -1,
        ticketAmount: 0,
        couponIds: [],
        discount: $('#discount').text(),
        expectedPrice: 0,
        actualPrice: 0,
        orderStatus: "",
        time: null,
        ps: ""
    };
    calculateCommon(orderBean, true);
}

function calculatePriceForNotMember() {
    var orderBean = {
        memberId: -1,
        stadiumId: localStorage.getItem('stadiumId'),
        showId: $('#search').val(),
        type: "",
        seatId: -1,
        ticketAmount: 0,
        couponIds: [],
        discount: $('#discount').text(),
        expectedPrice: 0,
        actualPrice: 0,
        orderStatus: "",
        time: null,
        ps: ""
    };
    calculateCommon(orderBean, false);
}

function calculateCommon(orderBean, type) {
    var seatIdAndAmount = showBean['showSeatBean']['seatIdAndAmount'];
    var seatId = showBean['seatId'];
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
                var expectedPrice = showBean['seatPrice'] * $('#amount').val();
                orderBean['expectedPrice'] = expectedPrice;
                ok = true;
            }
        } else {
            if ($('#amount').val() > 20) {
                alert("立即购票单次购买票数不能超过20张，请重新选择购买数量");
            } else {
                orderBean['type'] = '立即购买';
                //立即购票按照最低票价计算
                var seatNameAndPrice = showBean['showSeatBean']['seatNameAndPrice'];
                var names = Object.keys(seatNameAndPrice);
                var seatName = '';
                var minPrice = seatNameAndPrice[names[0]];
                for (var i = 1; i < names.length; i++) {
                    if (seatNameAndPrice[names[i]] < minPrice) {
                        minPrice = seatNameAndPrice[names[i]];
                        seatName = names[i];
                    }
                }
                console.log('seatName '+seatName);
                // var seatNameAndId = showBean['showSeatBean']['seatNameAndId'];
                // var unSelectSeatId = seatNameAndId[seatName];
                // console.log('unSelectSeatId ' + unSelectSeatId);
                // orderBean['seatId'] = unSelectSeatId;
                orderBean['expectedPrice'] = minPrice * $('#amount').val();
                ok = true;
            }
        }
        orderBean['ticketAmount'] = $('#amount').val();
        //coupons
        var couponIds = [];
        var couponValues = [];
        if (type===true) {
            var coupons = $('[name=coupon]');
            for (var i = 0; i < coupons.length; i++) {
                if (coupons[i].checked) {
                    couponIds.push($(coupons[i]).attr('id'));
                    couponValues.push($(coupons[i]).attr('value'));
                }
            }
            orderBean['couponIds'] = couponIds;
        }

        //actualPrice
        var actualPrice = orderBean['expectedPrice'] * orderBean['discount'];
        console.log(actualPrice);
        if (type===true) {
            for (var i = 0; i < couponValues.length; i++) {
                console.log("couponValue" + i+ couponValues[i]);
                actualPrice = actualPrice - couponValues[i];
            }
            console.log(actualPrice);
        }
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
                    window.location.reload();
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

function getCoupons(coupons) {
    var couponsList = $('#coupons');
    for (var i = 0; i < coupons.length; i++) {
        var li = document.createElement('li');
        var value = coupons[i]['value'];
        li.innerHTML = '<h4><input type="checkbox" name="coupon">' + value + '元优惠券</h4>';
        var input = $(li).children().children('input');
        $(input).attr('id', coupons[i]['id']);
        $(input).attr('value', value);
        couponsList.append(li);
    }
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