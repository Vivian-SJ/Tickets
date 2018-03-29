var commonUrl = 'http://localhost:8080/tickets/member';
var memberId = localStorage.getItem('memberId');
var orders = [];
$(document).ready(function () {
    var getOrdersUrl = commonUrl + '/order/display/' + memberId;
    $.ajax({
        url: getOrdersUrl,
        method: 'get',
        dataType: 'json',
        success: function (data) {
            orders = data;
            setPage(orders);
        },
        error: function () {
            console.log('error');
        }
    })
});

function setPage(orders) {
    var all = orders;
    var to_be_paid = [];
    var wait_ticket = [];
    var ticket_out = [];
    var canceled = [];
    var used = [];
    for (var i = 0; i < orders.length; i++) {
        switch (orders[i]['orderStatus']) {
            case '待出票':
                wait_ticket.push(orders[i]);
                break;
            case '待支付':
                to_be_paid.push(orders[i]);
                break;
            case '已出票':
                ticket_out.push(orders[i]);
                break;
            case '已取消':
                canceled.push(orders[i]);
                break;
            case '已使用':
                used.push(orders[i]);
                break;
            default:
                break;
        }
    }
    var refund = wait_ticket.concat(ticket_out);
    console.log("refund" + refund.length);

    for (var i = 0; i < all.length; i++) {
        var div = document.createElement('div');
        $(div).addClass('order-box');
        var id = 'all' + all[i]['id'];
        $(div).attr('id', id);
        $(div).html('<div class="order-header">\n' +
            '                                    <span class="order-date">2018-01-01</span>\n' +
            '                                    <span class="order-id">订单号：1</span>\n' +
            '                                </div>\n' +
            '                                <div class="order-body">\n' +
            '                                    <div class="poster">\n' +
            '                                        <img src="../../../pictrues/show1.jpg">\n' +
            '                                    </div>\n' +
            '                                    <form style="float: left; margin-left: 130px;">\n' +
            '                                        <div class="show-name">《驴得水》</div>\n' +
            '                                        <div class="stadium-name">幸福蓝海国际影城</div>\n' +
            '                                        <div class="seat-info">\n' +
            '                                            <span>一等座</span>\n' +
            '                                        </div>\n' +
            '                                        <div class="show-time">2018-02-02 15:10:00</div>\n' +
            '                                    </form>\n' +
            '                                    <div class="order-price">￥100</div>\n' +
            '                                    <div class="order-status">已出票</div>\n' +
            '                                </div>');
        //要先加入，后面那些修改才起作用，我也不知道为什么orz
        $('#all').append(div);

        $('#' + id + ' .order-header .order-date').text(getDate(all[i]['time']));
        $('#' + id + ' .order-header .order-id').text('订单号' + all[i]['id']);
        $('#' + id + ' .order-body .show-name').text(all[i]['showName']);
        $('#' + id + ' .order-body .stadium-name').text(all[i]['stadiumName']);
        $('#' + id + ' .order-body .seat-info').children('span').text(all[i]['seatName']);
        $('#' + id + ' .order-body .show-time').text(getTime(all[i]['showTime']));
        $('#' + id + ' .order-body .order-price').text(all[i]['actualPrice']);
        $('#' + id + ' .order-body .order-status').text(all[i]['orderStatus']);

        if (all[i]['orderStatus'] === '待支付') {
            var payButton = document.createElement('button');
            $('#' + id + ' .order-body .order-status').append(payButton);
            $(payButton).text('去支付');
            $(payButton).addClass('btn-sm');
            $(payButton).css('color', '#4aaf15');
            $(payButton).css('margin', '0 10px 0 10px');
            $(payButton).attr('name', 'payButton');
            $('#' + id + ' .order-body .order-status').css('width', 'auto');
        }
    }
    for (var i = 0; i < wait_ticket.length; i++) {
        var div = document.createElement('div');
        $(div).addClass('order-box');
        var id = 'wait_ticket' + wait_ticket[i]['id'];
        $(div).attr('id', id);
        div.innerHTML = '<div class="order-header">\n' +
            '                                    <span class="order-date">2018-01-01</span>\n' +
            '                                    <span class="order-id">订单号：1</span>\n' +
            '                                </div>\n' +
            '                                <div class="order-body">\n' +
            '                                    <div class="poster">\n' +
            '                                        <img src="../../../pictrues/show1.jpg">\n' +
            '                                    </div>\n' +
            '                                    <form style="float: left; margin-left: 130px;">\n' +
            '                                        <div class="show-name">《驴得水》</div>\n' +
            '                                        <div class="stadium-name">幸福蓝海国际影城</div>\n' +
            '                                        <div class="seat-info">\n' +
            '                                            <span>一等座</span>\n' +
            '                                        </div>\n' +
            '                                        <div class="show-time">2018-02-02 15:10:00</div>\n' +
            '                                    </form>\n' +
            '                                    <div class="order-price">￥100</div>\n' +
            '                                    <div class="order-status">已出票</div>\n' +
            '                                </div>';
        $('#wait-ticket').append(div);

        $('#' + id + ' .order-header .order-date').text(getDate(wait_ticket[i]['time']));
        $('#' + id + ' .order-header .order-id').text('订单号' + wait_ticket[i]['id']);
        $('#' + id + ' .order-body .show-name').text(wait_ticket[i]['showName']);
        $('#' + id + ' .order-body .stadium-name').text(wait_ticket[i]['stadiumName']);
        $('#' + id + ' .order-body .seat-info').children('span').text(wait_ticket[i]['seatName']);
        $('#' + id + ' .order-body .show-time').text(getTime(wait_ticket[i]['showTime']));
        $('#' + id + ' .order-body .order-price').text(wait_ticket[i]['actualPrice']);
        $('#' + id + ' .order-body .order-status').text(wait_ticket[i]['orderStatus']);
    }
    for (var i = 0; i < to_be_paid.length; i++) {
        var div = document.createElement('div');
        $(div).addClass('order-box');
        var id = 'to_be_paid' + to_be_paid[i]['id'];
        $(div).attr('id', id);
        div.innerHTML = '                  <div class="order-header">\n' +
            '                                    <span class="order-date">2018-01-01</span>\n' +
            '                                    <span class="order-id">订单号：1</span>\n' +
            '                                </div>\n' +
            '                                <div class="order-body">\n' +
            '                                    <div class="poster">\n' +
            '                                        <img src="../../../pictrues/show1.jpg">\n' +
            '                                    </div>\n' +
            '                                    <form style="float: left; margin-left: 130px;">\n' +
            '                                        <div class="show-name">《驴得水》</div>\n' +
            '                                        <div class="stadium-name">幸福蓝海国际影城</div>\n' +
            '                                        <div class="seat-info">\n' +
            '                                            <span>一等座</span>\n' +
            '                                        </div>\n' +
            '                                        <div class="show-time">2018-02-02 15:10:00</div>\n' +
            '                                    </form>\n' +
            '                                    <div class="order-price">￥100</div>\n' +
            '                                    <div class="order-status" style="width: auto">待支付\n' +
            '</div>\n' +
            '                            </div>';
        $('#to-be-paid').append(div);

        $('#' + id + ' .order-header .order-date').text(getDate(to_be_paid[i]['time']));
        $('#' + id + ' .order-header .order-id').text('订单号' + to_be_paid[i]['id']);
        $('#' + id + ' .order-body .show-name').text(to_be_paid[i]['showName']);
        $('#' + id + ' .order-body .stadium-name').text(to_be_paid[i]['stadiumName']);
        $('#' + id + ' .order-body .seat-info').children('span').text(to_be_paid[i]['seatName']);
        $('#' + id + ' .order-body .show-time').text(getTime(to_be_paid[i]['showTime']));
        $('#' + id + ' .order-body .order-price').text(to_be_paid[i]['actualPrice']);
        $('#' + id + ' .order-body .order-status').text(to_be_paid[i]['orderStatus']);

        var payButton = document.createElement('button');
        $('#' + id + ' .order-body .order-status').append(payButton);
        $(payButton).text('去支付');
        $(payButton).addClass('btn-sm');
        $(payButton).css('color', '#4aaf15');
        $(payButton).css('margin', '0 10px 0 10px');
        $(payButton).attr('name', 'payButton');
        $('#' + id + ' .order-body .order-status').css('width', 'auto');
    }
    for (var i = 0; i < ticket_out.length; i++) {
        var div = document.createElement('div');
        $(div).addClass('order-box');
        var id = 'ticket_out' + ticket_out[i]['id'];
        $(div).attr('id', id);
        div.innerHTML = '<div class="order-header">\n' +
            '                                    <span class="order-date">2018-01-01</span>\n' +
            '                                    <span class="order-id">订单号：1</span>\n' +
            '                                </div>\n' +
            '                                <div class="order-body">\n' +
            '                                    <div class="poster">\n' +
            '                                        <img src="../../../pictrues/show1.jpg">\n' +
            '                                    </div>\n' +
            '                                    <form style="float: left; margin-left: 130px;">\n' +
            '                                        <div class="show-name">《驴得水》</div>\n' +
            '                                        <div class="stadium-name">幸福蓝海国际影城</div>\n' +
            '                                        <div class="seat-info">\n' +
            '                                            <span>一等座</span>\n' +
            '                                        </div>\n' +
            '                                        <div class="show-time">2018-02-02 15:10:00</div>\n' +
            '                                    </form>\n' +
            '                                    <div class="order-price">￥100</div>\n' +
            '                                    <div class="order-status">已出票</div>\n' +
            '                                </div>';
        $('#ticket-out').append(div);

        $('#' + id + ' .order-header .order-date').text(getDate(ticket_out[i]['time']));
        $('#' + id + ' .order-header .order-id').text('订单号' + ticket_out[i]['id']);
        $('#' + id + ' .order-body .show-name').text(ticket_out[i]['showName']);
        $('#' + id + ' .order-body .stadium-name').text(ticket_out[i]['stadiumName']);
        $('#' + id + ' .order-body .seat-info').children('span').text(ticket_out[i]['seatName']);
        $('#' + id + ' .order-body .show-time').text(getTime(ticket_out[i]['showTime']));
        $('#' + id + ' .order-body .order-price').text(ticket_out[i]['actualPrice']);
        $('#' + id + ' .order-body .order-status').text(ticket_out[i]['orderStatus']);

    }
    for (var i = 0; i < canceled.length; i++) {
        var div = document.createElement('div');
        $(div).addClass('order-box');
        var id = 'canceled' + canceled[i]['id'];
        $(div).attr('id', id);
        div.innerHTML = '<div class="order-header">\n' +
            '                                    <span class="order-date">2018-01-01</span>\n' +
            '                                    <span class="order-id">订单号：1</span>\n' +
            '                                </div>\n' +
            '                                <div class="order-body">\n' +
            '                                    <div class="poster">\n' +
            '                                        <img src="../../../pictrues/show1.jpg">\n' +
            '                                    </div>\n' +
            '                                    <form style="float: left; margin-left: 130px;">\n' +
            '                                        <div class="show-name">《驴得水》</div>\n' +
            '                                        <div class="stadium-name">幸福蓝海国际影城</div>\n' +
            '                                        <div class="seat-info">\n' +
            '                                            <span>一等座</span>\n' +
            '                                        </div>\n' +
            '                                        <div class="show-time">2018-02-02 15:10:00</div>\n' +
            '                                    </form>\n' +
            '                                    <div class="order-price">￥100</div>\n' +
            '                                    <div class="order-status">已出票</div>\n' +
            '                                </div>';
        $('#canceled').append(div);

        $('#' + id + ' .order-header .order-date').text(getDate(canceled[i]['time']));
        $('#' + id + ' .order-header .order-id').text('订单号' + canceled[i]['id']);
        $('#' + id + ' .order-body .show-name').text(canceled[i]['showName']);
        $('#' + id + ' .order-body .stadium-name').text(canceled[i]['stadiumName']);
        $('#' + id + ' .order-body .seat-info').children('span').text(canceled[i]['seatName']);
        $('#' + id + ' .order-body .show-time').text(getTime(canceled[i]['showTime']));
        $('#' + id + ' .order-body .order-price').text(canceled[i]['actualPrice']);
        $('#' + id + ' .order-body .order-status').text(canceled[i]['orderStatus']);
    }
    for (var i = 0; i < used.length; i++) {
        var div = document.createElement('div');
        $(div).addClass('order-box');
        var id = 'used' + used[i]['id'];
        $(div).attr('id', id);
        div.innerHTML = '<div class="order-header">\n' +
            '                                    <span class="order-date">2018-01-01</span>\n' +
            '                                    <span class="order-id">订单号：1</span>\n' +
            '                                </div>\n' +
            '                                <div class="order-body">\n' +
            '                                    <div class="poster">\n' +
            '                                        <img src="../../../pictrues/show1.jpg">\n' +
            '                                    </div>\n' +
            '                                    <form style="float: left; margin-left: 130px;">\n' +
            '                                        <div class="show-name">《驴得水》</div>\n' +
            '                                        <div class="stadium-name">幸福蓝海国际影城</div>\n' +
            '                                        <div class="seat-info">\n' +
            '                                            <span>一等座</span>\n' +
            '                                        </div>\n' +
            '                                        <div class="show-time">2018-02-02 15:10:00</div>\n' +
            '                                    </form>\n' +
            '                                    <div class="order-price">￥100</div>\n' +
            '                                    <div class="order-status">已出票</div>\n' +
            '                                </div>';
        $('#used').append(div);

        $('#' + id + ' .order-header .order-date').text(getDate(used[i]['time']));
        $('#' + id + ' .order-header .order-id').text('订单号' + used[i]['id']);
        $('#' + id + ' .order-body .show-name').text(used[i]['showName']);
        $('#' + id + ' .order-body .stadium-name').text(used[i]['stadiumName']);
        $('#' + id + ' .order-body .seat-info').children('span').text(used[i]['seatName']);
        $('#' + id + ' .order-body .show-time').text(getTime(used[i]['showTime']));
        $('#' + id + ' .order-body .order-price').text(used[i]['actualPrice']);
        $('#' + id + ' .order-body .order-status').text(used[i]['orderStatus']);
    }
    for (var i = 0; i < refund.length; i++) {
        var div = document.createElement('div');
        $(div).addClass('order-box');
        var id = 'refund' + refund[i]['id'];
        $(div).attr('id', id);
        div.innerHTML = '<div class="order-header">\n' +
            '                                    <span class="order-date">2018-01-01</span>\n' +
            '                                    <span class="order-id">订单号：1</span>\n' +
            '                                </div>\n' +
            '                                <div class="order-body">\n' +
            '                                    <div class="poster">\n' +
            '                                        <img src="../../../pictrues/show1.jpg">\n' +
            '                                    </div>\n' +
            '                                    <form style="float: left; margin-left: 130px;">\n' +
            '                                        <div class="show-name">《驴得水》</div>\n' +
            '                                        <div class="stadium-name">幸福蓝海国际影城</div>\n' +
            '                                        <div class="seat-info">\n' +
            '                                            <span>一等座</span>\n' +
            '                                        </div>\n' +
            '                                        <div class="show-time">2018-02-02 15:10:00</div>\n' +
            '                                    </form>\n' +
            '                                    <div class="order-price">￥100</div>\n' +
            '                                    <div class="order-status">已出票</div>\n' +
            '<div class="order-cancel">\n' +
            '<label style="width: 100px; margin-left: 0; display: none">退票成功!</label>' +
            '                                        <button name="refund" class="btn" onclick="this.disabled=true;">退票</button>\n' +
            '                                    </div>' +
            '                                </div>';
        $('#refund').append(div);

        $('#' + id + ' .order-header .order-date').text(getDate(refund[i]['time']));
        $('#' + id + ' .order-header .order-id').text('订单号' + refund[i]['id']);
        $('#' + id + ' .order-body .show-name').text(refund[i]['showName']);
        $('#' + id + ' .order-body .stadium-name').text(refund[i]['stadiumName']);
        $('#' + id + ' .order-body .seat-info').children('span').text(refund[i]['seatName']);
        $('#' + id + ' .order-body .show-time').text(getTime(refund[i]['showTime']));
        $('#' + id + ' .order-body .order-price').text(refund[i]['actualPrice']);
        $('#' + id + ' .order-body .order-status').text(refund[i]['orderStatus']);
    }

    $('[name=payButton]').click(function (event) {
        var button = event.target;
        var div = $(button).parent().parent().parent();
        var type = div.parent().attr('id');
        var orderId = div.attr('id');
        var id = orderId.substring(type.length);
        window.location.href = '../pay.html?orderId=' + id;
    });

    $('[name=refund]').click(function (event) {
        var button = event.target;
        var div = $(button).parent().parent().parent();
        var type = div.parent().attr('id');
        var orderId = div.attr('id');
        var id = orderId.substring(type.length);
        $.ajax({
            url: commonUrl + '/ticket/cancel/' + id,
            method: 'get',
            dataType: 'json',
            success: function (data) {
                if (data['result'] === true) {
                    alert('退票成功');
                    // $(button).prev().css('display', 'block');
                    // $(button).css('display', 'none');
                    window.location.reload();
                } else {
                    alert(data['message']);
                }
            },
            error: function () {
                console.log('error');
            }
        })
    });
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