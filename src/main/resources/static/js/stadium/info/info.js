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
                $('#id').text(data['id']);
                $('#name').val(data['name']);
                $('#password').val(data['password']);
                $('#place').val(data['place']);
                var seats = data['seats'];
                var seatInfo = '';
                for (var i = 0; i < seats.length; i++) {
                    seatInfo = seatInfo + seats[i]['name'] + ' ' + seats[i]['amount'] + '，';
                }
                seatInfo = seatInfo.substring(0, seatInfo.length - 1);
                $('#seats').val(seatInfo);
                $('#description').val(data['description']);
            },
            error: function () {
                alert('error');
                console.log("error");
            }
        }
    );

    var changePs = false;
    $('#password').click(function (event) {
        event.preventDefault();
        $('#passwordAgainArea').removeAttr('hidden');
        changePs = true;
    });

    $('#submit').click(function (event) {
        event.preventDefault();
        var name = $('#name');
        var password = $('#password');
        var passwordAgain = $('#passwordAgain');
        var place = $('#place');
        var seats = $('#seats');
        var message = $('#message');
        if (name.val().length === 0 || password.val().length === 0 || place.val().length === 0 || seats.val().length === 0 || (changePs === true && passwordAgain.val().length === 0)) {
            message.text("请完整填写注册信息");
            message.show();
            return;
        }
        if (changePs===true && password.val() !== passwordAgain.val()) {
            message.text("两次密码不一致,请重新输入");
            message.show();
            passwordAgain.val("");
            return;
        }
        var seatsInfo = seats.val();
        var seatsNameAndAmount = seatsInfo.split('，');
        var seatBeans = [];
        for (var i = 0; i < seatsNameAndAmount.length; i++) {
            var seatInfo = seatsNameAndAmount[i].split(' ');
            var seatBean = {
                name: seatInfo[0],
                amount: seatInfo[1]
            };
            seatBeans.push(seatBean);
        }
        var stadiumBean = {
            id: $('#id').text(),
            name: name.val(),
            password: password.val(),
            place: place.val(),
            description: $('#description').val(),
            seats: seatBeans
        };
        var modifyUrl = commonUrl + '/info/modify';
        $.ajax(
            {
                url: modifyUrl,
                method: 'post',
                contentType: 'application/json',
                data: JSON.stringify(stadiumBean),
                dataType: 'json',
                success: function (result) {
                    if (result.result === true) {
                        window.location.href="info.html";
                    } else {
                        message.text(result.message);
                    }
                },
                error: function () {
                    alert('error');
                    console.log("error");
                }
            });
    })
});
