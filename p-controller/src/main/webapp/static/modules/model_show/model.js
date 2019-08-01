/**
 * Created by Administrator on 2018-11-26.
 */
define(function(require,exports,module){
    var menu_add = {
        initList:function(){
            $('#performance_list_table').bootstrapTable({
                'pagination': true,
                'sidePagination': 'server',
                'url': Config.root + '/web-v/model/modelList.json',
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
                    //menu_add.delete();
                },
                'striped': true,
                'columns': [
                    {
                        'field': 'model1',
                        'title': '村淘拉新是否展示',
                        'align': 'center',
                        'width': '5%',
                        'formatter': function (value) {
                            if (value=='1') {
                                return '是';

                            } else {
                                return '否';
                            }
                        }
                    },
                    {
                        'field': 'model2',
                        'title': '成交人数任务是否展示',
                        'align': 'center',
                        'width': '5%',
                        'formatter': function (value) {
                            if (value=='1') {
                                return '是';

                            } else {
                                return '否';
                            }
                        }
                    } , {
                        'field': 'model3',
                        'title': '有效订单金额任务是否展示',
                        'align': 'center',
                        'width': '5%',
                        'formatter': function (value) {
                            if (value=='1') {
                                return '是';

                            } else {
                                return '否';
                            }
                        }
                    }, {
                        'field': 'model4',
                        'title': '引流任务是否展示',
                        'align': 'center',
                        'width': '5%',
                        'formatter': function (value) {
                            if (value=='1') {
                                return '是';

                            } else {
                                return '否';
                            }
                        }
                    }, {
                        'field': 'model5',
                        'title': '高佣任务是否展示',
                        'align': 'center',
                        'width': '5%',
                        'formatter': function (value) {
                            if (value=='1') {
                                return '是';

                            } else {
                                return '否';
                            }
                        }
                    }, {
                        'field': 'model6',
                        'title': '免费开店是否展示',
                        'align': 'center',
                        'width': '5%',
                        'formatter': function (value) {
                            if (value=='1') {
                                return '是';

                            } else {
                                return '否';
                            }
                        }
                    },
                    {
                        'field': '',
                        'title': '操作',
                        'align': 'center',
                        'width': '6%',
                        events:{
                            "click #edit":function () {
                                menu_add.initAdd($(this).attr("data-id"));
                            }
                        },
                        'formatter': function (value,row) {
                            /*if(row.data_resource.trim()=='1'&&'监理'==row.punit_unittype_name){
                             return "<a  onclick=PerIndex.toEditPerf('"+row.perf_id+"')>编辑</a>&nbsp;&nbsp;<a  PerIndex.detail('"+row.perf_id+"')>详情</a>";

                             }else{*/
                            return "<a id='edit' data-id='"+row.id+"'>编辑</a>";
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
            this.initAdd();
        },initAdd:function(id){
            $("#edit").click(function(){
                Dao({
                    url:Config.service+"/model/update_add.html",
                    dataType:'html',
                    callback:function(content){
                        layer.open({
                            shadeClose: false,
                            type: 1,
                            title:'更新',
                            area:['70%','95%'],
                            left: "284",
                             top: "20",
                            fixed: false, //不固定
                            content:content,
                            success:function(){
                                menu_add.queryDetail(id);
                                menu_add.initEvent(id);
                                //menu_add.initMenu();
                            }
                        })
                    }
                })

                return false;

            })
            //PerIndex.add = function(){

        },
        queryDetail:function(id){
            Dao({
                url:'/web-v/model/getDetail',
                data:{"id":id},
                contentType:'application/json',
                callback:function(data){
                    var result =data.result;
                   $("input[name=model1]").each(function(i,item){
                       if(  result.model1 == $(item).val()){
                           $(item).prop("checked",true)
                       }
                   })
                    $("input[name=model2]").each(function(i,item){
                        if(  result.model2 == $(item).val()){
                            $(item).prop("checked",true)
                        }                   })
                    $("input[name=model3]").each(function(i,item){
                        if(  result.model3 == $(item).val()){
                            $(item).prop("checked",true)
                        }                   })
                    $("input[name=model4]").each(function(i,item){
                        if(  result.model4 == $(item).val()){
                            $(item).prop("checked",true)
                        }                   })
                    $("input[name=model5]").each(function(i,item){
                        if(  result.model5 == $(item).val()){
                            $(item).prop("checked",true)
                        }                   })
                    $("input[name=model6]").each(function(i,item){
                        if(  result.model6 == $(item).val()){
                            $(item).prop("checked",true)
                        }                   })
                }
            })
        },
        initEvent:function(id){
                $("#modelForm").validate({
                    errorClass:"form-label error",
                    rules:{

                    },messages:{

                    },errorPlacement:function(error,ele){
                        L$.layerTip(error[0].innerHTML,ele,{tips:[2,'#d9534f'],tipsMore: true})
                    },submitHandler:function(){
                        var json = $.extend($("#modelForm").formToObject(),{'id':id})
                        $.ajax({
                            url:Config.root+'/web-v/model/update',
                            data:JSON.stringify( json),
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