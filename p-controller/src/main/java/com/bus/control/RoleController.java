package com.bus.control;

import com.alibaba.fastjson.JSONObject;
import com.bus.IRoleService;
import com.bus.buzException.BuzEx;
import com.bus.result.BuzCode;
import com.bus.result.JsonResult;
import org.apache.bval.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bus.vo.Role;

import javax.validation.Valid;

/**
 * Created by wwz on 2018-11-25.
 */
@Controller
@RequestMapping("/web-v/role")
public class RoleController extends BaseController {

    @Autowired
    private IRoleService roleService;

    @RequestMapping("/roleList")
    @ResponseBody
    public Object getRoleList(@RequestBody JSONObject object){
        try{
            return roleService.getPageList(object);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 添加角色
     * @param body
     * @return
     */
    @RequestMapping("/saveRole")
    @ResponseBody
    public JsonResult saveRole( String body){
        try{
            Role role = JSONObject.parseObject(body,Role.class);
            return JsonResult.OK(roleService.saveRole(role));
        }catch (BuzEx ex){
            return JsonResult.Fail(ex.getBuzCode());
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonResult.Fail(BuzCode.Fail);

    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @RequestMapping("/deleteRole")
    @ResponseBody
    public JsonResult deleteRole(Integer id){
        try{
            return JsonResult.OK(roleService.deleteRole(id));
        }catch (BuzEx ex){
            return JsonResult.Fail(ex.getBuzCode());
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonResult.Fail(BuzCode.Fail);

    }


    /**
     * 获取角色信息
     * @param id
     * @return
     */
    @RequestMapping("/getRole")
    @ResponseBody
    public JsonResult getRole(@Valid Role role, Errors errors, Integer id){
        if(errors.hasErrors()){
            return JsonResult.Fail(errors.getFieldError().getDefaultMessage());
        }
        try{
            return JsonResult.OK(roleService.getRole(id));
        }catch (BuzEx ex){
            return JsonResult.Fail(ex.getBuzCode());
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonResult.Fail(BuzCode.Fail);

    }

    /**
     * 获取所有角色信息
     * @param
     * @return
     */
    @RequestMapping("/getRoleAll")
    @ResponseBody
    public JsonResult getRoleAll(){
        try{
            return JsonResult.OK(roleService.getRoleAll());
        }catch (BuzEx ex){
            return JsonResult.Fail(ex.getBuzCode());
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonResult.Fail(BuzCode.Fail);

    }

    /**
     * 角色列表
     * @return
     */
    @RequestMapping("/role.html")
    public String role(){
        return "role/role";
    }

    /**
     * 角色添加页面
     * @return
     */
    @RequestMapping("/role_add.html")
    public String role_add(){
        return "role/role_add";
    }

    /**
     * 角色更新页面
     * @return
     */
    @RequestMapping("/update_add.html")
    public String update_add(){
        return "role/update_add";
    }

    /**
     * 角色树形菜单
     * @return
     */
    @RequestMapping("/ztree.html")
    public String ztree(){
        return "role/ztree";
    }
}