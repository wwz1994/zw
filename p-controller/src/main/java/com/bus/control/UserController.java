package com.bus.control;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bus.IMenuService;
import com.bus.IUserService;
import com.bus.annotation.MRequest;
import com.bus.annotation.RequirePermission;
import com.bus.buzException.BuzEx;
import com.bus.constant.ResultCode;
import com.bus.result.BuzCode;
import com.bus.result.JsonResult;
import com.bus.userInfo.ShiroPrincipal;
import com.bus.utils.ObjectUtils;
import com.bus.utils.StringUtils;
import com.bus.utils.SubjectUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.bus.vo.Menu;
import com.bus.vo.User;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/10 10:12
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
@Controller
@RequestMapping({"/web-v/user/","/api/user"})
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping("/userList")
    @ResponseBody
    @RequirePermission(value = "user:list_1",isRequired = true)
    public Object userList(@RequestBody JSONObject object){
        try{
            return userService.getPageList(object);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    @RequestMapping("getUser")
    @ResponseBody
    @RequirePermission(value = "user:list_1",isRequired = false)
    public JsonResult getUserInfo(Integer id){
        try{
            return  JsonResult.OK(userService.getUser(id));
        }catch (BuzEx e){
            return  JsonResult.Fail(e.getBuzCode());
        }


    }

    private boolean isInclude(List<JSONObject> parentList,Menu m){
        for(JSONObject object:parentList){
            Menu menu = object.getObject("parent", Menu.class);
            if(menu.getId().equals(m.getId())){
                return true;
            }
        }
        return false;
    }
    private JSONArray isIncludeChild(List<JSONObject> parentList,Menu m){
        List<Menu> menus1 = new ArrayList<>();
        for(JSONObject object:parentList){
            JSONArray menus = object.getJSONArray("children");
            if(menus!=null&&menus.size()>0){
                for(Object menu:menus){
                    JSONObject jsonObject = (JSONObject)menu;
                    if(m.getParentId().equals(jsonObject.getInteger("parentId"))){
                        menus1.add(m);
                        break;
                    }
                }
                menus.addAll(menus1);
                object.put("children",menus);
                return menus;
            }
        }
        return null;
    }
    private List foreachlist(List<Menu> menus,Menu m,boolean flag){
        List<JSONObject> parentList = new ArrayList<>();
        int count = 0;
        boolean isParent = false;
        if(m.getIsParent()==1){
            isParent= true;
        }
        if(!ObjectUtils.isNull(menus)){
            for(Menu menu:menus){
                JSONObject object = new JSONObject();
                if(m.getId().equals(menu.getParentId())){
                    count++;
                    if(!isInclude(parentList,m)){
                        object.put("parent",m);
                    }
                    JSONArray array = isIncludeChild(parentList,menu);
                    if(array==null){
                        List list = new ArrayList<>();
                        list.add(menu);
                        object.put("children", list);
                        parentList.add(object);
                    }else{
                    }


                }
            }

        }
        if(isParent&&count==0){
            JSONObject object = new JSONObject();
            object.put("parent",m);
            parentList.add(object);
        }
        return parentList;

    }
    private String getDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(new Date());
    }
    /**
     * 登录入口
     * @param body
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public JsonResult login(String body){
        try{
            Subject subject = SecurityUtils.getSubject();
                JSONObject jsonObject = JSONObject.parseObject(body);
                String userName = jsonObject.getString("userName");
                UsernamePasswordToken info = new UsernamePasswordToken(userName, jsonObject.getString("password"));
                subject.login(info);
                ShiroPrincipal shiroPrincipal = ShiroPrincipal.get();
                List<Menu> menus = shiroPrincipal.getMenus();
                List<JSONObject> parentList = new ArrayList<>();
                if (!ObjectUtils.isNull(menus)) {
                    for (Menu menu : menus) {
                        parentList.addAll(foreachlist(menus, menu, true));
                    }

                }
                User user = shiroPrincipal.getUser();
                redisTemplate.opsForValue().set("user:info"+":"+subject.getSession().getId(),user,1L, TimeUnit.HOURS);

               // SubjectUtils.setUser(shiroPrincipal.getUser(), subject);
                return  JsonResult.OK(parentList,userName);
        }catch (AuthenticationException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            if(ResultCode.pwd_error.getCode().toString().equals(e.getMessage())){
                return  JsonResult.Fail(ResultCode.pwd_error);
            }else if(ResultCode.username_error.getCode().toString().equals(e.getMessage())){
                return  JsonResult.Fail(ResultCode.username_error);
            }
        }catch (Exception e){
            e.printStackTrace();
            return  JsonResult.Fail(BuzCode.Fail);
        }
        return JsonResult.Fail(BuzCode.Fail);
    }


    /**
     * 登录入口
     * @param body
     * @return
     */
    @RequestMapping("webLogin")
    @ResponseBody
    public JsonResult webLogin(String body, Model model){
        try{
            Subject subject = SecurityUtils.getSubject();
                JSONObject jsonObject = JSONObject.parseObject(body);
                String userName = jsonObject.getString("userName");
                UsernamePasswordToken info = new UsernamePasswordToken(userName, jsonObject.getString("password"));
                subject.login(info);
                ShiroPrincipal shiroPrincipal = ShiroPrincipal.get();
                List<Menu> menus = shiroPrincipal.getMenus();
                List<JSONObject> parentList = new ArrayList<>();
                if (!ObjectUtils.isNull(menus)) {
                    for (Menu menu : menus) {
                        parentList.addAll(foreachlist(menus, menu, true));
                    }

                }
            User user = shiroPrincipal.getUser();
            redisTemplate.opsForValue().set("user:info:"+subject.getSession().getId(),user,1L, TimeUnit.HOURS);
                //SubjectUtils.setUser(shiroPrincipal.getUser(), subject);

            return  JsonResult.OK();
        }catch (AuthenticationException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            if(ResultCode.pwd_error.getCode().toString().equals(e.getMessage())){
                model.addAttribute("msg",ResultCode.pwd_error.getMessage());
                return  JsonResult.Fail(ResultCode.pwd_error);
            }else if(ResultCode.username_error.getCode().toString().equals(e.getMessage())){
                return  JsonResult.Fail(ResultCode.username_error);
            }

        }catch (Exception e){
            e.printStackTrace();


        }
        return  JsonResult.Fail();
    }


    @RequestMapping("qqLogin")
    @ResponseBody
    @MRequest(isVlidate = false)
    public JsonResult qqLogin(String isType){
        try{
            if(!StringUtils.isEmpty(isType)&&"qq".equalsIgnoreCase(isType)){
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken info = new UsernamePasswordToken("wwz","19941115wwz");
                subject.login(info);
                return  JsonResult.OK();
            }

        }catch (AuthenticationException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
            return  JsonResult.Fail(BuzCode.Fail);
        }
        return JsonResult.Fail(BuzCode.Fail);
    }
    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("toLogin")
    public String  toLogin(){
        return "login";
    }

    /**
     * 跳转到首页
     * @return
     */
    @RequestMapping("index")
    public ModelAndView index(ModelAndView modelAndView){
        modelAndView.setViewName("index");
        return modelAndView;
    }


    /**
     * 登录入口
     * @return
     */
    @RequestMapping("logout")
    @ResponseBody
    @MRequest(isVlidate = false)
    public JsonResult logout(@Valid User user, Errors errors){
        try{
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            return  JsonResult.OK(BuzCode.OK);
        }catch (AuthenticationException e){
        }catch (Exception e){
            e.printStackTrace();
            return  JsonResult.Fail(BuzCode.Fail);
        }
        return JsonResult.Fail(BuzCode.Fail);
    }
    /**
     * 登录入口
     * @return
     */
    @RequestMapping("validLogin")
    @ResponseBody
    public JsonResult validLogin(){
        try{
            Subject subject = SecurityUtils.getSubject();
            Object o =subject.getPrincipal();
            Session session = subject.getSession(false);
            if(session == null || session.getId() == null){
                return null;
            }
            return  JsonResult.OK(BuzCode.OK);
        }catch (AuthenticationException e){
        }catch (Exception e){
            e.printStackTrace();
            return  JsonResult.Fail(BuzCode.Fail);
        }
        return JsonResult.Fail(BuzCode.Fail);
    }

    /**
     * 添加用户
     * @return
     */
    @RequestMapping("saveUser")
    @ResponseBody
    public JsonResult saveUser(@RequestBody User user){
        try{
            return  JsonResult.OK(userService.saveUser(user));
        }catch (AuthenticationException e){
        }catch (Exception e){
            e.printStackTrace();
            return  JsonResult.Fail(BuzCode.Fail);
        }
        return JsonResult.Fail(BuzCode.Fail);
    }

    /**
     * 用户列表
     * @return
     */
    @RequestMapping("/user.html")
    public String role(){
        return "user/user";
    }

    /**
     * 用户添加页面
     * @return
     */
    @RequestMapping("/user_add.html")
    public String role_add(){
        return "user/user_add";
    }


    @RequestMapping("queryUserInfo")
    @ResponseBody
    public JsonResult queryUserInfo(){
        try{
            return  JsonResult.OK(ShiroPrincipal.get().getUser());
        }catch (BuzEx e){
            return  JsonResult.Fail(e.getBuzCode());
        }


    }

}
