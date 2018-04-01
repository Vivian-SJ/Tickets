$(document).ready(function () {
    $.ajax({
        url: 'http://localhost:8080/tickets/stadium/uncheckStadiums',
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

function setPage(stadiums) {
    var all = [];
    var register = [];
    var modify = [];
    for (var i = 0; i < stadiums.length; i++) {
        all.push(stadiums[i]);
        var status = stadiums[i]['status'];
        if (status.length > 3) {
            modify.push(stadiums[i]);
        } else {
            register.push(stadiums[i]);
        }
    }
    for (var i = 0; i < all.length; i++) {
        setSinglePage(all[i], 'all');
    }

    for (var i = 0; i < register.length; i++) {
        setSinglePage(register[i], 'register');
    }

    for (var i = 0; i < modify.length; i++) {
        setSinglePage(modify[i], 'modify');
    }

    check(stadiums);
}

function setSinglePage(data, type) {
    var div = document.createElement('div');
    $(div).addClass('check-box');
    var id = type + data['id'];
    $(div).attr('id', id);
    div.innerHTML = '                    <div class="check-header">\n' +
        '                        <span class="stadium-name">1号场馆</span>\n' +
        '                        <span class="type">（注册申请）</span>\n' +
        '                    </div>\n' +
        '                    <div class="container check-body">\n' +
        '                        <form style="float: left; margin-left: 80px; text-align: left">\n' +
        '                            <div class="text">\n' +
        '                                <span>场馆名称:</span><span class="content">1号场馆</span><br>\n' +
        '                                <span>场馆地址:</span><span class="content">南京新街口</span><br>\n' +
        '                                <div class="container seat-box">\n' +
        '                                    <span>场馆座位信息:</span>\n' +
        '                                    <ul class="content" style="list-style: none">\n' +
        '                                    </ul>\n' +
        '                                </div>\n' +
        '                                <div class="container des-box">\n' +
        '                                    <span>场馆描述:</span><span class="content">描述啊描述</span><br>\n' +
        '                                </div>\n' +
        '                            </div>\n' +
        '                        </form>\n' +
        '                        <div class="agree" style="margin-right: 35px">\n' +
        '                            <button class="btn" name="sure">通过审核</button>\n' +
        '                            <button class="btn" name="refuse">拒绝请求</button>\n' +
        '                            <div style="display: none;border: 1px solid #e5e5e5;padding: 10px;">\n' +
        '                                <h4>拒绝理由：</h4>\n' +
        '                                <input type="text" name="refuseInfo" style="width: 390px"><br>\n' +
        '                                <button class="btn" name="sureRefuse">确定</button>\n' +
        '                            </div>\n' +
        '                        </div>\n' +
        '                    </div>';
    $('#' + type).append(div);
    $('#' + id + ' .stadium-name').text(data['name']);
    var status = '';
    if (type === 'register' || (type === 'all' && data['status'].length === 3)) {
        status = '(注册申请)';
    } else {
        status = '(修改申请)';
    }
    $('#' + id + ' .type').text(status);
    var nameSpan = $('#' + id + ' .check-body .text span.content')[0];
    $(nameSpan).text(data['name']);
    var addressSpan = $('#' + id + ' .check-body .text span.content')[1];
    $(addressSpan).text(data['place']);
    var seatUl = $('#' + id + ' .check-body .text ul');
    var seats = data['seats'];
    for (var i = 0; i < seats.length; i++) {
        var li = document.createElement('li');
        li.innerHTML = '<span>' + seats[i]['name'] + ' ' + seats[i]['amount'] + '个' + '</span>';
        $(seatUl).append(li);
    }
    $('#' + id + ' .des-box span.content').text(data['description']);
}

function check(stadiums) {
    $('[name=sure]').click(function (event) {
        event.preventDefault();
        var sureBtn = event.target;
        var refuseBtn = $(sureBtn).next();
        $(refuseBtn).attr('disabled',true);
        var data = {};
        var div = $(sureBtn).parent().parent().parent();
        var type = div.parent().attr('id');
        var stadiumId = div.attr('id').substring(type.length);
        for(var i=0;i<stadiums.length;i++) {
            if (stadiums[i]['id']===parseInt(stadiumId)) {
                data = stadiums[i];
                break;
            }
        }
        $.ajax({
            url: 'http://localhost:8080/tickets/manager/check',
            method:'post',
            dataType:'json',
            contentType:'application/json',
            data: JSON.stringify(data),
            success: function (result) {
                if (result['result']===true) {
                    alert('审核成功！');
                    window.location.reload();
                } else {
                    alert(result['message']);
                }
            },
            error:function () {
                console.log('error');
            }
        })
    });
    $('[name=refuse]').click(function (event) {
        event.preventDefault();
        var refuseBtn = event.target;
        var sureBtn = $(refuseBtn).prev();
        $(sureBtn).attr('disabled','true');
        var refuseBlock = $(refuseBtn).next();
        $(refuseBlock).css('display', 'block');
        var sureRefuse = $(refuseBlock).children('button');
        $(sureRefuse).click(function (event) {
            event.preventDefault();
            var refuseInfo = $(sureRefuse).prev().prev();
            var message = $(refuseInfo).val();
            var div = $(sureBtn).parent().parent().parent();
            var type = div.parent().attr('id');
            var stadiumId = div.attr('id').substring(type.length);
            $.ajax({
                url: 'http://localhost:8080/tickets/manager/check/refuse',
                method:'post',
                dataType:'json',
                data: {
                    stadiumId: stadiumId,
                    message: message
                },
                success: function (result) {
                    if (result['result']===true) {
                        alert('审核成功！');
                        location.reload();
                    } else {
                        alert(result['message']);
                    }
                },
                error: function () {
                    console.log('error');
                }
            })
        })
    })
}