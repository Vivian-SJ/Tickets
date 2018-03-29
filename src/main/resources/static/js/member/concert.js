$(document).ready(function () {
    var commonUrl = 'http://localhost:8080/tickets/member';
    var getAllShowsUrl = commonUrl + '/allshows';
    $.ajax({
        url: getAllShowsUrl,
        method: 'get',
        dataType: 'json',
        success: function (data) {
            displayShows(data['typesAndShows']['音乐会']);
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
            '                                <span class="img-double">\n' +
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
        $('a').attr('id', 'title'+i);
        $('a').text(data[i]['name']);
        $('.time').attr('id', 'time'+i);
        $('.time').text(getTime(data[i]['time']));
        $('.place').attr('id', 'place'+i);
        $.ajax({
            url: "http://localhost:8080/tickets/stadium/info/" + data[i]['stadiumId'],
            method: 'get',
            dataType: 'json',
            success: function (result) {
                $('.place').text(result['name']);
            }
        });
        $('strong').attr('id', 'money'+i);
        var seatAndPrice = data[i]['showSeatBean']['seatNameAndPrice'];
        var prices = Object.values(seatAndPrice);
        var min = prices[0];
        for (var i = 1; i < prices.length; i++) {
            if (prices[i] < min) {
                min = prices[i];
            }
        }
        $('strong').text(min);
    }
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