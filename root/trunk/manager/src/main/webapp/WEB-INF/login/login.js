$(function () {
    $('#addheight').css('height',$(window).height());
    $('button[name="login_btn"]').on('click',function () {
        login();
    })
    $.ajax({
        async:false,
        type:"GET",
        url:"/Login",
        dataType:"json",
        success:function(data){
            if(data.success){
                $('#pk').val(data.pk);
                $('#key').val(data.key);
                console.log(data.systemName);
                $('title').html(data.systemName);
            }else{
                console.log(data.message);
            }
        }
    });
})
function login() {
    var loginName=$('#username').val();
    var pwd=enc($('#password').val(),$('#pk').val());
    console.log(pwd)
    // var pwd=strEnc($('#password').val(),$('#pk').val());
    var key=$('#key').val();
    $.ajax({
        type:"GET",
        url:"/Login/Commit",
        dataType:"json",
        data:{"loginName":loginName,"pwd":pwd,'key':key},
        success:function(data){
            if(data.success){
                $.cookie('token', data.token, { expires: 1 ,path:'/'});//token装入cookie，过期时间为一天
                window.location.href='/home.html';
            }else{
                $('.box-body .callout p').html(data.message);
                $('.box-body .callout').slideDown('slow');
            }
        },error:function (error) {
            $('.box-body .callout p').html('未知错误，请联系系统管理员');
            $('.box-body .callout').slideDown('slow');
        }
    })
}
function on_return(e) {
    var keyNum='';
    if (window.event) {
        keyNum=e.keyCode;
    }else if(e.which){
        keyNum=e.which;
    }
    if(keyNum==13){
        login();
    }
}


function enc(value,key){
    var key = CryptoJS.enc.Utf8.parse(key);
    var srcs = CryptoJS.enc.Utf8.parse(value);
    var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
    return encrypted.toString();
    // return encrypted.ciphertext.toString();
}
