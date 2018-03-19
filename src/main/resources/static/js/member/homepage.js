$(document).ready(function () {
    // $('.more').click(function () {
    //     var name = $(this).attr('id');
    //     var navName = name.substring(0,name.indexOf('-'));
    //     var navId = navName + '-' + 'title';
    //     $('#navbar li.active').removeClass('active');
    //     var li = $("[id=" + navId + "]");
    //     li.addClass('active');
    // });
    // $('#info').click(function () {
    //     $('#navbar li.active').removeClass('active');
    //     $(this).addClass('active');
    // })
    var commonUrl = 'http://localhost:8080/tickets/member';
    var getAllShowsUrl = commonUrl + '/allshows';
    $.ajax({
        url: getAllShowsUrl,
        method: 'get',
        dataType: 'json',
        success: function (data) {
            displayShows(data);
        },
        error: function () {

        }
    });
});

function displayShows(data) {
    var typesAndShows = data['typesAndShows'];
    var drama = typesAndShows['戏剧'];
    var sports = typesAndShows['体育'];
    var opera = typesAndShows['歌剧'];
    var vocalConcert = typesAndShows['演唱会'];
    var concert = typesAndShows['音乐会'];
    var dance = typesAndShows['舞蹈'];

    if (drama.length !== 0) {
        var dramaContent = $('.drama ul');
        for (var i = 0; i < (drama.length > 3 ? 3 : drama.length); i++) {
            var li = dramaContent.children().eq(i);
            li.attr('id', drama[i]['id']);
            var currentShow = '#' + drama[i]['id'];
            $(currentShow + ' [name=title]').text(drama[i]['name']);
            var time = getDate(drama[i]['time']);
            $(currentShow + ' .time').text(time);
            var place = "";
            $.ajax({
                url: "http://localhost:8080/tickets/stadium/info/" + drama[i]['stadiumId'],
                method: 'get',
                dataType: 'json',
                success: function (result) {
                    place = result['name'];
                    $(currentShow + ' .place').text(place);
                }
            });
            var seatAndPrice = drama[i]['seatAndPrice'];
            var prices = Object.values(seatAndPrice);
            var max = 0;
            for (var i = 0; i < prices.length; i++) {
                if (prices[i] > max) {
                    max = prices[i];
                }
            }
            $(currentShow + ' .price strong').text(max);
        }
    }
}

function getDate(timestamp) {
    var date = new Date(timestamp);
    Y = date.getFullYear() + '-';
    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    D = date.getDate() + ' ';
    h = date.getHours() + ':';
    m = date.getMinutes() + ':';
    s = date.getSeconds();
    return Y+M+D+h+m+s;
}