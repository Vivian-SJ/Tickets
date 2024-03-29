var timeChart;
var typeChart;
var incomeChart;
var stadiumName = "";
$(document).ready(function () {
    init();
});

function init() {
    var loginAndRegisterArea = $('nav .navbar-right');
    var login = loginAndRegisterArea.children().children()[0];
    var register = loginAndRegisterArea.children().children()[1];
    if (localStorage.getItem('stadiumId') !== null) {
        login.innerHTML = '退出登录';
        $(register).css('display', 'none');
        $(register).parent().css('display', 'none');
        console.log('stadiumId:' + localStorage.getItem('stadiumId'));
    } else {
        alert("请先登录!");
        return;
    }
    $(login).click(function (event) {
        event.preventDefault();
        if ($(login).text() === '退出登录') {
            localStorage.removeItem('stadiumId');
        }
        window.location.href = 'login.html';
    });

// 基于准备好的dom，初始化echarts实例
    timeChart = echarts.init(document.getElementById('timeDistribution'), 'light');

// 指定图表的配置项和数据
    var timeOption = {
        title: {
            text: '演出时间分布'
        },
        tooltip: {},
        legend: {
            data: ['数量']
        },
        xAxis: {
            data: ["已完成", "即将到来", "今日上映"]
        },
        yAxis: {},
        series: [{
            name: '数量',
            type: 'bar',
            data:[]
        }]
    };
// 使用刚指定的配置项和数据显示图表。
    timeChart.setOption(timeOption);

    typeChart = echarts.init(document.getElementById('typeDistribution'), 'light');
    var typeOption = {
        title: {
            text: '发布的演出类型分布'
        },
        tooltip: {},
        legend: {
            // orient: 'vertical',
            // top: 'middle',
            bottom: 10,
            left: 'center',
            data: ['音乐会', '舞蹈', '戏剧', '歌剧', '体育', '演唱会']
        },
        series: [
            {
                type: 'pie',
                radius: '65%',
                center: ['50%', '50%'],
                selectedMode: 'single',
                data: [],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    typeChart.setOption(typeOption);

    incomeChart = echarts.init(document.getElementById('incomeDistribution'), 'light');
    var incomeOption = {
        title: {
            text: '订单使用情况'
        },
        tooltip: {},
        legend: {
            bottom: 10,
            left: 'center',
            data: ['已使用', '待使用','已取消']
        },
        series: [
            {
                type: 'pie',
                radius: '65%',
                center: ['50%', '50%'],
                selectedMode: 'single',
                data: [],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    incomeChart.setOption(incomeOption);

    $.ajax({
        url: 'http://localhost:8080/tickets/stadium/info/' + localStorage.getItem('stadiumId'),
        method: 'get',
        dataType: 'json',
        success: function (data) {
            $('#name').text(data['name']);
            if (data['status_info']!==null) {
                $('#message').text('审核未通过！！！理由:'+data['status_info']);
                $('#message').css('display', 'block')
            }
        },
        error: function () {
            console.log('error');
        }
    });

    var url1 = 'http://localhost:8080/tickets/stadium/shows/' + localStorage.getItem('stadiumId');
    console.log('stadiumId:' + localStorage.getItem('stadiumId'));
    $.ajax({
        url: url1,
        method: 'get',
        dataType: 'json',
        success: function (data) {
            console.log(data.length);
            setPage1(data)
        },
        error: function () {
            console.log('error');
        }
    });

    var url2 = 'http://localhost:8080/tickets/stadium/statistics/' + localStorage.getItem('stadiumId');
    $.ajax({
        url: url2,
        method: 'get',
        dataType: 'json',
        success: function (data) {
            setPage2(data);
        },
        error: function () {
            console.log('error');
        }
    })
}

function setPage1(shows) {
    var finish = 0;
    var future = 0;
    var now = 0;
    var drama = 0;
    var sports = 0;
    var opera = 0;
    var vocalConcert = 0;
    var concert = 0;
    var dance = 0;
    for (var i = 0; i < shows.length; i++) {
        var date = getDate(shows[i]['time']);
        var newDate = date.replace('-', '/');
        var currentDate = new Date();
        var showDate = new Date(Date.parse(newDate));
        if (currentDate > showDate) {
            finish++;
        } else if (currentDate < showDate) {
            future++;
        } else {
            now++;
        }

        switch (getType(shows[i]['type'])) {
            case '戏剧':
                drama++;
                break;
            case '体育':
                sports++;
                break;
            case '歌剧':
                opera++;
                break;
            case '演唱会':
                vocalConcert++;
                break;
            case '音乐会':
                concert++;
                break;
            case '舞蹈':
                dance++;
                break;
            default:
                break;
        }
    }

    timeChart.setOption({
        series: [{
            // 根据名字对应到相应的系列
            name: '数量',
            type: 'bar',
            data: [finish, future, now]
        }]
    });

    typeChart.setOption({
        series: [
            {
                data: [
                    {value: concert, name: '音乐会'},
                    {value: dance, name: '舞蹈'},
                    {value: drama, name: '戏剧'},
                    {value: opera, name: '歌剧'},
                    {value: sports, name: '体育'},
                    {value: vocalConcert, name: '演唱会'}
                ]
            }
        ]
    })
}

function setPage2(statisticsBean) {
    $('#income').text('当前收入：' + statisticsBean['totalPrice'] + '元');
    var map = statisticsBean['statusAndOrder'];
    var all = statisticsBean['orderSum'];
    var used = map['已使用'].length;
    var canceled = map['已取消'].length;
    incomeChart.setOption({
        series: [
            {
                data: [
                    {value: used, name: '已使用'},
                    {value: all-used-canceled, name: '待使用'},
                    {value: canceled, name: '已取消'}
                ]
            }
        ]
    })
}

function getDate(timestamp) {
    var date = new Date(timestamp);
    Y = date.getFullYear() + '-';
    M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    D = date.getDate() + ' ';
    return Y + M + D;
}

function getType(origin) {
    switch (origin) {
        case 'DRAMA' :
            return '戏剧';
        case 'CONCERT':
            return '音乐会';
        case 'DANCE' :
            return '舞蹈';
        case 'OPERA':
            return '歌剧';
        case 'VOCAL_CONCERT' :
            return '演唱会';
        case 'SPORTS_GAMES' :
            return '体育';
        default:
            return '';
    }
}