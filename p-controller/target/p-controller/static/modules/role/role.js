/**
 * Created by Administrator on 2018-11-26.
 */
define(function(require,exports,module){
    var role_add = {
        initList:function(){
            $('#performance_list_table').bootstrapTable({
                'pagination': true,
                'sidePagination': 'server',
                'url': Config.root + '/web-v/role/roleList.json',
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
                    var searchFormData = $('#performance_list_form').formToObject();
                    return common_utils.mergeJson(param, searchFormData);;
                },onLoadSuccess:function(){
                    role_add.delete();
                    role_add.updateRole();
                },
                'striped': true,
                'columns': [
                    {
                        'field': 'roleName',
                        'title': '角色名',
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
                        'field': 'remark',
                        'title': '备注',
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
                            return "<a  style='cursor:pointer' class='deleteRole' data-id=\""+row.id+"\" >删除</a>&nbsp;<a  style='cursor:pointer' class='updateRole' data-id=\""+row.id+"\">编辑</a>";
                            //}
                        }
                    }
                ]
            });
            this.query();
        },query:function(){
            $("#query").on("click",function(){
                $('#performance_list_table').bootstrapTable("refresh");

            })
        },delete:function(){
            $(".deleteRole").on("click",function(){
                var _this = $(this);
				  L$.layerConfirm("是否删除？",function(i){
                Dao({
                    url:Config.root+'/web-v/role/deleteRole',
                    type:'post',
                    data:{id:_this.attr("data-id")},
                    dataType:'json',
                    contentType:"application/x-www-form-urlencoded",
                    callback:function(data){
                        if(data.success){
                          
                                L$.layerAlertSuccess(data.message,function(index){
                                    layer.close(index);
                                    $('#performance_list_table').bootstrapTable("refresh");
                                });
                           

                        }
                    }
                })
				 })

            })
        },updateRole:function(){
            $(".updateRole").click(function(){
                var _this = $(this);
                Dao({
                    url:Config.service+"/role/update_add.html",
                    dataType:'html',
                    callback:function(content){
                        layer.open({
                            shadeClose: false,
                            type: 1,
                            title:'编辑角色',
                            area:['70%','95%'],
                            left: "284",
                            top: "20",
                            fixed: false, //不固定
                            content:content,
                            success:function(){
                                role_add.initMenu(function(){
                                    role_add.getRole(_this.attr("data-id"));
                                });
                                role_add.initEvent();

                            }
                        })
                    }
                })


            })
        },getRole:function(val){
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            Dao({
                url:Config.root+'/web-v/role/getRole.json',
                type:'post',
                data:{id:val},
                dataType:'json',
                callback:function(data){
                    if(data.success){
                        var result = data.result;
                        $("#roleName").val(result.roleName);
                        $("#remark").val(result.remark);
                        $("#roleId").val(result.id);
                        $("#roleCode").val(result.roleCode);
                        var menuId =  result.menuId;
                        if(menuId){
                            var menuIds = menuId.split(",");
                            for(var i in menuIds){

                                var node = zTree.getNodeByParam("id", menuIds[i]);
                                node.checked=true;
                                zTree.updateNode(node);
                            }
                        }
                    }
                }
            })
        },initAdd:function(){
            $("#add").click(function(){
                Dao({
                    url:Config.service+"/role/role_add.html",
                    dataType:'html',
                    callback:function(content){
                        layer.open({
                            shadeClose: false,
                            type: 1,
                            title:'添加角色',
                            area:['70%','95%'],
                            left: "284",
                             top: "20",
                            fixed: false, //不固定
                            content:content,
                            success:function(){
                                role_add.initMenu();
                                role_add.initEvent();
                            }
                        })
                    }
                })

                return false;

            })
            //PerIndex.add = function(){

        },
        initMenu:function(fun){
            Dao({
                url:Config.service+'/role/ztree.html',
                dataType:'html',
                callback:function(data){
                    $("#ztree").html(data);
                   var clear = setInterval(function(){
                       var li = $("#treeDemo>li");
                       if(li&&li.length>0){
                           fun&&fun();
                           clearInterval(clear);
                       }
                    },10)

                }
            })
        },
        initEvent:function(){
                $("#roleForm").validate({
                    errorClass:"form-label error",
                    rules:{
                        roleName:{
                            required:true,
                            maxlength:15
                        }, roleCode:{
                            required:true,
                            maxlength:25
                        }
                    },messages:{
                        roleName:{
                            required:'角色名不能为空',
                            maxlength:'角色名最多15个字符'
                        } , roleCode:{
                            required:'角色编码不能为空',
                            maxlength:'角色编码最多25个字符'
                        }
                    },errorPlacement:function(error,ele){
                        L$.layerTip(error[0].innerHTML,ele,{tips:[2,'#d9534f'],tipsMore: true})
                    },submitHandler:function(){
                        var treeObj= $.fn.zTree.getZTreeObj("treeDemo"),
                            nodes=treeObj.getCheckedNodes(true),
                            name="",
                            menuId=""
                        if(nodes.length==0){
                            L$.layerTip('菜单选项不能为空',$("#treeDemo"),{tips:[2,'#d9534f'],tipsMore: true})
                        }
                        var len = nodes.length;
                        for(var i=0;i<len;i++){
                            menuId+=nodes[i].id;
                            if(len-1!=i){
                                menuId+= ",";
                            }

                        }
                        var option = {menuId:menuId};
                        $.extend(option,$("#roleForm").formToObject());

                        Dao({
                            url:Config.root+'/web-v/role/saveRole',
                            data:{body:JSON.stringify(option)},
                            type:'post',
                            dataType:'',
                            callback:function(data){
                                if(data.success){
                                    var index = layer.alert(data.message,function (){
                                        layer.closeAll();
                                        $('#performance_list_table').bootstrapTable("refresh");
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
    return role_add;
})