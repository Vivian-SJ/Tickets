$(document).ready(function () {
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
    var a = $('.block a');
    a.click(function (event) {
        event.preventDefault();
        var currentA = event.target;
        console.log(currentA);
        var li;
        var id;
        var stadiumId;
        if(currentA.getAttribute('name')==='title') {
            li = $(currentA).parent().parent().parent().parent();
            id = li.attr('id');
            stadiumId = li.attr('title');
        } else {
            li = $(currentA).parent().parent().parent();
            id = li.attr('id');
            stadiumId = li.attr('title');
        }
        window.location.href = "order-ticket.html?id="+id+"&stadiumId="+stadiumId;
    })
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
            //li的title里面存的是stadiumId
            li.attr('title', drama[i]['stadiumId']);
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
            var seatAndPrice = drama[i]['showSeatBean']['seatNameAndPrice'];
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