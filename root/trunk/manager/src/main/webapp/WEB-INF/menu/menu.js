// var $table = $('#table');
// $(function() {
//     var data=ajaxCommit(false,'/Manager/Menu','GET',{parentId:'00'})
//     //控制台输出一下数据
//     console.log(data.data);
//     $table.bootstrapTable({
//         data:data.data,
//         uniqueId:'id',
//         dataType:'json',
//         columns: [
//             { field:'id',title:'id',width:50},
//             { field: 'name', title: '名称' },
//             { field: 'url', title: 'url'},
//             { field: 'type', title: '菜单级别'},
//         ],
//         // bootstrap-table-treegrid.js 插件配置 -- start
//         //在哪一列展开树形
//         treeShowField: 'name',
//         //指定父id列
//         parentIdField: 'parentId',
//         onResetView: function(data) {
//             //console.log('load');
//             $table.treegrid({
//                 initialState: 'collapsed',// 所有节点都折叠
//                 // initialState: 'expanded',// 所有节点都展开，默认展开
//                 saveState:true,
//                 treeColumn: 1,
//                 // expanderExpandedClass: 'glyphicon glyphicon-minus',  //图标样式
//                 // expanderCollapsedClass: 'glyphicon glyphicon-plus',
//                 onChange: function() {
//                     alert(2)
//                     $table.bootstrapTable('resetWidth');
//                 },
//                 onExpand:function (node) {
//                     // debugger
//                     // var row=$('#table').bootstrapTable('getRowByUniqueId', parseInt($(this).closest("tr").attr('data-uniqueid')));
//                     // //var data=ajaxCommit(false,'/Manager/Menu','GET',{parentId:row.id})
//                     // $('#table').treegrid('option').url='/Manager/Menu?parentId='+row.id
//                     var childNode=$('#table').treegrid('getChildren', node.id);
//                     if(childNode == '' || childNode == undefined){
//                         var childData=ajaxCommit(false,'/Manager/Menu','GET',{parentId:node.id});
//                         $('#table').treegrid('append',{
//                             parent: node.id,
//                             data:childData.data
//                         });
//                     }
//                     return true;
//                 },
//                 onBeforeExpand:function(node){
//                     var childNode=$('#table').treegrid('getChildren', node.id);
//                     if(childNode == '' || childNode == undefined){
//                         var childData=ajaxCommit(false,'/Manager/Menu','GET',{parentId:node.id});
//                         $('#table').treegrid('append',{
//                             parent: node.id,
//                             data:childData.data
//                         });
//                     }
//                     return true;
//                 }
//             });
//             //只展开树形的第一级节点
//             //$table.treegrid('getRootNodes').treegrid('expand');
//         },
//         onUncheck:function(row){
//             var datas = $table.bootstrapTable('getData');
//             selectChilds(datas,row,"id","parentId",false);
//             $table.bootstrapTable('load', datas);
//         }
//         // bootstrap-table-treetreegrid.js 插件配置 -- end
//     });
//     //禁用原鼠标右键菜单
//     $table.contextmenu(function(){
//         return false;
//     });
//     //自定义鼠标右键事件
//     $('table tbody tr').mousedown(function(event){
//         var row=$table.bootstrapTable('getRowByUniqueId',$(this).attr('data-uniqueid'));
//         var e=event||window.event;
//         if(e.button == 2){0.
//             // 显示自定义菜单
//             $("#menu").css({
//                 //定义菜单显示位置为事件发生的X坐标和Y坐标
//                 top : e.clientY-$('.main-header').height()-28,
//                 left : e.clientX-$('.main-sidebar').width()-13
//             }).slideDown(100);
//             //如果点击鼠标左键，隐藏菜单
//         }else if(e.button == 0){
//             $("#menu").slideUp(100);
//         }
//     })
//     // $(document).on('click','.treegrid-expander-collapsed',function () {
//     //     var row=$('#table').bootstrapTable('getRowByUniqueId', parseInt($(this).closest("tr").attr('data-uniqueid')));
//     //     var tableData=$table.bootstrapTable('getData')
//     //     $.each(tableData,function (n,value) {
//     //         debugger
//     //         if (value&&value.parentId==row.id){
//     //             tableData.splice(n,1)
//     //         }
//     //     })
//     //     var data=ajaxCommit(false,'/Manager/Menu','GET',{parentId:row.id})
//     //     $table.bootstrapTable('append', data.data)
//     // })
//     // $('span').click(function () {
//     //
//     // })
// });
/*
easyui实现方式
 */
