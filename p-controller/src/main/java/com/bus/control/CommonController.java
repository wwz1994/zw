package com.bus.control;

import com.bus.buzException.AuthEx;
import com.bus.buzException.BuzEx;
import com.bus.result.JsonResult;
import com.bus.utils.CookiesUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


/**
 * Created by wwz on 2018-12-01.
 */
@RequestMapping({"/api/common","/web-v"})
@Controller
public class CommonController {
    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping("/list")
    @ResponseBody
    public Object getList(){
        return null;
    }

    @RequestMapping(value = "/noRight")
    public String   noRight(Model model){
        return "noRight";
    }

    @RequestMapping(value = "/index",produces = "text/html;charset=utf-8")
    public String  index(Model model){
        model.addAttribute("name","暂无权限");
        return "index";
    }

    @RequestMapping(value = "/login",produces = "text/html;charset=utf-8")
    public String  login(Model model){
        return "login";
    }


}