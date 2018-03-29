$(document).ready(function () {
    var url = 'http://localhost:8080/tickets/member/info/' + localStorage.getItem('memberId');
    $.ajax({
        url: url,
        method: 'get',
        dataType: 'json',
        success: function (data) {
            setPage(data);
        },
        error: function () {
            console.log('error')
        }
    })
});

function setPage(memberBean) {
    $('#sum_consumption').text('￥' + memberBean['sum_consumption']);
    $('#money_available').text('￥' + memberBean['money_available']);
}