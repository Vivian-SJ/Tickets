$(document).ready(function () {
    var url = 'http://localhost:8080/tickets/stadium/statistics/' + localStorage.getItem('stadiumId');
    $.ajax({
        url: url,
        method: 'get',
        dataType: 'json',
        success: function (data) {
            setPage(data);
        },
        error: function () {
            console.log('error');
        }
    })
});

function setPage(statisticsBean) {
    $('#sum').text('￥' + statisticsBean['totalPrice']);
    var map = statisticsBean['statusAndOrder'];
    var used = map['已使用'];
    var canceled = map['已取消'];
    $('#sum_order').text(statisticsBean['orderSum']);
    $('#used_order').text(used.length);
    $('#canceled_order').text(canceled.length);
}