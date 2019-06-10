$(function () {
    $('.closeIframe').click(function () {
        dialogClose();
    })
});

/**
 * 全选
 * @param param 全选id
 */
function checkAll(param) {
    var check = $(param).prop("checked");
    $(".checkchild").prop("checked", check);
}

/**
 * 获取属性为checked的checkbox
 * @returns {string | *}
 */
function getChecked() {
    var list;
    $.each($('input:checked'),function () {
        if ($(this).val()!=''){
            list+=$(this).val()+','
        }
    })
    return list.substring(0,list.length-1);
}

/**
 * 加载table列表方法
 * @param url  访问地址
 * @param param 参数json格式 {}
 * @operates 行尾操作参数  jsonarr []
 */
function selectTable(url,param,operates){
    var columns=[];
    //动态获取
    $('th').each(function(){
        var id=$(this).attr('path');
        if(id==='checkbox'){
            var column={
                "sClass": "text-center",
                "data": "id",
                "render": function (data, type, full, meta) {
                    return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                },
                "bSortable": false}
            columns.push(column);
        }else if(id==='operates'){
            var column={'sClass':'text-center','data':'id','render':function(data){
                var str='';
                for(var i=0;i<operates.length;i++){
                    var operateJson=operates[i];
                    str+='<a href="javascript:operate(\''+operateJson.key+'\',\''+data+'\')" style="margin: 0 3px">'+operateJson.value+'</a>';
                }
                return str
            }};
            columns.push(column);
        }else{
            var column={};
            column.data=id;
            column.defaultContent='';
            columns.push(column);
        }
    });

    $('#table').dataTable({
        searching: false,
        Paginate: true,
        autoWidth: true,
        bLengthChange: false,
        serverSide: true,
        ordering:false,
        destroy:true,
        language: {
            "sProcessing": "处理中...",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        },
        ajax:function(data,callback,settings){
            // var param = {};//后台data
            param.pageRows = data.length;       //页面显示记录条数，在页面显示每页显示多少项的时候
            param.startIndex = data.start;   //开始的记录序号
            param.pageNumber=(data.start / data.length)+1;//当前页码

            // var result=ajaxCommit(false,'/Manager/User','GET',param);
            var result=ajaxCommit(false,url,'GET',param);
            result=JSON.parse(result.data);
            var returnData = {};
            returnData.draw = data.draw;
            //返回数据全部记录
            returnData.recordsTotal = result.pageCountRows;
            //后台不实现过滤功能，每次查询均视作全部结果
            returnData.recordsFiltered = result.pageCountRows;
            //返回的数据列表
            returnData.data = result.pageList;
            callback(returnData);
        },
        columns: columns
    }).api();
}

/***
 * 模式化打开弹窗
 * @param parent 是否父（true/false）
 * @param title
 * @param width
 * @param height
 * @param url
 * @param full 是否全屏(true/false)
 * @param param 参数  url+'?'+param
 */
function dialogOpen(parent,title,width,height,url,full,param) {
    if (param!='')
        url=url+'?'+param
    if(parent){
        if(full){
            layer.full(parent.layer.open({
                title:title,
                type: 2,
                area: [width, height],
                fixed: false, //不固定
                maxmin: true,
                content: url
            }));
        }else {
            parent.layer.open({
                title:title,
                type: 2,
                area: [width, height],
                fixed: false, //不固定
                maxmin: true,
                content: url
            })
        }
    }else{
        if(full){
            layer.full(layer.open({
                title:title,
                type: 2,
                area: [width, height],
                fixed: false, //不固定
                maxmin: true,
                content: url
            }));
        }else{
            layer.open({
                title:title,
                type: 2,
                area: [width, height],
                fixed: false, //不固定
                maxmin: true,
                content: url
            });
        }
    }
}

/***
 * layer 确认框
 * @param title
 * @returns {*}
 */
function dialogConfirm(title) {
    var message;
    layer.confirm(title, {
        btn: ['确认','取消'] //按钮
    }, function(){
        message=true;
    }, function(){
        message= false;
    });
    return message;
}

/***
 * 关闭窗口
 */
function dialogClose(index) {
    if (index) {
        parent.layer.close(index);
    }else{
        index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }
}


/**
 * 加载字典下拉框
 * @param selectId 下拉框id
 * @param key 字典key
 */
function dictOptions(selectId,key){
    var oneReq=[];
    $.ajax({
        async:false,
        type:"GET",
        url:"/Pub/Dict",
        dataType:"json",
        contentType:"application/json",
        data:{'key':key},
        success:function(data){
            if(data.success===true){
                oneReq = data.dict;
            }
        }
    });
    $("#"+selectId).select2({
        data: oneReq,
        placeholder:'请选择',//默认文字提示
        language: "zh-CN",//汉化
        allowClear: false//允许清空
    })
}

/**
 * 表单验证是否为空
 * @param formid 表单id
 * @param fields 表单每项name   <input name="aaa">
 * @param value  表单每项名称   value='姓名';   value不能为空
 */
function formValidator(formid,name,value) {
    var data={};

    for (var i=0;i<name.length;i++){
        var validators={},field={},notEmpty={message:value[i]+'不能为空'};
        validators['notEmpty']=notEmpty;
        field['validators']=validators;
        data[name[i]]=field;
    }
    $('#'+formid).bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields:data
    });
}

/**
 * select 校验颜色变化
 * @param select    colorChange(this)   this→select
 */
function colorChange(select) {
    if($(select).parent('div').hasClass('has-success')){
        $(select).parent('div').find('span[role="combobox"]').css('border','1px solid #00a65a');
    }else if ($(select).parent('div').hasClass('has-error')){
        $(select).parent('div').find('span[role="combobox"]').css('border','1px solid #dd4b39');
    }
}
/**
 * 直接执行select验证时，框体变色
 * @param formId
 */
function formSelectValid(formId) {
    $.each($('#'+formId).find('select'),function () {
        if($(this).parent('div').hasClass('has-error')){
            $(this).parent('div').find('span[role="combobox"]').css('border','1px solid #dd4b39');
        }
    })
}

/**
 * 获取url问号后参数
 * @param name  参数名
 * @returns {*}
 * @constructor
 */
function getUrlParam(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return unescape(r[2]); return null;
}