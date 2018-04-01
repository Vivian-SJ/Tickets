$(document).ready(function () {
    var loginAndRegisterArea = $('nav .navbar-right');
    var login = loginAndRegisterArea.children().children();
    if (localStorage.getItem('managerId')!==null) {
        login.innerHTML = '退出登录';
    } else {
        alert("请先登录!");
        return;
    }
    $(login).click(function (event) {
        event.preventDefault();
        if ($(login).text()==='退出登录') {
            localStorage.removeItem('managerId');
        }
        window.location.href='login.html';
    });

    $.ajax({
        url: 'http://localhost:8080/tickets/manager/toBePayedAccounts',
        method: 'get',
        dataType:'json',
        success: function (data) {
            setPage(data);
        },
        error: function () {
            console.log('error');
        }
    })
});

function setPage(accounts) {
    for (var i=0;i<accounts.length;i++) {
        var div = document.createElement('div');
        $(div).addClass('pay-box');
        $(div).css('margin-top','95px');
        //注意这里的id不是真正的account的id哦！！！
        var id = i;
        $(div).attr('id', id);
        div.innerHTML = '<div class="pay-header">\n' +
            '                <span class="show-name"></span>\n' +
            '                <span></span>\n' +
            '                <span class="show-name"></span>\n' +
            '            </div>\n' +
            '            <div class="pay-body">\n' +
            '                <div class="poster">\n' +
            '                    <img src="../../pictrues/show1.jpg">\n' +
            '                </div>\n' +
            '                <form style="float: left; margin-left: 130px;">\n' +
            '                    <div class="show-name"></div>\n' +
            '                    <div class="stadium-name"></div>\n' +
            '                    <div class="show-time"></div>\n' +
            '                </form>\n' +
            '                    <ul class="money-box">\n' +
            '                        <li class="price"><span>总收入：</span><span></span></li>\n' +
            '                        <li class="price"><span>支付场馆：</span><span style="color: #4aaf15"></span></li>\n' +
            '                        <li class="price"><span>网站自留：</span><span></span></li>\n' +
            '                    </ul>\n' +
            '                <div class="agree">\n' +
            '                    <button name="payButton" class="btn" onclick="this.disabled=true;">结算</button>\n' +
            '                </div>\n' +
            '            </div>';
        $('.main').append(div);

        var stadiumNameSpan = $('#' + id + ' .pay-header .show-name')[0];
        var stadiumIdSpan = $('#' + id + ' .pay-header .show-name')[1];
        $(stadiumNameSpan).text(accounts[i]['stadiumName']);
        $(stadiumIdSpan).text('场馆编号： '+accounts[i]['stadiumId']);
        $('#' + id +' .pay-body .show-name').text('演出名：《'+accounts[i]['showName']+'》');
        $('#' + id +' .pay-body .stadium-name').text(accounts[i]['stadiumName']);
        $('#' + id +' .pay-body .show-time').text(getTime(accounts[i]['showTime']));
        var totalIncome = accounts[i]['totalIncome'];
        var webIncome = totalIncome * 0.3;
        var stadiumIncome = totalIncome-webIncome;
        var totalIncomeSpan = $($('#' + id +' .pay-body .money-box li')[0]).children('span')[1];
        var stadiumIncomeSpan = $($('#' + id +' .pay-body .money-box li')[1]).children('span')[1];
        var webIncomeSpan = $($('#' + id +' .pay-body .money-box li')[2]).children('span')[1];
        $(totalIncomeSpan).text(totalIncome);
        $(stadiumIncomeSpan).text(stadiumIncome);
        $(webIncomeSpan).text(webIncome);
        accounts[i]['webIncome'] = webIncome;
        accounts[i]['stadiumIncome'] = stadiumIncome;
    }

    $('[name=payButton]').click(function (event) {
        event.preventDefault();
        var btn = event.target;
        var i = $(btn).parent().parent().parent().attr('id');
        $.ajax({
            url: 'http://localhost:8080/tickets/manager/pay',
            method:'post',
            contentType:'application/json',
            data:JSON.stringify(accounts[i]),
            dataType: 'json',
            success: function (result) {
                if (result['result']===true) {
                    alert('结算成功');
                    window.location.reload();
                } else {
                    alert(result['message']);
                }
            },
            error: function () {
                alert('error');
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