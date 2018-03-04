$(document).ready(function() {
    $('.more').click(function () {
        var name = $(this).attr('id');
        console.log(name);
        var navName = name.substring(0,name.indexOf('-'));
        console.log(navName);
        var navId = navName + '-' + 'title';
        console.log(navId);
        $('#navbar li.active').removeClass('active');
        var li = $("[id=" + navId + "]");
        console.log(li.hasClass('active'));
        li.addClass('active');
        console.log(li.hasClass('active'));
    });
});