/**
 * Created by wangweizhen on 2018/4/4.
 */
;(function(w){
    seajs.config({
        base: Config.root,
        paths:{
            'common':Config.url+"/common/",
            'modules':Config.url+"/modules"
        }
    })
})(window)