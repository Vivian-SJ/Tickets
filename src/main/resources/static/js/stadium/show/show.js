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

    var url = 'http://localhost:8080/tickets/stadium/shows/'+localStorage.getItem('stadiumId');
    console.log('stadiumId:'+ localStorage.getItem('stadiumId'));
    $.ajax({
        url: url,
        method: 'get',
        dataType: 'json',
        success: function (data) {
            console.log(data.length);
            setPage(data);
        },
        error: function () {
            console.log('error');
        }
    })
});

function setPage(shows) {
    var all = [];
    var finish = [];
    var future = [];
    var now = [];
    for (var i = 0; i < shows.length; i++) {
        var date = getDate(shows[i]['time']);
        var newDate = date.replace('-','/');
        var currentDate = new Date();
        var showDate = new Date(Date.parse(newDate));
        if (currentDate>showDate) {
            shows[i]['newStatus']=getStatus('finish');
            finish.push(shows[i]);
        } else if (currentDate < showDate) {
            shows[i]['newStatus']=getStatus('future');
            future.push(shows[i]);
        } else {
            shows[i]['newStatus']=getStatus('now');
            now.push(shows[i]);
        }
        all.push(shows[i]);
    }

    for (var i=0;i<all.length;i++) {
        setSingleTypeOrders(all, 'all', i);
    }
    for (var i=0;i<finish.length;i++) {
        setSingleTypeOrders(finish, 'finish', i);
    }

    for (var i=0;i<future.length;i++) {
        setSingleTypeOrders(future, 'future', i);
    }

    for (var i=0;i<now.length;i++) {
        setSingleTypeOrders(now, 'now', i);
    }
}

function setSingleTypeOrders(data, type, i) {
    var div = document.createElement('div');
    $(div).addClass('show-box');
    var id = type + data[i]['id'];
    $(div).attr('id', id);
    div.innerHTML = '<div class="show-header">\n' +
        '                                    <span class="show-id">演出编号：1</span>\n' +
        '                                </div>\n' +
        '                                <div class="show-body">\n' +
        '                                    <div class="poster">\n' +
        '                                        <img src="../../../pictrues/show1.jpg">\n' +
        '                                    </div>\n' +
        '                                    <form style="float: left; margin-left: 130px;margin-top: 10px;">\n' +
        '                                        <div class="show-name">《驴得水》</div>\n' +
        '                                        <div class="show-type">类型：电影</div>\n' +
        '                                        <div class="show-time">2018-02-02 15:10:00</div>\n' +
        '                                    </form>\n' +
        '                                    <div class="right">\n' +
        '                                        <ul style="list-style: none">\n' +
        '                                        </ul>\n' +
        '                                        <p class="show-status"></p>\n' +
        '                                    </div>\n' +
        '                                </div>';
    $('#'+type).append(div);
    $('#' + id + ' .show-id').text('演出编号：'+data[i]['id']);
    $('#' + id + ' .show-name').text(data[i]['name']);
    $('#' + id + ' .show-type').text(getType(data[i]['type']));
    $('#' + id + ' .show-time').text(getTime(data[i]['time']));
    var ul = $('#' + id +' .right').children('ul');
    var seatNameAndPrice = data[i]['showSeatBean']['seatNameAndPrice'];
    var names = Object.keys(seatNameAndPrice);
    for (var j=0;j<names.length;j++) {
        var li = document.createElement('li');
        $(li).addClass('seat-info');
        var nameSpan = document.createElement('span');
        nameSpan.innerHTML = names[j]+' ';
        var priceSpan = document.createElement('span');
        priceSpan.innerHTML = '￥'+ seatNameAndPrice[names[j]];
        $(li).append(nameSpan);
        $(li).append(priceSpan);
        ul.append(li);
        // $(ul).append(li);
    }
    $('#' + id + ' .show-status').text(data[i]['newStatus']);
}

function getDate(timestamp) {
    var date = new Date(timestamp);
    Y = date.getFullYear() + '-';
    M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    D = date.getDate() + ' ';
    return Y + M + D;
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

function getType(origin) {
    switch (origin){
        case 'DRAMA' : return '戏剧';
        case 'CONCERT': return '音乐会';
        case 'DANCE' : return '舞蹈';
        case 'OPERA': return '歌剧';
        case 'VOCAL_CONCERT' : return '演唱会';
        case 'SPORTS_GAMES' :return '体育';
        default: return '';
    }
}

function getStatus(english) {
    switch (english) {
        case 'finish': return '已完成';
        case 'future': return '即将到来';
        case 'now': return '今日上映';
        default: return '';
    }
}