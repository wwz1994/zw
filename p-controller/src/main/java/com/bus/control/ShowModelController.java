package com.bus.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bus.IShowModelService;
import com.bus.result.JsonResult;
import com.bus.result.PageInfo;
import com.bus.vo.ShowModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wwz
 * @date 2019-07-15
 * @descrption:
 */
@Controller
@RequestMapping({"web-v/model"})
public class ShowModelController {

    @Autowired
    private IShowModelService showModelService;

    /**
     * 更新模块显示
     * @param showModel
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public JsonResult update(@RequestBody ShowModel showModel){
        try{
            if(showModelService.update(showModel)>0){
                return JsonResult.OK();
            }
        }catch (Exception e){

        }
        return JsonResult.Fail();
    }


    @RequestMapping("/modelList")
    @ResponseBody
    public Object modelList(@RequestBody JSONObject object){
        try{
            return showModelService.getPageList(object);

        }catch (Exception e){

        }
        return new PageInfo<>();
    }

    /**
     * 跳转到列表页面
     * @return
     */
    @RequestMapping("/list")
    public String toModelList() {
        return "showModel/model_show";
    }

    @RequestMapping("/update_add.html")
    public String update_add() {
        return "showModel/update_add";
    }

    @RequestMapping("/getDetail")
    public JsonResult getObj(@RequestParam Integer id){
        try{
            return JsonResult.OK(this.showModelService.queryModel());
        }catch (Exception e){

        }
        return JsonResult.Fail();
    }
}