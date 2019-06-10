/**
 * 创建行内操作按钮
 * @type {Array}
 */
var operates=[];
var roleJson={'key':'openRole','value':'编辑角色'}
var editJson={'key':'openEdit','value':'修改'};
var delJson={'key':'del','value':'删除'};
operates.push(roleJson);
operates.push(editJson);
operates.push(delJson);
/**
 * 操作方法
 * @param str
 * @param id
 */
function operate(str,id){
    switch (str) {
        case 'openAdd'://打开新增界面
            dialogOpen(false,'用户新增','700px','450px','/user/add.html',false);
            break;
        case 'add'://新增提交
            //获取表单对象
            var bootstrapValidator = $('#myform').data('bootstrapValidator');
            //手动触发验证
            bootstrapValidator.validate();
            //校验select颜色变化
            formSelectValid('myform');
            if(bootstrapValidator.isValid()){
                var data=getFormData('myform');//获得表单元素
                var back=ajaxCommit(false,'/Manager/User','POST',data);
                if(back.success){
                    var param={};
                    parent.selectTable('/Manager/User',param,operates);
                    dialogClose();
                    parent.layer.msg(back.message);
                }else{
                    layer.msg(back.message);
                }
            }
            break;
        case 'openEdit'://打开修改界面
            dialogOpen(false,'用户编辑','700px','450px','/user/update.html',false,'id='+id);
            break;
        case 'update'://修改提交
            var data=getFormData('myform');
            var back=ajaxCommit(false,'/Manager/User','PUT',data);
            if (back.success){
                var param={};
                parent.selectTable('/Manager/User',param,operates);
                dialogClose();
                parent.layer.msg(back.message);
            } else{
                parent.layer.msg(back.message);
            }
            break;
        case 'del'://单条删除
            layer.confirm('确认删除？', {
                btn: ['确认','取消'] //按钮
            }, function(){
                var param=[];
                var back=ajaxCommit(false,'/Manager/User','DELETE',{ids:id});
                if (back.success){
                    selectTable('/Manager/User',param,operates);
                    layer.msg(back.message);
                } else {
                    layer.msg(back.message);
                }
            })
            break;
        case 'delAll'://批量删除
            var list=getChecked();
            if(list==''){
                layer.msg('请选择删除项');
                return;
            }
            layer.confirm('确认删除？', {
                btn: ['确认','取消'] //按钮
            }, function(){
                var param=[];
                var back=ajaxCommit(false,'/Manager/User','DELETE',{ids:list});
                if (back.success){
                    selectTable('/Manager/User',param,operates);
                    layer.msg(back.message);
                } else {
                    layer.msg(back.message);
                }
            });
            break;
        case 'list'://列表数据
            var param={};
            param.name=$("#name").val();
            param.active=$("#active").val();
            selectTable('/Manager/User',param,operates);
            break;
        case 'upload'://上传头像
            parent.dialogOpen(false,'上传头像','700px','450px','/pub/upload.html',false,'count=1');
            break;
        case 'openRole':
            var param='checkbox=true';
            var data={};
            data.userId=id;
            var roles=ajaxCommit(false,'/Manager/Role/User','GET',data);

            var ids='';
            if(roles.roles!=undefined){
                var arr=roles.roles.split(',');
                for(var i=0;i<arr.length;i++){
                    ids+=arr[i]+",";
                }
            }
            param+='&checked='+ids+'&button=true&param='+id;
            dialogOpen(false,'角色编辑','250px','350px','/Pub/tree_role.html',false,param);
            break;
    }
}

/**
 * 菜单tree页回调函数
 * 对角色菜单进行修改
 * @param ids 选中的复选框值
 */
function treeBack(ids,param){
    var data={};
    data.id=param;
    data.roleIds=ids;
    debugger
    var result=ajaxCommit(false,'/Manager/User','PATCH',data);
    if (result.success){
        var param1={};
        parent.selectTable('/Manager/User',param1,operates);
        var index = layer.getFrameIndex(window.frames[0].name);
        layer.close(index);
        layer.msg(result.message);
    }else{
        layer.msg(result.message);
    }
}

/**
 * 上传文件回调函数
 * 将头像id存入img内
 */
function getUploadFiles(rep){
    var json=JSON.parse(rep.successMessage);
    for(var p in json){//遍历json对象的每个key/value对,p为key
        window.parent.$("#layui-layer-iframe1").contents().find('input[name="imgName"]').val(p);
        window.parent.$("#layui-layer-iframe1").contents().find('input[name="img"]').val(json[p]);
        break;
    }
}

/**
 *  页面加载
 */
$(function () {
    var $title=$('title').html();
    if($title!='') {
        //新增页面
        if ($title=='add') {
            formValidator('myform', ['name', 'loginName', 'pwd', 'password_again', 'active'],
                ['姓名', '用户名', '密码', '再次输入密码', '状态']);
            $('#myform').bootstrapValidator('addField', 'pwd', {
                validators: {identical: {
                        field: 'password_again',
                        message: '两次输入的密码不一致'
                }}});
            $('#myform').bootstrapValidator('addField', 'password_again', {
                validators: {identical: {
                        field: 'pwd',
                        message: '两次输入的密码不一致'
                }}});
            dictOptions('active','active');

        }else if ($title=='update'){//修改页面
            dictOptions('active','active');//加载下拉框
            var userid=getUrlParam('id');
            var back=ajaxCommit(false,'/Manager/User','GET',{id:userid});
            if (back.success){
                back=JSON.parse(back.data);
                setFormData('myform',back.pageList[0]);
                // $('input[name="name"]').val(back.pageList[0].name);
                // $('input[name="loginName"]').val(back.pageList[0].loginName);
                // $("#active").select2().val(back.pageList[0].active).trigger("change");
                // $('#id').val(back.pageList[0].id);
            }
            /**
             * 单独验证
             */
            $('#myform').bootstrapValidator('addField', 'pwd', {
                validators: {identical: {
                        field: 'password_again',
                        message: '两次输入的密码不一致'
                }}});
            $('#myform').bootstrapValidator('addField', 'password_again', {
                validators: {identical: {
                        field: 'pwd',
                        message: '两次输入的密码不一致'
                }}});

        }else{
            /**
             * 加载列表页面
             */
            var param={};
            selectTable('/Manager/User',param,operates);
            dictOptions('active','active');//加载下拉框
        }
    }
})




