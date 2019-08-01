/**
 * Created by Administrator on 2018-11-26.
 */
define(function(require,exports,module){
    var menu_add = {
        initList:function(){
            $('#menu_list_table').bootstrapTable({
                'pagination': true,
                'sidePagination': 'server',
                'url': Config.root + '/web-v/menu/menuList.json',
                'method': 'post',
                'contentType': 'application/json',
                'dataType': 'json',
                'pageSize': 10,
                'pageNumber': 1,
                'clickToSelect':true,
                'queryParamsType': '',
                'queryParams': '',
                'paginationPreText': '上一页',
                'paginationNextText': '下一页',
                'clickToSelect': true,
                queryParams: function (param) {
                    var searchFormData = $('#menu_list_form').formToObject();

                    return common_utils.mergeJson(param, searchFormData);
                },onLoadSuccess:function(){
                    menu_add.delete();
                },
                'striped': true,
                'columns': [
                    {
                        'field': 'meunName',
                        'title': '菜单名',
                        'align': 'center',
                        'width': '5%',
                        'formatter': function (value) {
                            if (value) {
                                return value;

                            } else {
                                return '-';
                            }
                        }
                    },
                    {
                        'field': 'menuUrl',
                        'title': '地址',
                        'align': 'center',
                        'width': '10%',
                        'formatter': function (value) {
                            if (value) {
                                return '<span title="' + value + '">' + value
                                    + '</span>';
                            } else {
                                return '-';
                            }
                        }
                    }, {
                        'field': 'menuIcon',
                        'title': '菜单图标',
                        'align': 'center',
                        'width': '10%',
                        'formatter': function (value) {
                            if (value) {
                                return '<span title="' + value + '">' + value
                                    + '</span>';
                            } else {
                                return '-';
                            }
                        }
                    }, {
                        'field': 'menuCode',
                        'title': '菜单编码',
                        'align': 'center',
                        'width': '10%',
                        'formatter': function (value) {
                            if (value) {
                                return '<span title="' + value + '">' + value
                                    + '</span>';
                            } else {
                                return '-';
                            }
                        }
                    }, {
                        'field': 'isShow',
                        'title': '是否显示',
                        'align': 'center',
                        'width': '10%',
                        'formatter': function (value) {
                            if (value==-1) {
                                return '<span title="' + value + '">是</span>';
                            } else {
                                return '<span title="' + value + '">否</span>';
                            }
                        }
                    },
                    {
                        'field': '',
                        'title': '操作',
                        'align': 'center',
                        'width': '6%',
                        'formatter': function (value,row) {
                            /*if(row.data_resource.trim()=='1'&&'监理'==row.punit_unittype_name){
                             return "<a  onclick=PerIndex.toEditPerf('"+row.perf_id+"')>编辑</a>&nbsp;&nbsp;<a  PerIndex.detail('"+row.perf_id+"')>详情</a>";

                             }else{*/
                            return "<a  style='cursor:pointer' class='deleteRole' data-id=\""+row.id+"\" >删除</a>&nbsp;<a  onclick=PerIndex.detail('"+row.id+"','"+row.punit_id+"')>详情</a>";
                            //}
                        }
                    }
                ]
            });
            this.query();
        },query:function(){
            $("#query").on("click",function(){
                $('#menu_list_table').bootstrapTable("refresh");

            })
        },initMenu:function(){
            Dao({
                url:Config.service+'/role/ztree.html',
                dataType:'html',
                success:function(data){
                    $("#ztree").html(data);
                }
            })
        },delete:function(){
            $(".deleteRole").on("click",function(){
                var _this = $(this);
                Dao({
                    url:Config.root+'/web-v/menu/deleteMenu',
                    type:'post',
                    data:{id:_this.attr("data-id")},
                    dataType:'json',
                    contentType:"application/x-www-form-urlencoded",
                    callback:function(data){
                        if(data.success){
                            L$.layerConfirm("是否删除？",function(i){
                                L$.layerAlertSuccess(data.message,function(index){
                                    layer.close(index);
                                    $('#menu_list_table').bootstrapTable("refresh");
                                });
                            })

                        }
                    }
                })

            })
        },initAdd:function(){
            $("#add").click(function(){
                Dao({
                    url:Config.service+"/menu/menu_add.html",
                    dataType:'html',
                    callback:function(content){
                        layer.open({
                            shadeClose: false,
                            type: 1,
                            title:'添加菜单',
                            area:['70%','95%'],
                            left: "284",
                             top: "20",
                            fixed: false, //不固定
                            content:content,
                            success:function(){
                                menu_add.initEvent();
                                //menu_add.initMenu();
                                menu_add.selectObj();
                            }
                        })
                    }
                })

                return false;

            })
            //PerIndex.add = function(){

        },
        selectObj:function(){
            $("#isParent").on("change",function(){
                var _this = $(this);
                if(_this.val()==='0'){
                    $("#display").removeClass("display_none");
                    $("#display").addClass("display_inline");
                    Dao({
                        url:Config.root+'/web-v/menu/queryMenuJson.json',
                        dataType:'json',
                        callback:function(result){
                            console.log(result);
                            if(result&&result.length>0){
                                var options = "";
                                $.each(result,function(i,item){
                                   console.log(i);
                                    options+='<option value="'+item.id+'">'+item.name+'</option>'
                                })
                                $("#parentId").html(options);
                            }
                        }
                    })

                }else{
                    $("#display").removeClass("display_inline");
                    $("#display").addClass("display_none");
                    $("#parentId").val("");
                }
            })
        },
        initEvent:function(){
                $("#menuForm").validate({
                    errorClass:"form-label error",
                    rules:{
                        meunName:{
                            required:true,
                            maxlength:15
                        },menuUrl:{
                            required:true,
                            maxlength:50
                        },menuCode:{
                            required:true,
                            maxlength:15
                        },isParent:{
                            required:true
                        }
                    },messages:{
                        meunName:{
                            required:'菜单名不能为空',
                            maxlength:'菜单名最多15个字符'
                        }, menuUrl:{
                            required:'菜单地址不能为空',
                            maxlength:'菜单地址最多50个字符'
                        }, menuCode:{
                            required:'菜单编码不能为空',
                            maxlength:'菜单编码最多15个字符'
                        },isParent:{
                            required:'类型不能为空',
                        }
                    },errorPlacement:function(error,ele){
                        L$.layerTip(error[0].innerHTML,ele,{tips:[2,'#d9534f'],tipsMore: true})
                    },submitHandler:function(){
                        console.log(JSON.stringify( $("#menuForm").formToObject()))
                        $.ajax({
                            url:Config.root+'/web-v/menu/saveMenu',
                            data:JSON.stringify( $("#menuForm").formToObject()),
                            type:'post',
                            dataType:'json',
                            contentType:'application/json;charset=utf-8',
                            success:function(data){
                                if(data.success){
                                    var index = layer.alert(data.message,function (){
                                        layer.closeAll();
                                        $('#menu_list_table').bootstrapTable("refresh");
                                        //PerIndex.getIndexData();
                                    });
                                }
                            }
                        })

                    }
                })
           /* })*/

        }
    }
    return menu_add;
})