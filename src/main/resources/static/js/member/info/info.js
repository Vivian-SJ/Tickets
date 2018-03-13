$(document).ready(function () {
    var memberId = localStorage.getItem('memberId');
    var commonUrl = 'http://localhost:8080/tickets/member';
    console.log(memberId);
    var getInfoUrl = commonUrl + '/info' + '/' + memberId;
    $.ajax(
        {
            url: getInfoUrl,
            method: 'get',
            dataType: 'json',
            success: function (data) {
                $('#email').text(data['email']);
                $('#name').val(data['name']);
                $('#password').val(data['password']);
                $('#gender').val(data['gender']);
                $('#rank').val(data['rank']);
                $('#credit').val(data['credit']);
            },
            error: function () {
                console.log("error");
            }
        }
    );
    var cancelBtn = $('#cancel-member');
//    取消会员
    cancelBtn.click(function (event) {
        event.preventDefault();
        var url = commonUrl + '/cancel/' + memberId;
        console.log(url);
        $.ajax(
            {
                url: url,
                method: 'get',
                // contentType: 'application/json',
                // data: JSON.stringify(memberBean),
                dataType: 'json',
                success: function (data) {
                    // var data = JSON.parse(result);
                    cancel(data);
                },
                error: function (result) {
                    console.log("error");
                }
            });
    })
});

function cancel(data) {
    if (data['result'] === true) {
        console.log("success");
        $('#valid').text('失效');
        $('#cancel-member').css('display', 'none')
    }
}