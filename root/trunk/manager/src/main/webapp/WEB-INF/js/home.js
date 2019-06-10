//主頁主題組
var mySkins = [
    'skin-blue',
    'skin-black',
    'skin-red',
    'skin-yellow',
    'skin-purple',
    'skin-green',
    'skin-blue-light',
    'skin-black-light',
    'skin-red-light',
    'skin-yellow-light',
    'skin-purple-light',
    'skin-green-light'
];
$(function () {
    var data=ajaxCommit(false,'/Manager/Role/User','GET','');
    if (data.success){
        var menu=ajaxCommit(false,'/Manager/Menu/Role','GET',{'roles':data.roles});
        var list=menu.data;
        for (var i=0;i<list.length;i++){
            if(list[i].type==1){
                var new_li='<li class="treeview" parent-id="'+list[i].id+'">' +
                    '                    <a href="#">' +
                    '                        <i class="fa '+list[i].img+'"></i> <span>'+list[i].name+'</span>' +
                    '                        <span class="pull-right-container">' +
                    '                            <i class="fa fa-angle-left pull-right"></i>' +
                    '                        </span>' +
                    '                    </a>' +
                    '                    <ul class="treeview-menu">' +
                    '                    </ul>' +
                    '                </li>';
                $('.sidebar-menu').append(new_li);
            }
        }
        for (var i=0;i<list.length;i++){
            if(list[i].type==2){
                var new_li='<li class="menu-li"><a data-on="'+list[i].url+'.html" href="#"><i class="fa fa-circle-o"></i>'+list[i].name+'</a></li>';
                if ($('li[parent-id="'+list[i].parentId+'"]').length>0){
                    $('li[parent-id="'+list[i].parentId+'"] ul').append(new_li);
                } else{
                    console.log(list[i].name+'无父菜单项');
                }
            }
        }
    }
    //主题颜色变化监听
    $('[data-skin]').on('click', function (e) {
        if ($(this).hasClass('knob'))
            return
        e.preventDefault()
        changeSkin($(this).data('skin'))
    })
    //菜单跳转监听
    $('[data-on]').on('click',function () {
        ChangeCenter($(this).attr('data-on'));
    })
    //菜单点击后位置提醒
    $('.menu-li').click(function () {
        $.each($('li'),function () {
            $(this).removeClass('active');
        })
        $(this).addClass('active');
        $(this).parent().parent().addClass('active');
    })
    var ifm= document.getElementById("content_div");
    ifm.height=document.documentElement.clientHeight-90;

    //获取系统名
    var system=ajaxCommit(false,'/Pub/SystemName','GET',null);
    $('title').html(system.systemName);
    $('span[name=systemName]').text(system.systemName);
    //获取用户信息
    var user=ajaxCommit(false,'/Manager/Home','GET',null);
    $('span[name=userName]').text(user.name);
    $('p[name=userName]').text(user.name);
})

//菜单页面跳转
function ChangeCenter(url) {
    $("#content_div").load(url,function (result) {
        $(result).find('script').appendTo('#content_div');
    });
}

//主题颜色变化事件
function changeSkin(cls) {
    $.each(mySkins, function (i) {
        $('body').removeClass(mySkins[i])
    })

    $('body').addClass(cls)
    store('skin', cls)
    return false
}
function store(name, val) {
    if (typeof (Storage) !== 'undefined') {
        localStorage.setItem(name, val)
    }
}

/**
 * 登出
 */
function loginOut(){
    var result=ajaxCommit(false,'/Manager/Home/LoginOut','GET',null);
    if(result.success===true){
        window.location.href='login/index.html';
    }
}