/**
 * Created by Administrator on 2018-11-26.
 */
define(function(require,exports,module){
    var order = {
        $that : this,
        initList:function(){
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

            console.log($('#order_list_table').bootstrapTable);
            $('#order_list_table').bootstrapTable({
                'pagination': true,
                'sidePagination': 'server',
                'url': Config.root + '/web-v/order/orderList.json',
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
                    var searchFormData = $('#order_list_form').formToObject();
                    return common_utils.mergeJson(param, searchFormData);;
                },onLoadSuccess:function(){

                },
                'striped': true,
                'columns': [
                    {
                        'field': 'productName',
                        'title': '商品名称',
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
                        'field': 'skuName',
                        'title': '商品属性',
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
                    },{
                        'field': 'price',
                        'title': '商品单价',
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
                    },{
                        'field': 'totalAmount',
                        'title': '总价格',
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
                    },{
                        'field': 'orderStatus',
                        'title': '订单状态',
                        'align': 'center',
                        'width': '5%',
                        'formatter': function (value) {
                            return value=='0'?'待支付':value=='1'?"已支付":value=='2'?"未完成":value=="3"?"已完成":value=='4'?"已发送邮箱":'--';
                        }
                    },{
                        'field': 'businessType',
                        'title': '订单模块',
                        'align': 'center',
                        'width': '5%',
                        'formatter': function (value) {
                            return value=='0'?'模块1':value=='1'?"模块2":value=='2'?"模块3":value=="3"?"模块4":value=='4'?"模块5":'--';
                        }
                    },{
                        'field': 'userId',
                        'title': '用户标识',
                        'align': 'center',
                        'width': '5%',
                        'formatter': function (value) {
                            return value;
                        }
                    },{
                        'field': 'orderDate',
                        'title': '订单创建时间',
                        'align': 'center',
                        'width': '5%',
                        'formatter': function (value) {
                            if(value&&new Date().Format){
                                return new Date(value).Format('yyyy-MM-dd HH:mm:ss');
                            }

                        }
                    },{
                        'field': '',
                        'title': '操作',
                        'align': 'center',
                        'width': '6%',
                        events:{
                            "click .sendEmail":function(e,value,row){
                                var orderid= row.orderId;
                                if(!orderid){
                                    L$.layerAlertFailed("参数为空");
                                    return false;
                                }
                                Dao({
                                    url:'/web-v/order/sendEmail',
                                    data:{"orderId":orderid},
                                    contentType:'application/json',
                                    callback:function(data){
                                        if(data.success){
                                            L$.layerAlertSuccess("操作成功");
                                            $('#order_list_table').bootstrapTable("refresh");
                                        }else{
                                            L$.layerAlertSuccess("操作失败");
                                        }
                                    }
                                })
                            },"click .updateStatus":function(e,value,row){
                                var orderid= row.orderId;
                                if(!orderid){
                                    L$.layerAlertFailed("参数为空");
                                    return false;
                                }
                                    Dao({
                                        url:'/web-v/order/updateOrder',
                                        data:{"orderId":orderid},
                                        contentType:'application/json',
                                        callback:function(data){
                                            if(data.success){
                                                L$.layerAlertSuccess("操作成功");
                                                $('#order_list_table').bootstrapTable("refresh");
                                            }else{
                                                L$.layerAlertSuccess("操作失败");
                                            }
                                        }
                                    })
                            }
                        },
                        'formatter': function (value,row) {
                            var r = []
                            if(row.orderStatus=='1'){
                                r.push('<a  style=\'cursor:pointer\' class=\'updateStatus\' data-type=\'"+row.businessType+"\' data-id=\\""+row.orderId+"\\">完工</a>&nbsp;')
                            } else if(row.orderStatus=='2'){
                                r.push("<a  style='cursor:pointer' class='sendEmail' data-type='"+row.businessType+"' data-id=\""+row.orderId+"\">发送邮箱</a>")
                            }
                            /*<a  style='cursor:pointer' class='deleteRole' data-id=\""+row.orderId+"\" >删除</a>&nbsp;*/
                            return r.join();

                        }
                    }


                ]
            });
            this.query();
        },query:function(){
            $("#query").on("click",function(){
                $('#order_list_table').bootstrapTable("refresh");

            })

        }
    }
    return order;
})