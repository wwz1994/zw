package com.bus.control;

import com.alibaba.fastjson.JSONObject;
import com.bus.IMenuService;
import com.bus.control.shiro.ShiroFilterFactoryBean_;
import com.bus.result.BuzCode;
import com.bus.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bus.vo.Menu;

import javax.annotation.Resource;

/**
 * Created by wwz on 2018-11-26.
 */
@Controller
@RequestMapping({"/web-v/menu/"})
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @Resource
    private ShiroFilterFactoryBean_ shiroFilterFactoryBean_;


    /**
     * 菜单列表分页
     * @param object
     * @return
     */
    @RequestMapping("/menuList")
    @ResponseBody
    public Object getRoleList(@RequestBody JSONObject object){
        try{
            return menuService.getPageList(object);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
    /**
     * 菜单列表分页
     * @param menu
     * @return
     */
    @RequestMapping("/saveMenu")
    @ResponseBody
    public JsonResult saveMenu(@RequestBody Menu menu){
        try{
            Integer count = menuService.saveMenu(menu);
            if(count>0){
                shiroFilterFactoryBean_.reloadFilterChains();
            }
            return JsonResult.OK(count);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonResult.Fail(BuzCode.Fail);

    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @RequestMapping("/deleteMenu")
    @ResponseBody
    public JsonResult deleteMenu(Integer id){
        try{
            Integer count = menuService.deleteMenu(id);
            if(count>0){
                shiroFilterFactoryBean_.reloadFilterChains();
            }
            return JsonResult.OK(count);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonResult.Fail(BuzCode.Fail);

    }

    /**
     * 获取菜单
     * @return
     */
    @RequestMapping("/queryMenuJson")
    @ResponseBody
    public Object queryMenuJson(){
        return menuService.queryMenuList();
    }

    /**
     * 菜单列表
     * @return
     */
    @RequestMapping("/menu.html")
    public String menu(){
        return "menu/menu";
    }

    /**
     * 菜单添加页面
     * @return
     */
    @RequestMapping("/menu_add.html")
    public String menu_add(){
        return "menu/menu_add";
    }
}