$(document).ready(function () {
    $('#submit').click(function () {
        var name = $('#name');
        var password = $('#password');
        var passwordAgain = $('#passwordAgain');
        var place = $('#place');
        var seats = $('#seats');
        var message = $('#message');
        if (name.val().length===0 || password.val().length===0||passwordAgain.val().length===0||place.val().length===0|| seats.val().length===0) {
            message.text("请完整填写注册信息");
            message.show();
            return;
        }
        if (password.val() !== passwordAgain.val()) {
            message.text("两次密码不一致,请重新输入");
            message.show();
            passwordAgain.val("");
            return;
        }
        var seatsInfo = seats.val();
        var seatsNameAndAmount = seatsInfo.split('，');
        var seatBeans = [];
        for (var i=0;i<seatsNameAndAmount.length;i++) {
            var seatInfo = seatsNameAndAmount[i].split(' ');
            var seatBean = {
                name : seatInfo[0],
                amount: seatInfo[1]
            };
            seatBeans.push(seatBean);
        }
        var stadiumBean = {
            name: name.val(),
            password: password.val(),
            place: place.val(),
            description: $('#description').val(),
            seats: seatBeans
        };
        $.ajax({
                url: 'http://localhost:8080/tickets/stadium/register',
                method: 'post',
                contentType: 'application/json',
                data: JSON.stringify(stadiumBean),
                dataType: 'json',
                success:function (result) {
                    if (result['resultBean']['result'] === true) {
                        alert('注册成功！您的场馆ID为'+result['id']+'，请牢记此ID。请去登录页面重新登录');
                        window.location.href = 'login.html';
                    } else {
                        alert(result['resultBean']['message'])
                    }
                },
                error:function (result) {
                    console.log("error");
                }
            });
    })
});