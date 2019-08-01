/**
 * Created by wangweizhen on 2018/4/4.
 */
;(function(w){
    w.config={
        userId:'isLogin',
        isLogin:function(){
            var item = sessionStorage.getItem('user');
            if(!item){
                this.toLoginPage();
            }
        },toLoginPage:function(){
            document.cookie="session-ID="
            window.location.href =Config.root+ "/login.html";
        }
    }
    config.isLogin();
    w.config = config;
})(window);