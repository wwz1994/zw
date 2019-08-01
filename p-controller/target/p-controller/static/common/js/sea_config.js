/**
 * Created by wangweizhen on 2018/4/4.
 */
;(function(w){
    seajs.config({
        base: Config.root,
        paths:{
            'common':Config.base+"/static/common/",
            'modules':Config.base+"/static/modules/"
        }
    })
})(window)