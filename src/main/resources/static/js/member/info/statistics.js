var consumptionChart;
var typeChart;

$(document).ready(function () {
    var loginAndRegisterArea = $('nav .navbar-right');
    var login = loginAndRegisterArea.children().children()[0];
    var register = loginAndRegisterArea.children().children()[1];
    if (localStorage.getItem('memberId')!==null) {
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
            localStorage.removeItem('memberId');
        }
        window.location.href='../login.html';
    });

    consumptionChart = echarts.init(document.getElementById('consumptionDistribution'), 'light');
    var consumptionOption = {
        title: {
            text: '订单使用情况'
        },
        tooltip : {
        },
        legend: {
            bottom: 10,
            left: 'center',
            data: ['已使用', '待使用','已取消']
        },
        series : [
            {
                type: 'pie',
                radius : '65%',
                center: ['50%', '50%'],
                selectedMode: 'single',
                data:[
                ],
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
    consumptionChart.setOption(consumptionOption);

    typeChart = echarts.init(document.getElementById('typeDistribution'), 'light');
    var typeOption = {
        title: {
            text: '不同类型演出预订情况'
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
                data: [
                ],
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

    var url1 = 'http://localhost:8080/tickets/member/statistics/'+ localStorage.getItem('memberId');
    $.ajax({
        url: url1,
        method: 'get',
        dataType: 'json',
        success: function (data) {
            setPage1(data);
        },
        error: function () {
            console.log('error');
        }
    });

    $.ajax({
        url: 'http://localhost:8080/tickets/member/typesAndShows/' + localStorage.getItem('memberId'),
        method: 'get',
        dataType: 'json',
        success: function (data) {
            setPage2(data)
        },
        error: function () {
            console.log('error');
        }
    })
});

function setPage1(statisticsBean) {
    $('#sum').text('￥'+statisticsBean['totalPrice']);
    var map = statisticsBean['statusAndOrder'];
    var all = statisticsBean['orderSum'];
    var used = map['已使用'].length;
    var canceled = map['已取消'].length;
    consumptionChart.setOption({
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

function setPage2(shows) {
    var drama = shows['typesAndShows']['戏剧'].length;
    var sports = shows['typesAndShows']['体育'].length;
    var opera = shows['typesAndShows']['歌剧'].length;
    var vocalConcert = shows['typesAndShows']['演唱会'].length;
    var concert = shows['typesAndShows']['音乐会'].length;
    var dance = shows['typesAndShows']['舞蹈'].length;

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