$(function(){
    var $title=$('title').html();
    if($title!='') {
        //新增页面
        if ($title=='add') {
            formValidator('myform',['name','url'],['菜单名','访问地址']);
        }else if ($title=='update'){
            //修改页面
            var menuId=getUrlParam('id');
            var backData=ajaxCommit(false,'/Manager/Menu/Menu','GET',{id:menuId});
            if (backData.success){
                backData=JSON.parse(backData.data);
                setFormData('myform',backData);
            }
            formValidator('myform',['name','url'],['菜单名','访问地址']);
        }else{
            /**
             * 加载列表页面
             */
            $('#table').treegrid({
                url:'/Pub/MenuTree',    //请求地址
                method:'GET',           //请求方式
                idField:'id',           //定义标识树节点的键名字段
                treeField:'name',       //定义树节点的字段
                fitColumns:false,        //设置为 true，则会自动扩大或缩小列的尺寸以适应网格的宽度并且防止水平滚动。
                columns:[[
                    { field:'id',title:'id',width:'10%'},
                    { field: 'name', title: '名称',width:'30%'},
                    { field: 'url', title: 'url',width:'50%'},
                    { field: 'type', title: '菜单级别',width:'10%'}
                ]],
                state:'closed',
                onContextMenu:function(e, rowIndex, rowData){
                    e.preventDefault();
                    $('#menuId').val(rowIndex.id);
                    $('#childLevel').removeAttr('disabled');
                    if(rowIndex.type==2)
                        $('#childLevel').attr('disabled',true);
                    $("#menu").css({
                        //定义菜单显示位置为事件发生的X坐标和Y坐标
                        top : e.clientY-$('.main-header').height()-28,
                        left : e.clientX-$('.main-sidebar').width()-13
                    }).slideDown(100);
                    //如果点击鼠标左键，隐藏菜单
                },
                onClickRow:function(rowIndex,rowData){
                    $("#menu").slideUp(100);
                },
                onBeforeExpand:function(node){
                    $('#table').treegrid('options').url = "/Pub/MenuTree";
                    //每次执行展开一个节点的操作时
                    //记录被展开的节点ID
                    return true;
                }

            });
            $('.datagrid-wrap').css({'height':document.body.clientHeight-70,'border':'none'});
        }
    }

});

function operate(str,type){
    switch (str) {
        case 'openAdd':
            $("#menu").slideUp(100);
            var id=$('#menuId').val();
            dialogOpen(false,'菜单新增','700px','450px','/menu/add.html',false,'type='+type+'&id='+id);
            break;
        case 'add':
            //获取type值，type属性为同级添加或下级添加
            type=getUrlParam('type');
            var menuId=getUrlParam('id');
            var data = getFormData('myform');
            var backData=ajaxCommit(false,'/Manager/Menu/Menu','GET',{id:menuId});
            if(backData.success) {
                backData=JSON.parse(backData.data);
                if (type == 'same') {//同级
                    data.parentId = backData.parentId;
                    data.type = backData.type;
                } else {//下级
                    data.parentId = backData.id;
                    data.type = parseInt(backData.type) + 1;
                }
                var back = ajaxCommit(false, '/Manager/Menu', 'POST', data);
                if (back.success) {
                    dialogClose();
                    refreshMenu(data.type,data.parentId);
                    parent.layer.msg(back.message);
                } else {
                    parent.layer.msg(back.message);
                }
            }
            break;
        case 'openEdit':
            $("#menu").slideUp(100);
            var id=$('#menuId').val();
            dialogOpen(false,'菜单编辑','500px','350px','/menu/update.html',false,'id='+id);
            break;
        case 'update':
            var data=getFormData('myform');
            var menuId=getUrlParam('id');
            var backData=ajaxCommit(false,'/Manager/Menu/Menu','GET',{id:menuId});
            var back=ajaxCommit(false,'/Manager/Menu','PUT',data);
            if (back.success){
                backData=JSON.parse(backData.data);
                dialogClose();
                refreshMenu(backData.type,backData.parentId);
                parent.layer.msg(back.message);
            } else{
                parent.layer.msg(back.message);
            }
            break;
        case 'del':
            $("#menu").slideUp(100);
            var message;
            var menuId=$('#menuId').val();
            var backData=ajaxCommit(false,'/Manager/Menu/Menu','GET',{id:menuId});
            backData=JSON.parse(backData.data);
            if(backData.type=='1'){
                message='删除后此菜单下子菜单也将删除，是否删除？'
            }else{message='确认删除？'}
            layer.confirm(message, {
                btn: ['确认','取消'] //按钮
            }, function(){
                var back=ajaxCommit(false,'/Manager/Menu','DELETE',{id:menuId});
                if (back.success){
                    refreshMenu(backData.type,backData.parentId);
                    layer.msg(back.message);
                } else {
                    layer.msg(back.message);
                }
            })
            break;
    }
}

/**
 * easyui treegrid 刷新操作
 * @param type  菜单级别
 * @param id    需要刷新的父节点id
 */
function refreshMenu(type,id) {
    if (type==2){
        parent.$("#table").treegrid('reload',id);
    } else {
        parent.$("#table").treegrid('reload');
    }
}
