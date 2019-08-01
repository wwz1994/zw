package com.bus.control;

import com.alibaba.fastjson.JSONObject;
import com.bus.IProductService;
import com.bus.buzException.BuzEx;
import com.bus.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by wwz on 2019-06-26.
 */
@Controller
@RequestMapping({"/web-v/product"})
public class ProductController {

    @Autowired
    private IProductService productService;

    /**
     * 获取商品属性
     * @param type
     * @return
     */
    @RequestMapping("/goods/{type}")
    @ResponseBody
    public JsonResult getGoods(@PathVariable("type")String type){
        try{
            return JsonResult.OK(productService.queryProductSku(type));
        }catch (BuzEx e){

        }
        return JsonResult.Fail();
    }

    @RequestMapping(value = "/productList",produces = "text/html;charset=utf-8")
    public String  productList(Model model){
        return "product/product";
    }

    @RequestMapping(value = "/update_add.html",produces = "text/html;charset=utf-8")
    public String  update_add(Model model){
        return "product/update_add";
    }


    /**
     * 商品列表
     * @param object
     * @return
     */
    @RequestMapping("/productList")
    @ResponseBody
    public Object productList(@RequestBody JSONObject object){
        try{
            return productService.getPageList(object);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 删除商品
     * @param id
     * @return
     */
    @RequestMapping("/deleteProduct")
    @ResponseBody
    public JsonResult deleteProduct(@RequestParam Integer id){
        try{
            return JsonResult.OK(productService.deleteProduct(id));
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonResult.Fail();

    }


    @RequestMapping("/queryProduct")
    @ResponseBody
    public JsonResult queryProduct(@RequestParam String type){
        try{
            return JsonResult.OK(productService.queryProduct(type));
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonResult.Fail();

    }


    @RequestMapping("/saveProduct")
    @ResponseBody
    public JsonResult queryProduct(@RequestBody JSONObject object){
        try{
            return JsonResult.OK(this.productService.saveProduct(object));
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonResult.Fail();

    }

}