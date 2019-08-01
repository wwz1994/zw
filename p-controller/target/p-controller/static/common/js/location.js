$(function(){
    $(".menu-card").on("click",function(){
        var url = $(this).attr("data-v-11e7ede0");
        if(!url){
           return;
        }
        $.ajax({
            url:url,
            success:function(data){
                $("body").html(data)
            }
        })
        //window.location.href = url
    })
    $(".van-nav-bar__text").on("click",function(){
        window.location.href='/api/index'
    });
    $(".van-goods-action-button").on("click",function(){
            $(".van-popup").show();
    })
    $(".van-icon-close").on("click",function(){
        $(".van-popup").hide();
    })

    $(".van-stepper__plus").on("click",function(){
        var value = $(this).prev().val();
        $(this).prev().val(parseInt(value)+1)
    })

    $(".van-stepper__minus").on("click",function(){
        var value = $(this).next().val();
        $(this).next().val(parseInt(value)-1<=0?1:parseInt(value)-1)
    })
    $(".van-tab").click(function(){
        $(".van-tab").removeClass("van-tab--active");
        $(this).addClass("van-tab--active");
       var index =  $(this).index();
       $(".van-tab__pane").hide();
       $($(".van-tab__pane")[index]).show();
    })
    /*$(".van_buy").click(function(){
        var url = $(".van-field__control").val();
        if(!url){
            alert("链接不能为空");
            return;
        }
        $.ajax({
            url:'/api/buy',
            data:{"location":"","num":'',"email":''},
            type:'post',
            success:function(data){

            }
        })
    })*/
    $(".van-sku-row__item").click(function(){
        $(".van-sku-row__item").removeClass("van-sku-row__item--active");
        $(this).addClass("van-sku-row__item--active");
        var price = $(this).attr("data-price");
        var skuId = $(this).attr("data-sku");
        $(".van-sku__price-num").hide();
        $(".van-sku__price-num[data-sku='"+skuId+"']").show();
    })
/*    $(window).resize(function() {
        // 变化后需要做的事
        var width = document.documentElement.scrollWidth  || document.body.scrollWidth;
        $(".van-swipe__track").css({"width":width*3+"px"});
        $(".van-swipe-item").css({"width":width+"px"});
    })*/
   /* var i = 0;
    var j = 0;
    var len1 = $(".van-swipe__track").length;
    if(len>1) {
        setInterval(function () {
            var width = document.documentElement.scrollWidth || document.body.scrollWidth;
            $(".van-swipe-item").css({"width": width * 3 + "px"});
            var maxWidth = width * 3;
            i++;
            var initWidth = maxWidth / 3;
            j += initWidth;
            if (j > maxWidth) {
                j = 0;
                j += width;
            } else if (j == maxWidth) {
                $(".van-swipe-item:first").css({"transform": "translateX(-" + j + "px)"})
            }

            $(".van-swipe__track").css({"transform": "translateX(-" + j + "px)"});
        }, 2000);
    }
    var width = document.documentElement.scrollWidth  || document.body.scrollWidth;
    $(".van-swipe-item").css({"width":width+"px"});


    var i1 = 0;
    var j1 = 0;
var len = $(".swiper-wrapper").length;
if(len>1){
    setInterval(function(){
        var width = document.documentElement.scrollWidth  || document.body.scrollWidth;
        $(".swiper-wrapper").css({"width":width*3+"px"});
        var maxWidth = width*3;
        i1++;
        var initWidth  =maxWidth/3;
         j1+=initWidth;
        if(j>maxWidth){
            j=0;
            j+=width;
        }else if(j==maxWidth){
            $(".swipe-item:first").css({"transform":"translateX(-"+j+"px)"})
        }

        $(".swiper-wrapper").css({"transform":"translateX(-"+j+"px)"});
    },2000);
    }
    var width1 = document.documentElement.scrollWidth  || document.body.scrollWidth;
    $(".swipe-item").css({"width":width1+"px"});*/

    $(".van-tabbar-item").click(function(){
        $(this).siblings().removeClass("van-tabbar-item--active")
        $(this).addClass("van-tabbar-item--active");
    })
    $(".van-icon-manager-o").click(function(){
        $("#app").hide();
        $("#app1").show();
    })
    $(".van-icon-home-o").click(function(){
        $("#app1").hide();
        $("#app").show();
    })
    $("#copyUrl").click(function(){
        try{
            var Url2=document.getElementById("dianpuUrl").innerText;
            var oInput = document.createElement('input');
            oInput.value = Url2;
            document.body.appendChild(oInput);
            oInput.select(); // 选择对象
            document.execCommand("Copy"); // 执行浏览器复制命令
            oInput.className = 'oInput';
            oInput.style.display='none';
            alert('复制成功');
        }catch (e){
            alert('复制失败');
        }

    })
})

