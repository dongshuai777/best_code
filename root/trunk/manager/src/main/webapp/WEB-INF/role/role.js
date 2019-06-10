/**
 * 创建行内操作按钮
 * @type {Array}
 */
var operates=[];
var menuJson={'key':'openMenu','value':'菜单编辑'};
var editJson={'key':'openEdit','value':'修改'};
var delJson={'key':'del','value':'删除'};
operates.push(menuJson);
operates.push(editJson);
operates.push(delJson);


/**
 * 操作方法
 * @param str
 * @param id
 */
function operate(str,id){
    switch (str) {
        case 'list':
            var param={};
            param.name=$("#name").val();
            param.state=$("#state").val();
            selectTable('/Manager/Role',param,operates);
            break;
        case 'openAdd':
            dialogOpen(false,'角色新增','500px','350px','/role/add.html',false);
            break;
        case 'add':
            //获取表单对象
            var bootstrapValidator = $('#myform').data('bootstrapValidator');
            //手动触发验证
            bootstrapValidator.validate();
            //校验select颜色变化
            formSelectValid('myform');
            if(bootstrapValidator.isValid()) {
                var data = getFormData('myform');
                var back = ajaxCommit(false, '/Manager/Role', 'POST', data);
                if (data.success) {
                    var param={};
                    parent.selectTable('/Manager/Role',param,operates);
                    dialogClose();
                    parent.layer.msg(back.message);
                }else {
                    layer.msg(back.message);
                }
            }
            break;
        case 'del':
            layer.confirm('确认删除？', {
                btn: ['确认','取消'] //按钮
            }, function(){
                var param=[];
                var back=ajaxCommit(false,'/Manager/Role','DELETE',{ids:id});
                if (back.success){
                    selectTable('/Manager/Role',param,operates);
                    layer.msg(back.message);
                } else {
                    layer.msg(back.message);
                }
            })
            break;
        case 'delAll':
            var list='';
            $.each($('input:checked'),function () {
                if ($(this).val()!=''){
                    list+=$(this).val()+','
                }
            })
            list=list.substring(0,list.length-1);
            if(list==''){
                layer.msg('请选择删除项');
                return;
            }
            layer.confirm('确认删除？', {
                btn: ['确认','取消'] //按钮
            }, function(){
                var param=[];
                var back=ajaxCommit(false,'/Manager/Role','DELETE',{ids:list});
                if (back.success){
                    selectTable('/Manager/Role',param,operates);
                    layer.msg(back.message);
                } else {
                    layer.msg(back.message);
                }
            });
            break;
        case 'openEdit':
            dialogOpen(false,'角色编辑','500px','350px','/role/update.html',false,'id='+id);
            break;
        case 'update':
            var data=getFormData('myform');
            var back=ajaxCommit(false,'/Manager/Role','PUT',data);
            if (back.success){
                var param={};
                parent.selectTable('/Manager/Role',param,operates);
                dialogClose();
                parent.layer.msg(back.message);
            } else{
                parent.layer.msg(back.message);
            }
            break;
        case 'openMenu':
            var param='checkbox=true';
            var data={};
            data.roles=id;
            var menus=ajaxCommit(false,'/Manager/Menu/Role','GET',data);
            var arr=menus.data;
            var ids='';
            for(var i=0;i<arr.length;i++){
                ids+=arr[i].id+",";
            }
            param+='&checked='+ids+'&button=true&param='+id;
            dialogOpen(false,'菜单编辑','250px','350px','/Pub/tree_menu.html',false,param);
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
    data.menuIds=ids;
    var result=ajaxCommit(false,'/Manager/Role','PATCH',data);
    if (result.success){
        var param1={};
        parent.selectTable('/Manager/Role',param1,operates);
        var index = layer.getFrameIndex(window.frames[0].name);
        layer.close(index);
        layer.msg(result.message);
    }else{
        layer.msg(result.message);
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
            formValidator('myform',['name','state'],['角色名','状态']);
            dictOptions('state','state');
        }else if ($title=='update'){
            //修改页面
            dictOptions('state','state');
            var roleid=getUrlParam('id');
            var back=ajaxCommit(false,'/Manager/Role','GET',{id:roleid});
            if (back.success){
                back=JSON.parse(back.data);
                setFormData('myform',back.pageList[0]);
            }
            formValidator('myform',['name'],['角色名']);
        }else{
            /**
             * 加载列表页面
             */
            var param={};
            selectTable('/Manager/Role',param,operates);
            dictOptions('state','state');//加载下拉框
        }
    }
})
