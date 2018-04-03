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

    var stadiumId = localStorage.getItem('stadiumId');
    var commonUrl = 'http://localhost:8080/tickets/stadium';
    console.log('stadiumId: ' + stadiumId);
    var getInfoUrl = commonUrl + '/info' + '/' + stadiumId;
    $.ajax(
        {
            url: getInfoUrl,
            method: 'get',
            dataType: 'json',
            success: function (data) {
                var seats = data['seats'];
                setPage(seats);
                var seatInfo = '';
                for (var i = 0; i < seats.length; i++) {
                    seatInfo = seatInfo + seats[i]['name'] + ' ' + seats[i]['amount'] + '，';
                }
                seatInfo = seatInfo.substring(0, seatInfo.length - 1);
            },
            error: function () {
                alert('error');
                console.log("error");
            }
        }
    );
});

function setPage(seats) {
    var tbody = $('#price').children('tbody');
    for (var i = 0; i < seats.length; i++) {
        var tr = document.createElement('tr');
        tr.innerHTML = '<tr>\n' +
            '                                        <td></td>\n' +
            '                                        <td>\n' +
            '                                            <div class="input-group">\n' +
            '                                                <span class="input-group-addon">￥</span>\n' +
            '                                                <input type="text" class="form-control" name="seat_price">\n' +
            '                                            </div>\n' +
            '                                        </td>\n' +
            '                                    </tr>';
        $(tr).attr('id', seats[i]['id']);
        var nameTd = ($(tr).children())[0];
        $(nameTd).text(seats[i]['name']);
        tbody.append(tr);
    }
    releaseShow();
}

function releaseShow() {
    $('#submit').click(function (event) {
        event.preventDefault();
        //showSeatBean传seatNameAndPrice即可
        var prices = $('[name=seat_price]');
        var seatNameAndPrice = {};
        for (var i=0;i<prices.length;i++) {
            var name = $(prices[i]).parent().parent().prev().text();
            var price = $(prices[i]).val();
            seatNameAndPrice[name] = price;
        }
        var showSeatBean = {
            seatNameAndPrice: seatNameAndPrice
        };
        if ($('#name').val().length===0 || $('#time').val().length===0 || seatNameAndPrice.length===0) {
            $('#submit').attr('disabled','true');
            alert("请填写完整信息");
            $('#submit').attr('disabled','false');
            return;
        }
        var showBean = {
            stadiumId: localStorage.getItem('stadiumId'),
            time: new Date($('#time').val()).getTime(),
            name: $('#name').val(),
            type: $('#type').val(),
            showSeatBean: showSeatBean,
            description: $('#description').val()
        };
        if ($('#description').val() === undefined || $('#description').val() === null) {
            showBean['description'] = "";
        }
        $.ajax({
            url: 'http://localhost:8080/tickets/stadium/releaseShow',
            method: 'post',
            contentType: 'application/json',
            data: JSON.stringify(showBean),
            dataType: 'json',
            success:function (result) {
                if (result['result'] === true) {
                    alert('演出发布成功！');
                    window.location.href = 'show.html';
                } else {
                    alert(result['message'])
                }
            },
            error:function () {
                console.log("error");
            }
        })
    });
}