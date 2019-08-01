/**
 * Created by wangweizhen on 2018/4/4.
 */
define(function(require,exports,module){
   var  login={
       isLogin:function(){
           if(sessionStorage.getItem('isLogin')){
               window.location.href=Config.root+"/index.html"
           }
       },
        initEvent:function(){
            this.submitClick();
            $("#qqLogin").click(function(){
                window.location.href ="https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=101541356&state=register&redirect_uri="+encodeURI("http://zhdyd.xyz:8085/api/qqLogin");

                //window.open("/api/qqAuth")
                //window.location.href="https://graph.qq.com/oauth2.0/show?which=Login&display=pc&client_id=101284669&redirect_uri=http%3A%2F%2F47.112.16.249%3A8080%2Fapi%2Fuser%2FqqLogin%3FisType%3Dqq"
            })
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

                error.appendTo(element.parent());
            }
            ,submitHandler:function(){
                    debugger;
                    var userName = $("input[id=userName]").val()
                    var password = $("input[id=password]").val()
                    $.ajax({
                        url:Config.root+"/api/user/webLogin",
                        dataType:'',
                        type:'post',
                        data: {body:JSON.stringify({"userName":userName,"password":md5_encode(password)})},
                        success:function(data){
                            debugger;
                            console.log(data)
                            if(data.success){
                                sessionStorage.setItem("user",data.result);
                              //  document.cookie = "session-ID="+data.result.sessionID;
                                top.location.href=Config.root+"/api/index"
                            }else{
                                alert("登录失败");
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

