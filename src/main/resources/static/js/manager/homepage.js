var amountChart;
var typeChart;
$(document).ready(function () {
    init();
});
function init() {
    var loginAndRegisterArea = $('nav .navbar-right');
    var login = loginAndRegisterArea.children().children();
    if (localStorage.getItem('managerId') !== null) {
        login.innerHTML = '退出登录';
    } else {
        alert("请先登录!");
        return;
    }
    $(login).click(function (event) {
        event.preventDefault();
        if ($(login).text() === '退出登录') {
            localStorage.removeItem('managerId');
        }
        window.location.href = 'login.html';
    });

    amountChart = echarts.init(document.getElementById('amountDistribution'), 'light');
    var amountOption = {
        title: {
            text: '用户/场馆数量统计'
        },
        tooltip: {},
        legend: {
            data: ['数量']
        },
        xAxis: {
            data: ["用户", "场馆"]
        },
        yAxis: {},
        series: [{
            name: '数量',
            type: 'bar',
            data: [50, 20]
        }]
    };
    amountChart.setOption(amountOption);

    typeChart = echarts.init(document.getElementById('typeDistribution'), 'light');
    var typeOption = {
        title: {
            text: '不同类型演出分布情况'
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
                    {value: 1548, name: '音乐会'},
                    {value: 535, name: '舞蹈'},
                    {value: 510, name: '戏剧'},
                    {value: 634, name: '歌剧'},
                    {value: 635, name: '体育'},
                    {value: 100, name: '演唱会'}
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

    $.ajax({
        url: 'http://localhost:8080/tickets/manager/web/statistics',
        method: 'get',
        dataType: 'json',
        success: function (data) {
            setPage1(data)
        },
        error: function () {
            console.log('error');
        }
    });

    $.ajax({
        url: 'http://localhost:8080/tickets/manager/allShows',
        method: 'get',
        dataType: 'json',
        success: function (data) {
            setPage2(data)
        },
        error: function () {
            console.log('error');
        }
    })
}

function setPage1(data) {
    $('#income').text(data['income']+'元');
    amountChart.setOption({
        series: [{
            // 根据名字对应到相应的系列
            name: '数量',
            type: 'bar',
            data: [data['stadiumAmount'], data['memberAmount']]
        }]
    });
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