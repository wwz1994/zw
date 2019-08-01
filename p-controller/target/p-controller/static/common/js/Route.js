/**
 * Created by wangweizhen on 2018/4/4.
 */
;(function(w){
    function Router(){
        this.routes= {};
        this.currentURL = '';
    }
    Router.prototype.route = function(url,callback){
          this.routes[url] = callback||function(){};
      }
    Router.prototype.refresh = function(){

        this.currentURL = location.hash.slice(1) || '/tologin';
        this.routes[this.currentURL]();
    }
    Router.prototype.init = function () {
        window.addEventListener('load',this.refresh.bind(this),false);
        window.addEventListener('hashchange',this.refresh.bind(this),false);
    }
    w.ro = new Router();
    ro.route("/tologin",function(){
        document.body.style.backgroundColor = '#00ff00'
       // window.location.href="http://localhost:9099/login.html";
    }),ro.route("/red",function(){
        document.body.style.backgroundColor = '#FF0000'
       // window.location.href="http://localhost:9099/login.html";
    })
})(window)