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

    var commonUrl = 'http://localhost:8080/tickets/member';
    var getAllShowsUrl = commonUrl + '/allshows';
    $.ajax({
        url: getAllShowsUrl,
        method: 'get',
        dataType: 'json',
        success: function (data) {
            displayShows(data['typesAndShows']['戏剧']);
        },
        error: function () {
        }
    })
});

function displayShows(data) {
    for (var i=0;i<data.length;i++) {
        var li = document.createElement('li');
        $(li).addClass('li-double');
        li.innerHTML = '<div class="container block">\n' +
            '                                <span class="img-double" style="width:233px">\n' +
            '                                            <img src="../../pictrues/opera.jpg">\n' +
            '                                        </span>\n' +
            '                            <dl class="info-double">\n' +
            '                                <dt><a href="">title1</a></dt>\n' +
            '                                <dd class="txt">\n' +
            '                                    <p class="time">time</p>\n' +
            '                                    <p class="place">place</p>\n' +
            '                                    <p class="price">￥<strong>money</strong>起</p>\n' +
            '                                </dd>\n' +
            '                            </dl>\n' +
            '                        </div>';
        var ul = $('.floor-content-list');
        ul.append(li);
        $('.floor-content-list a').attr('id', 'title'+i);
        $('#title'+i).text(data[i]['name']);
        $('.time').attr('id', 'time'+i);
        $('#time'+i).text(getTime(data[i]['time']));
        $('.place').attr('id', 'place'+i);
        getPlaceName(i,data);
        $('.floor-content-list strong').attr('id', 'money'+i);
        var seatAndPrice = data[i]['showSeatBean']['seatNameAndPrice'];
        var prices = Object.values(seatAndPrice);
        var min = prices[0];
        for (var j = 1; j < prices.length; j++) {
            if (prices[j] < min) {
                min = prices[j];
            }
        }
        $('#money'+i).text(min);
    }
    $('.floor-content-list dt a').click(function () {
        var a = event.target;
        var id =  $(a).attr('id').substring(5);
        window.location.href = 'order-ticket.html?id=' + id + '&stadiumId=' + data[id]['stadiumId'];
    })
}

function getPlaceName(i,data) {
    $.ajax({
        url: "http://localhost:8080/tickets/stadium/info/" + data[i]['stadiumId'],
        method: 'get',
        dataType: 'json',
        success: function (result) {
            $('#place'+i).text(result['name']);
        }
    });
}

function getTime(timestamp) {
    var date = new Date(timestamp);
    Y = date.getFullYear() + '-';
    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    D = date.getDate() + ' ';
    h = date.getHours() + ':';
    m = date.getMinutes() + ':';
    s = date.getSeconds();
    return Y+M+D+h+m+s;
}