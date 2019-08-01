package com.bus.control;

import com.bus.IProductService;
import com.bus.IShowModelService;
import com.bus.socket.WebSocket;
import com.bus.userInfo.ShiroPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by wwz on 2019-06-25.
 */
@Controller
@RequestMapping("/api/")
public class MainController {
    @Autowired
    private IProductService productService;
    @Autowired
    private IShowModelService showModelService;
    @Autowired
    private WebSocket webSocket;
    @Value("${rjhost}")
    String rjhost;
    //加上该注解表明该方法会在bean初始化后调用
    @PostConstruct
    private void init(){
        //这里便可以获取到environment
        System.out.println(rjhost);
         rjhost=rjhost;
    }

    /**
     * 首页
     * @return
     */
    @RequestMapping({"/index","/web-v/index"})
    public String index(HttpServletRequest request,Model model){
        if(request.getRequestURI().contains("web-v/index")){
            return "index";
        }
        model.addAttribute("model",showModelService.queryModel());
        return "web/index";
    }

    /**
     * 登录页面
     * @return
     */
    @RequestMapping({"/login","/web-v/login"})
    public String login(HttpServletRequest request){
        if(request.getRequestURI().contains("web-v/login")){
            return "login";
        }
        return "web/login";
    }


    /**
     * type=1
     * @return
     */
    @RequestMapping("/full_set_task/{type}")
    public String full_set_task(Model model, @PathVariable("type")String type){
        model.addAttribute("productList",productService.queryProductSku(type));
        return "full_set_task";
    }

    /**
     * 村淘
     * @param model
     * @param type=1
     * @return
     */
    @RequestMapping("/cunTao/{type}")
    public String cunTao(Model model, @PathVariable("type")String type){
        model.addAttribute("productList",productService.queryProductSku(type));
        return "cuntao_task";
    }

    /**
     * 有效订单金额
     * @param model
     * @param type=3
     * @return
     */
    @RequestMapping("/youxiao/{type}")
    public String youxiao(Model model, @PathVariable("type")String type){
        model.addAttribute("productList",productService.queryProductSku(type));
        return "youxiao_task";
    }

    /**
     * 引流页面
     * @param model
     * @param type=4
     * @return
     */
    @RequestMapping("/yinliu/{type}")
    public String com(Model model, @PathVariable("type")String type){
        model.addAttribute("productList",productService.queryProductSku(type));
        return "yinliu_task";
    }

    /**
     * 成交页面
     * @param model
     * @param type=5
     * @return
     */
    @RequestMapping("/chengjiao/{type}")
    public String chengjiao(Model model, @PathVariable("type")String type){
        model.addAttribute("productList",productService.queryProductSku(type));
        return "chengjiao_task";
    }

    @RequestMapping("/kaidian")
    public String kaidian(Model model){
        String salt = ShiroPrincipal.get().getUser().getSalt();
        model.addAttribute("rjhost",rjhost+"/"+salt);
        return "kaidian";
    }


}