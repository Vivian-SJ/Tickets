$(document).ready(function () {
    $.ajax({
        url:'http://localhost:8080/tickets/manager/statistics/stadiums',
        method:'get',
        dataType:'json',
        success:function (data) {
            setStadium(data);
        },
        error:function () {

        }
    });

    $.ajax({
        url:'http://localhost:8080/tickets/manager/statistics/members',
        method:'get',
        dataType:'json',
        success:function (data) {
            setMember(data);
        },
        error:function () {

        }
    });
});

function setStadium(stadiumsStatistics) {
    for (var i=0;i<stadiumsStatistics.length;i++) {
        var div = document.createElement('div');
        $(div).addClass('check-box');
        $(div).attr('id', stadiumsStatistics[i]['id']);
        div.innerHTML = '<div class="check-header">\n' +
            '                        <span class="stadium-name">1号场馆</span>\n' +
            '                        <span class="type">ID：1000000</span>\n' +
            '                    </div>\n' +
            '                    <div class="container check-body">\n' +
            '                        <form style="float: left; margin-left: 280px; text-align: left">\n' +
            '                            <div class="text">\n' +
            '                                <span><strong>总收入</strong></span><span\n' +
            '                                    class="content"><strong>100000元</strong></span><br>\n' +
            '                                <span>总订单数</span><span class="content">200</span><br>\n' +
            '                                <span>已使用订单</span><span class="content">100</span><br>\n' +
            '                                <span>已取消订单</span><span class="content">100</span><br>\n' +
            '                            </div>\n' +
            '                        </form>\n' +
            '                    </div>';
        $('#stadium').append(div);

        var id = $(div).attr('id');
        $('#' + id + ' .stadium-name').text(stadiumsStatistics[i]['name']);
        $('#' + id + ' .type').text('ID:' + stadiumsStatistics[i]['id']);
        $($('#' + id + ' .check-body form .text strong')[1]).text(stadiumsStatistics[i]['statisticsBeanForMemberAndStadium']['totalPrice'] + '元');
        $($('#' + id + ' .check-body form span')[3]).text(stadiumsStatistics[i]['statisticsBeanForMemberAndStadium']['orderSum']);
        $($('#' + id + ' .check-body form span')[5]).text((stadiumsStatistics[i]['statisticsBeanForMemberAndStadium']['statusAndOrder']['已使用']).length);
        $($('#' + id + ' .check-body form span')[7]).text((stadiumsStatistics[i]['statisticsBeanForMemberAndStadium']['statusAndOrder']['已取消']).length);
    }
}

function setMember(membersStatistics) {
    for (var i=0;i<membersStatistics.length;i++) {
        var div = document.createElement('div');
        $(div).addClass('check-box');
        $(div).attr('id', membersStatistics[i]['id']);
        div.innerHTML = '<div class="check-header">\n' +
            '                        <span class="stadium-name">1号场馆</span>\n' +
            '                        <span class="type">ID：1000000</span>\n' +
            '                    </div>\n' +
            '                    <div class="container check-body">\n' +
            '                        <form style="float: left; margin-left: 280px; text-align: left">\n' +
            '                            <div class="text">\n' +
            '                                <span><strong>总消费</strong></span><span\n' +
            '                                    class="content"><strong>100000元</strong></span><br>\n' +
            '                                <span>总订单数</span><span class="content">200</span><br>\n' +
            '                                <span>已使用订单</span><span class="content">100</span><br>\n' +
            '                                <span>已取消订单</span><span class="content">100</span><br>\n' +
            '                            </div>\n' +
            '                        </form>\n' +
            '                    </div>';
    $('#member').append(div);

    var id = $(div).attr('id');
    $('#'+ id + ' .stadium-name').text(membersStatistics[i]['name']);
    $('#'+ id + ' .type').text('ID:'+membersStatistics[i]['id']);
    $($('#'+ id + ' .check-body form .text strong')[1]).text(membersStatistics[i]['statisticsBeanForMemberAndStadium']['totalPrice']+'元');
    $($('#'+ id + ' .check-body form span')[3]).text(membersStatistics[i]['statisticsBeanForMemberAndStadium']['orderSum']);
    $($('#'+ id + ' .check-body form span')[5]).text((membersStatistics[i]['statisticsBeanForMemberAndStadium']['statusAndOrder']['已使用']).length);
    $($('#'+ id + ' .check-body form span')[7]).text((membersStatistics[i]['statisticsBeanForMemberAndStadium']['statusAndOrder']['已取消']).length);
    }
}