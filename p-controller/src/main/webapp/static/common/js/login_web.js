/**
 * Created by wangweizhen on 2018/4/4.
 */
define(function(require,exports,module){
   var  login={
       isLogin:function(){
          /* if(sessionStorage.getItem('isLogin')){
               window.location.href=Config.root+"/index.html"
           }*/
       },
        initEvent:function(){
            this.submitClick();
        },init:function(){
           /*Dao({
               url:Config.root+'/api/user/validLogin',
               type:'post',
               success:function(data){
                   if(data.success){
                       top.location.href=Config.root+"/web-v/index.html"
                   }
               }
           })*/
       },

        submitClick:function(){
            // $("#login").click(function(){
            $("#form").validate({
                errorClass:"form-label error",
                rules:{
                    userName:{
                        required:true,
                        maxlength:30
                    },
                    password:{
                        required:true,
                        maxlength:30
                    }
                },messages:{
                    userName:{
                        required:"用户名不能为空",
                        maxlength:"用户名最大长度为30"
                    },
                    password:{
                        required:"密码不能为空",
                        maxlength:"密码最大长度为30"
                    }
                },
            errorPlacement:function(error,element){
                L$.layerTip(error[0].innerHTML,element,{tips:[2,'#d9534f'],tipsMore: true})
                return;
              /*  error.appendTo(element.parent());*/
            }
            ,submitHandler:function(){
                    $("#submit").prop("disabled",true);
                    var userName = $("input[name=userName]").val()
                    var password = $("input[name=password]").val()
                    $.ajax({
                        url:Config.root+"/api/user/login",
                        dataType:'',
                        type:'post',
                        data: {body:JSON.stringify({"userName":userName,"password":md5_encode(password)})},
                        success:function(data){
                            if(data.success){
                                var base = new Base64(userName);
                                sessionStorage.setItem(constant.__utmz1,base.encode(JSON.stringify(data.result)));
                                sessionStorage.setItem("userName",data.o);
                                //console.log();
                              //  document.cookie = "session-ID="+data.result.sessionID;
                                top.location.href="/web-v/index"
                            }else{
                                $("#submit").removeAttr("disabled");
                                L$.layerAlertFailed(data.message);
                            }
                        }
                    })

                }})
          //  })

        },isNUll:function(val){
            if(!val){
                return true;
            }
            return false;
        }
    }
    return login;
})

