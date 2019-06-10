/**
 * 全局js提交
 * @param async true/false
 * @param url 提交路径
 * @param type 提交方式
 * @param data 提交内容
 */
function ajaxCommit(async,url,type,data) {
    var backData={};
    $.ajax({
        headers: {
            'X-AUTH-TOKEN':$.cookie('token')
        },
        async:async,
        type:type,
        url:url,
        dataType:"json",
        data:data,
        success:function(ajaxdata){
            if(!ajaxdata.success&&ajaxdata.code==='50001'){
                window.location.href='login/index.html';
            }else{
                backData=ajaxdata;
            }
        },error:function (ajaxdata,type,err) {
            console.log(err)
            window.location.href='login/index.html';
        }
    });

    return backData;
}

/**
 * 获取提交ajaxCommit的data
 * @param data    需要获取的name属性数组
 * @returns {string}
 */
function ajaxData(data) {
    var message={};
    for (var i=0;i<data.length;i++){
        message[data[i]]=document.getElementsByName(data[i])[0].value;
    }
    return message;
}


/**
 * 根据表单id返回表单内元素json串
 * 根据表单name值拼装
 * @param formId
 */
function getFormData(formId){
    var data={};
    var form=$('#'+formId);
    var inputs=form.find(":input");
    for(var i=0;i<inputs.length;i++){
        var type=inputs[i].type;
        if(type==='text'||type==='password'||type==='hidden'||type.indexOf("select") != -1){
            data[inputs[i].name]=inputs[i].value;
        }
    }
    return data;
}

/**
 * 给表单赋值
 * 只给页面带有path属性的标签赋值
 * @param formId 表单id
 * @param data 要赋值的数据
 */
function setFormData(formId,data){
    var form=$('#'+formId);
    var inputs=form.find(":input");
    for(var i=0;i<inputs.length;i++){
        var type=inputs[i].type;
        if(undefined != $(inputs[i]).attr('path')){
            if(type==='text'||type==='hidden'){
                $(inputs[i]).val(data[$(inputs[i]).attr('path')]);
            }else if(type.indexOf("select") != -1){
                $(inputs[i]).select2().val(data[$(inputs[i]).attr('path')]).trigger("change");
            }
        }
    }
}