$(function(){
    var initEvent = function(){
        return {
            "sku":function(){
                $(".van-sku-row__item").click(function(){
                    $(".van-sku-row__item").removeClass("van-sku-row__item--active");
                    $(this).addClass("van-sku-row__item--active");
                    var price = $(this).attr("data-price");
                    var skuId = $(this).attr("data-sku");
                    $(".van-sku__price-num").hide();
                    $(".van-sku__price-num[data-sku='"+skuId+"']").show();
                })
                return this;
            },"buy":function () {
                var $this = this;
                $(".van_buy").click(function(){
                    var productUrl = $("#productUrl").val();
                    var json = {};
                    var num = $(".van-stepper__input").val();
                    var sku = $(".van-sku-row__item--active");
                    num = !num?1:num;
                    json["num"] = num;
                    json["skuId"] = sku.attr("data-sku");
                    json["price"] = sku.attr("data-price");
                    json["productId"] = $("#productId").val();
                    json["email"] = $("#email").val();
                    json["productUrl"] = productUrl;
                    if(!$this.valid(json)){
                        alert("参数为空");
                        return;
                    }
                    $.ajax({
                        url:'/api/order/saveOrder',
                        data:JSON.stringify(json),
                        contentType:'application/json',
                        dataType:'json',
                        type:'post',
                        success:function(data){
                            if(data.success){
                                alert("下单成功");
                                window.location.href='/api/index'
                            }else{
                                alert(data.message);
                            }
                        }
                    })
                })
                return this;
            },"valid":function(data){
                if(data){
                    for(var i in data){
                        if(!data[i]){
                            return false
                        }
                    }
                    return true;
                }
                return false;
            }
        }
    }
    new initEvent().sku().buy();
})