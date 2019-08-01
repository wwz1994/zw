/**
 * Created by Administrator on 2018-11-26.
 */
define(function(require,exports,module){
    var product = {
         $that : this,
        initList:function(){
            $('#performance_list_table').bootstrapTable({
                'pagination': true,
                'sidePagination': 'server',
                'url': Config.root + '/web-v/product/productList.json',
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
                    product.delete();
                    product.updateRole();
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
                        'title': '商品价格',
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
                            /*<a  style='cursor:pointer' class='deleteRole' data-id=\""+row.productId+"\" >删除</a>&nbsp;*/
                            return "<a  style='cursor:pointer' class='updateRole' data-type='"+row.businessType+"' data-id=\""+row.productId+"\">编辑</a>";

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
                    url:Config.root+'/web-v/product/deleteProduct',
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
                    url:Config.service+"/product/update_add.html",
                    dataType:'html',
                    callback:function(content){
                        layer.open({
                            shadeClose: false,
                            type: 1,
                            title:'编辑商品',
                            area:['70%','95%'],
                            left: "284",
                            top: "20",
                            fixed: false, //不固定
                            content:content,
                            success:function(){
                                product.updateOrInsert(_this.attr("data-type"));

                                $("#id").val(_this.attr("data-id"));
                                product.initEvent();
                                product.copy();

                            }
                        })
                    }
                })


            })
        },copy:function(){
            $("#cp").click(function(){
               var  index = $("input[name=skuName]:last").attr("id").replace(/skuName/gi,'');
               if(!index){
                   index = 1;
               }else{
                   index++;
               }
               $(".skuname_").append("<input type=\"text\" class=\"form-control\"  data-sku='-1' id=\"skuName"+index+"\"\n" +
                    "                   placeholder=\"请输入商品sku属性\" name=\"skuName\" value=''><a href='javascript:void(0)' id='remove"+index+"' >移除</a>");
                $(".price_").append(" <input type=\"number\" class=\"form-control\" id=\"price"+index+"\" name=\"price\"\n" +
                    "                   placeholder=\"请输入商品价格\" value='0'>")
                product.removeObj(index);
            })
        },removeObj:function(i){
            $("#remove"+i).click(function(){
                $("#skuName"+i).remove();
                $("#price"+i).remove();
                $("#remove"+i).remove();
            })

        },updateOrInsert:function(value){
                if(value){
                    Dao({
                        url:Config.service+"/product/queryProduct",
                        data:{"type":value},
                        type:'post',
                        dataType:'json',
                        callback:function (data) {
                            if(data.success){
                              var object = data.result;
                              if(object&&object.length>0){
                                  $("#productName").val(object[0].productName);
                                  for(var i = 0;i<object.length;i++){
                                      if(i == 0){
                                         $("#skuName").val(object[i].skuName);
                                         $("#skuName").attr("data-sku",object[i].skuId);
                                         $("#price").val(object[i].price);
                                      }else{
                                        $(".skuname_").append("<input type=\"text\" class=\"form-control\" data-sku='"+object[i].skuId+"' id=\"skuName"+i+"\"\n" +
                                              "                   placeholder=\"请输入商品sku属性\" name=\"skuName\" value='"+object[i].skuName+"'><a href='javascript:void(0)' id='remove"+i+"' >移除</a> ");
                                          $(".price_").append(" <input type=\"number\" class=\"form-control\" id=\"price"+i+"\" name=\"price\"\n" +
                                              "                   placeholder=\"请输入商品价格\" value='"+object[i].price+"'>")
                                            product.removeObj(i);
                                      }

                                  }
                              }
                            }
                        }
                    })
                }
        }
        ,initAdd:function(){
            $("#add").click(function(){
                Dao({
                    url:Config.service+"/role/product.html",
                    dataType:'html',
                    callback:function(content){
                        layer.open({
                            shadeClose: false,
                            type: 1,
                            title:'添加商品',
                            area:['70%','95%'],
                            left: "284",
                             top: "20",
                            fixed: false, //不固定
                            content:content,
                            success:function(){
                                //product.initMenu();
                                product.initEvent();
                                product.updateOrInsert();
                            }
                        })
                    }
                })

                return false;

            })
            //PerIndex.add = function(){

        },

        initEvent:function(){
                $("#roleForm").validate({
                    errorClass:"form-label error",
                    rules:{
                        businessType:{
                            required:true
                        }, productName:{
                            required:true,
                            maxlength:100
                        }, skuName:{
                            required:true,
                            maxlength:100
                        }
                    },messages:{
                        businessType:{
                            required:'业务类型不能为空'
                        } , productName:{
                            required:'商品名称不能为空',
                            maxlength:'商品名称最多100个字符'
                        }, skuName:{
                            required:'商品sku名称不能为空',
                            maxlength:'商品sku名称最多100个字符'
                        }
                    },errorPlacement:function(error,ele){
                        L$.layerTip(error[0].innerHTML,ele,{tips:[2,'#d9534f'],tipsMore: true})
                    },submitHandler:function(){
                        var productName = $("input[name=productName]").val()
                        var skuName =  $('input[name=skuName]').map(function(){
                            return this.value;
                        }).toArray();
                        var price = $('input[name=price]').map(function(){
                            return this.value;
                        }).toArray();
                        var skuId = $('input[name=skuId]').map(function(){
                            return this.attr("data-sku");
                        }).toArray();
                        var option = {};
                        option['productName']=productName
                        option['skuName']=skuName
                        option['productId']=$("#id").val()
                        option['price']=price
                        option['skuId']=skuId
                        Dao({
                            url:Config.root+'/web-v/product/saveProduct',
                            data:JSON.stringify(option),
                            type:'post',
                            contentType:'application/json',
                            dataType:'json',
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
    return product;
})