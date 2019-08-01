/**
 * Created by Administrator on 2018-11-26.
 */
define(function(require,exports,module){
    var User = {
        initList:function(){
            $('#user_list_table').bootstrapTable({
                'pagination': true,
                'sidePagination': 'server',
                'url': Config.root + '/web-v/user/userList.json',
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
                    var searchFormData = $('#user_list_form').formToObject();
                    return common_utils.mergeJson(param, searchFormData);;
                },onLoadSuccess:function(){
                    User.delete();
                    User.updateRole();
                },
                'striped': true,
                'columns': [
                    {
                        'field': 'userName',
                        'title': '用户名',
                        'align': 'center',
                        'width': '5%',
                        'formatter': function (value) {
                            if (value) {
                                return value;

                            } else {
                                return '-';
                            }
                        }
                    } ,{
                        'field': 'realName',
                        'title': '真实姓名',
                        'align': 'center',
                        'width': '5%',
                        'formatter': function (value) {
                            if (value) {
                                return value;

                            } else {
                                return '-';
                            }
                        }
                    },{
                        'field': 'phone',
                        'title': '手机号',
                        'align': 'center',
                        'width': '5%',
                        'formatter': function (value) {
                            if (value) {
                                return value;

                            } else {
                                return '-';
                            }
                        }
                    },{
                        'field': 'idcard',
                        'title': '身份证号',
                        'align': 'center',
                        'width': '5%',
                        'formatter': function (value) {
                            if (value) {
                                return value;

                            } else {
                                return '-';
                            }
                        }
                    },{
                        'field': 'email',
                        'title': '邮箱',
                        'align': 'center',
                        'width': '5%',
                        'formatter': function (value) {
                            if (value) {
                                return value;

                            } else {
                                return '-';
                            }
                        }
                    },{
                        'field': 'addTime',
                        'title': '添加时间',
                        'align': 'center',
                        'width': '5%',
                        'formatter': function (value) {
                            if (value) {
                                return new Date(value).Format('yyyy-MM-dd');

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
                            return "<a  style='cursor:pointer' class='deleteUser' data-id=\""+row.id+"\" >删除</a>&nbsp;<a  style='cursor:pointer' class='updateUser' data-id=\""+row.id+"\">编辑</a>";
                            //}
                        }
                    }
                ]
            });
            this.query();
        },query:function(){
            $("#query").on("click",function(){
                $('#user_list_table').bootstrapTable("refresh");

            })
        },delete:function(){
            $(".deleteUser").on("click",function(){
                var _this = $(this);
                Dao({
                    url:Config.root+'/web-v/user/deleteUser',
                    type:'post',
                    data:{id:_this.attr("data-id")},
                    dataType:'json',
                    contentType:"application/x-www-form-urlencoded",
                    callback:function(data){
                        if(data.success){
                            L$.layerConfirm("是否删除？",function(i){
                                L$.layerAlertSuccess(data.message,function(index){
                                    layer.close(index);
                                    $('#user_list_add').bootstrapTable("refresh");
                                });
                            })

                        }
                    }
                })

            })
        },updateRole:function(){
            $(".updateUser").click(function(){
                var _this = $(this);
                Dao({
                    url:Config.service+"/user/user_add.html",
                    dataType:'html',
                    callback:function(content){
                        layer.open({
                            shadeClose: false,
                            type: 1,
                            title:'编辑用户',
                            area:['70%','95%'],
                            left: "284",
                            top: "20",
                            fixed: false, //不固定
                            content:content,
                            success:function(){
                                $("#pwd").addClass("display_none");
                                User.initRole(function(){
                                    User.getUserDetail(_this.attr("data-id"));
                                });
                                User.initEvent();

                            }
                        })
                    }
                })


            })
        },getUserDetail:function(val){
            Dao({
                url:Config.root+'/web-v/user/getUser',
                data:{id:val},
                dataType:'json',
                type:'post',
                callback:function(data){
                    if(data.success){
                        var result = data.result;
                       $("#userName_").val( result.user_name);
                       $("#realName").val( result.real_name);
                       $("#phone").val( result.phone);
                       $("#email").val( result.email);
                       $("#idcard").val( result.idcard);
                       $("#roleId").val( result.roleId);
                       $("#id").val( result.id);
                        User.Constant.set(true);
                    }
                }
            })
        },
        initRole:function(fun){
                var _this = $("#roleId");
                Dao({
                    url:Config.root+'/web-v/role/getRoleAll.json',
                    dataType:'json',
                    type:'post',
                    callback:function(data){
                        if(data.success){
                            var options = "<option value=''>请选择</option>";
                            $.each(data.result,function(i,item){
                                options+='<option value="'+item.id+'">'+item.roleName+'</option>'

                            })
                            _this.html(options);
                            fun&&fun();
                        }
                    }
                })
        },initAdd:function(){
            $("#userAdd").click(function(){
                Dao({
                    url:Config.service+"/user/user_add.html",
                    dataType:'html',
                    callback:function(content){
                        layer.open({
                            shadeClose: false,
                            type: 1,
                            title:'添加用户',
                            area:['70%','95%'],
                            left: "300",
                             top: "20",
                            fixed: false, //不固定
                            content:content,
                            success:function(){
                                User.initEvent();
                                User.initPwd();
                                User.initRole();
                            }
                        })
                    }
                })

                return false;

            })
            //PerIndex.add = function(){

        },
        Constant:{
            flag:false,
            userName:'',
            get:function(){
                return this.flag;
            },set:function(f){
                this.flag = f;
            }
        },
        initPwd:function(){
            $("#password_").on("blur",function(){
                var _val = $(this).val();
                layer.prompt({
                    title:'密码确认',
                    formType:'1',
                  },function(pass,index){
                        if(pass!=_val){
                            layer.msg("密码输入不一致！");
                            return false;
                        }else{
                            User.Constant.set(true);
                            User.Constant.userName=$("#userName").val();
                            layer.close(index);
                        }
                })
            });
        },
        initEvent:function(){
                $("#userForm").validate({
                    errorClass:"form-label error",
                    rules:{
                        userName_:{
                            required:true,
                            maxlength:15
                        },realName:{
                            required:true,
                            maxlength:15
                        },password_:{
                            required:true,
                            maxlength:18
                        }
                    },messages:{
                        userName_:{
                            required:"用户名不能为空",
                            maxlength:"用户名最多输入15个字符"
                        },realName:{
                            required:"真实姓名不能为空",
                            maxlength:"真实姓名最多输入15个字符"
                        },password_:{
                            required:"密码不能为空",
                            maxlength:"密码最多输入18个字符"
                        }
                    },errorPlacement:function(error,ele){
                        L$.layerTip(error[0].innerHTML,ele,{tips:[2,'#d9534f'],tipsMore: true})
                    },submitHandler:function(){
                        if(!User.Constant.get()){
                            L$.layerAlertFailed("请先验证密码！");
                            return false;
                        }/*if(User.Constant.userName != $("#userName").val()){
                            L$.layerAlertFailed("验证通过的密码的用户和当前用户不一致，请重新验证！");
                            return false;
                        }*/
                       /* var option = {id:menuId};
                        $.extend(option,$("#roleForm").formToObject());*/
                        var options = $("#userForm").formToObject();
                        $.extend(options,{password: md5_encode($("#password_").val()),userName:$("#userName_").val()});
                        Dao({
                            url:Config.root+'/web-v/user/saveUser',
                            data:JSON.stringify(options),
                            type:'post',
                            contentType:'application/json;charset=utf-8',
                            callback:function(data){
                                if(data.success){
                                    var index = layer.alert(data.message,function (){
                                        layer.closeAll();
                                        $('#user_list_table').bootstrapTable("refresh");
                                        //PerIndex.getIndexData();
                                    });
                                }
                            }
                        })

                    }
                })
           /* })*/

        },initDate:function(){
            Date.prototype.Format = function(fmt)
            {//author:wangweizhen
                var o = {
                    "M+" : this.getMonth()+1,                 //月份   
                    "d+" : this.getDate(),                    //日
                    "h+" : this.getHours(),                   //小时   
                    "m+" : this.getMinutes(),                 //分   
                    "s+" : this.getSeconds(),                 //秒   
                    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
                    "S"  : this.getMilliseconds()             //毫秒   
                };
                if(/(y+)/.test(fmt))
                    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
                for(var k in o)
                    if(new RegExp("("+ k +")").test(fmt))
                        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
                return fmt;
            };
        }
    }
    return User;
})