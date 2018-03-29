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
        setPage(drama, 'drama');
    }
    if (sports.length !== 0) {
        setPage(sports, 'sports');
    }
    if (opera.length !== 0) {
        setPage(opera, 'opera');
    }
    if (vocalConcert.length !== 0) {
        setPage(vocalConcert, 'vocal-concert');
    }
    if (concert.length !== 0) {
        setPage(concert, 'concert');
    }
    if (dance.length !== 0) {
        setPage(dance, 'dance');
    }
}

function setPage(data, type) {
    if (data.length !== 0) {
        var dataContent = $('.'+type+' ul');
        for (var i = 0; i < (data.length > 3 ? 3 : data.length); i++) {
            var li = dataContent.children().eq(i);
            li.attr('id', data[i]['id']);
            //li的title里面存的是stadiumId
            li.attr('title', data[i]['stadiumId']);
            var currentShow = '#' + data[i]['id'];
            $(currentShow + ' [name=title]').text(data[i]['name']);
            var time = getDate(data[i]['time']);
            $(currentShow + ' .time').text(time);
            getPlaceName(i, currentShow, data);
            var seatAndPrice = data[i]['showSeatBean']['seatNameAndPrice'];
            var prices = Object.values(seatAndPrice);
            var min = prices[0];
            console.log('minPrice:'+min);
            for (var j = 1; j < prices.length; j++) {
                if (prices[j] < min) {
                    min = prices[j];
                }
            }
            console.log('minPrice:'+min);
            $(currentShow + ' .price strong').text(min);
        }
    }
}

function getPlaceName(i,currentShow, data) {
    $.ajax({
        url: "http://localhost:8080/tickets/stadium/info/" + data[i]['stadiumId'],
        method: 'get',
        dataType: 'json',
        success: function (result) {
            var place = result['name'];
            $(currentShow + ' .place').text(place);
        }
    });
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