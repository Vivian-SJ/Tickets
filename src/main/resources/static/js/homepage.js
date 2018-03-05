$('#header').innerHTML = "<nav class=\"navbar navbar-default navbar-fixed-top\">\n" +
    "    <div class=\"container\">\n" +
    "        <div class=\"navbar-header\">\n" +
    "            <button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#navbar\"\n" +
    "                    aria-expanded=\"false\" aria-controls=\"navbar\">\n" +
    "                <span class=\"sr-only\">Toggle navigation</span>\n" +
    "                <span class=\"icon-bar\"></span>\n" +
    "                <span class=\"icon-bar\"></span>\n" +
    "                <span class=\"icon-bar\"></span>\n" +
    "            </button>\n" +
    "            <h1 class=\"header-left\" style=\"padding-right: 10px\"><strong>Tickets</strong></h1>\n" +
    "        </div>\n" +
    "        <div class=\"navbar-collapse collapse\" style=\"padding-top: 12px\">\n" +
    "            <ul id=\"navbar\" class=\"nav nav-tabs\" style=\"border: none\">\n" +
    "                <li id=\"homepage-title\" class=\"active\"><a href=\"#home\" data-toggle=\"tab\">主页</a></li>\n" +
    "                <li id=\"drama-title\"><a href=\"#drama\" data-toggle=\"tab\">戏剧</a></li>\n" +
    "                <li id=\"opera-title\"><a href=\"#opera\" data-toggle=\"tab\">歌剧</a></li>\n" +
    "                <li id=\"dance-title\"><a href=\"#dance\" data-toggle=\"tab\">舞蹈</a></li>\n" +
    "                <li id=\"sport-title\"><a href=\"#sport\" data-toggle=\"tab\">体育</a></li>\n" +
    "                <li id=\"concert-title\"><a href=\"#concert\" data-toggle=\"tab\">音乐会</a></li>\n" +
    "                <li id=\"vocal-concert-title\"><a href=\"#vocal-concert\" data-toggle=\"tab\">演唱会</a></li>\n" +
    "                <li class=\"dropdown\">\n" +
    "                    <a id=\"info\" href=\"\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">个人信息<span class=\"caret\"></span></a>\n" +
    "                    <ul class=\"dropdown-menu\">\n" +
    "                        <li><a href=\"\" class=\"dropdown-a\" style=\"color: #4aaf51\">基本信息</a></li>\n" +
    "                        <li><a href=\"\" class=\"dropdown-a\" style=\"color: #4aaf51\">我的订单</a></li>\n" +
    "                        <li><a href=\"\" class=\"dropdown-a\" style=\"color: #4aaf51\">我的钱包</a></li>\n" +
    "                        <li><a href=\"\" class=\"dropdown-a\" style=\"color: #4aaf51\">我的优惠券</a></li>\n" +
    "                        <!--<li><a href=\"\" class=\"dropdown-a\" style=\"color: #4aaf51\">我的消费</a></li>-->\n" +
    "                    </ul>\n" +
    "                </li>\n" +
    "                <li class=\"nav navbar-right\" style=\"padding-top: 10px;text-decoration: underline\"><span\n" +
    "                        style=\"color: #4aaf51;\"><a href=\"./\">登录</a></span><span style=\"color: #9d9d9d\">/注册</span>\n" +
    "                </li>\n" +
    "            </ul>\n" +
    "        </div>\n" +
    "    </div>\n" +
    "</nav>";
$(document).ready(function() {

    $('.more').click(function () {
        var name = $(this).attr('id');
        var navName = name.substring(0,name.indexOf('-'));
        var navId = navName + '-' + 'title';
        $('#navbar li.active').removeClass('active');
        var li = $("[id=" + navId + "]");
        li.addClass('active');
    });
    $('#info').click(function () {
        $('#navbar li.active').removeClass('active');
        $(this).addClass('active');
    })
});