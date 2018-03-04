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