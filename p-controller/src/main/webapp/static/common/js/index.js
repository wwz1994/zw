/**
 * Created by Administrator on 2018-11-23.
 */
$(function(){
  /*  Dao({
        url:Config.root+'/api/user/validLogin',
        type:'post',
        callback:function(data){
            if(!data.success){
                top.location.href=Config.root+"/web-v/login.html"
            }
        }
    })*/
    var userName = sessionStorage.getItem("userName");
    $("#userName").html(userName);
    var array = sessionStorage.getItem(constant.__utmz1);
	if(!array){
		return;
	}
    var base = new Base64(userName);
    array = base.decode(array);
    if(array&&array.length>0){
        array = JSON.parse(array);
        var html = '';
        for(var i in array){
            var parent = array[i].parent;
            var children = array[i].children;
            if(parent){
                html+='<li id="menu-academico" ><a href="javascript:void(0)" onclick="view(\''+parent.menuUrl+'\',\''+parent.meunName+'\')"><i class="'+parent.menuIcon+'" aria-hidden="true"></i><span>'+parent.meunName+'</span> <span class="fa fa-angle-right" style="float: right"></span><div class="clearfix"></div></a>';
                if(children){
                    html+='<ul id="menu-academico-sub" >';
                    for(var i in children){
                        html+='<li id="menu-academico-avaliacoes" ><a href="javascript:void(0)" onclick="view(\''+children[i].menuUrl+'\',\''+children[i].meunName+'\')">'+children[i].meunName+'</a></li>'
                    }
                    html+='</ul>'
                }
                html+='</li>'
            }
        }
        $("#menu").append(html);
    }
})
var array = [];
function view(url,name){
    if(!name){
        return;
    }
    if(url.includes("api")){
        url = url.replace(/\/api/gi,"");
    }else if(url.includes("web-v")){
        url = url.replace(/\/web-v/gi,"");
    }
    Dao({
        url:Config.service+"/"+url,
        dataType:'html',
        callback:function(data){

            $(".agileinfo-grap").html(data);
            if($.inArray(name,array)==-1){
                var h = '<li class="breadcrumb-item"><a href="javascript:void(0)" onclick="view(\''+url+'\',\''+name+'\')">'+name+'</a> <i class="fa fa-angle-right"></i></li>'
                $(".breadcrumb").append(h);
                array.push(name);
            }else{

            }

        },error:function(xhr){
        }
    })
}