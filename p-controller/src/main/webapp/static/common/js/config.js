/**
 * Created by wangweizhen on 2018/4/4.
 */
;(function(w){
    w.Auth = function(){

    };
    w.Config={
        root:'',
        base:'',
        service:'/web-v'
    }
    w.constant = {
        "is_login":403
    };
    w.Dao=function(param){
        $.ajax({
            url:param.url,
            type:param.type,
            dataType:param.dataType,
            contentType:param.contentType,
            data:param.data||{},
            success:function(data){
                if(data&&data.code&&data.code==constant.is_login){
                    if(data.code==constant.is_login){
                        config.toLoginPage();
                    }
                }else{
                    if(param.callback){
                        param.callback(data);
                    }

                }

            }
        })
    }
})(window)