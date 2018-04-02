var consumptionChart;
var typeChart;
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
        data: ['已使用', '已取消']
    },
    series : [
        {
            type: 'pie',
            radius : '65%',
            center: ['50%', '50%'],
            selectedMode: 'single',
            data:[
                {value:1548, name: '已使用'},
                {value:535, name: '已取消'}
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
        text: '不同类型演出消费情况'
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

// $(document).ready(function () {
//     var loginAndRegisterArea = $('nav .navbar-right');
//     var login = loginAndRegisterArea.children().children()[0];
//     var register = loginAndRegisterArea.children().children()[1];
//     if (localStorage.getItem('memberId')!==null) {
//         login.innerHTML = '退出登录';
//         $(register).css('display', 'none');
//         $(register).parent().css('display', 'none');
//     } else {
//         alert("请先登录!");
//         return;
//     }
//     $(login).click(function (event) {
//         event.preventDefault();
//         if ($(login).text()==='退出登录') {
//             localStorage.removeItem('memberId');
//         }
//         window.location.href='../login.html';
//     });
//
//     var url = 'http://localhost:8080/tickets/member/statistics/'+ localStorage.getItem('memberId');
//     $.ajax({
//         url: url,
//         method: 'get',
//         dataType: 'json',
//         success: function (data) {
//             setPage(data);
//         },
//         error: function () {
//             console.log('error');
//         }
//     })
// });

// function setPage(statisticsBean) {
//     $('#sum').text('￥'+statisticsBean['totalPrice']);
//     var map = statisticsBean['statusAndOrder'];
//     var used = map['已使用'];
//     var canceled = map['已取消'];
//     $('#sum_order').text(statisticsBean['orderSum']);
//     $('#used_order').text(used.length);
//     $('#canceled_order').text(canceled.length);
// }