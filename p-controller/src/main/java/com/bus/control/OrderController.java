package com.bus.control;

import com.alibaba.fastjson.JSONObject;
import com.bus.IOrderEntityService;
import com.bus.IProductService;
import com.bus.buzException.BuzEx;
import com.bus.context.springContext;
import com.bus.dao.ProductSkuMapper;
import com.bus.result.JsonResult;
import com.bus.result.PageInfo;
import com.bus.socket.WebSocket;
import com.bus.userInfo.ShiroPrincipal;
import com.bus.utils.MailUtil;
import com.bus.utils.StringUtils;
import com.bus.vo.OrderEntity;
import com.bus.vo.Product;
import com.bus.vo.ProductSku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * Created by wwz on 2019-06-28.
 */
@Controller
@RequestMapping({"/web-v/order","/api/order"})
public class OrderController {

    @Autowired
    private IOrderEntityService orderEntityService;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private IProductService productService;
    private WebSocket webSocket = (WebSocket)springContext.getBeanByClass(WebSocket.class) ;
    /**
     * 下单
     * @param orderEntity
     * @param errors
     * @return
     */
    @RequestMapping("/saveOrder")
    @ResponseBody
    public JsonResult saveOrder(@RequestBody @Valid OrderEntity orderEntity, BindingResult errors){
        if(errors.hasErrors()){
            return JsonResult.Fail(errors.getFieldError().getDefaultMessage());
        }
        if(orderEntity.getProductId() == null || orderEntity.getSkuId()==null
                ||StringUtils.isEmpty(orderEntity.getProductUrl())){
            return JsonResult.Fail("参数为空");

        }
        ProductSku productSku = productSkuMapper.selectByPrimaryKey( orderEntity.getSkuId());
        if(productSku == null){
            return JsonResult.Fail("不存在的商品属性，请刷新重试");
        }
        Product product = productService.selectById( orderEntity.getProductId());
        if(product == null){
            return JsonResult.Fail("不存在的商品，请刷新重试");
        }
        try{
            orderEntityService.saveOrder(orderEntity,product,productSku);
            webSocket.onMessage(ShiroPrincipal.get().getUser().getRealName()+"下单成功！");
            return JsonResult.OK();

        }catch (Exception e){
            e.printStackTrace();
            return JsonResult.Fail("下单失败");

        }
    }

    /**
     * 查询订列表
     * @param object
     * @return
     */
    @RequestMapping("/orderList")
    @ResponseBody
    public Object orderList(@RequestBody JSONObject object){
        try{
            return orderEntityService.getPageList(object);

        }catch (Exception e){

        }
        return new PageInfo<>();
    }

    /**
     * 更新订单状态
     * @param orderId
     * @return
     */
    @RequestMapping("/updateOrder")
    @ResponseBody
    public JsonResult updateOrder(@RequestParam()Integer orderId){
        try{
            OrderEntity object = orderEntityService.selectById(orderId);
            if(object == null){
                return JsonResult.Fail("数据不存在");
            }
            return JsonResult.OK(orderEntityService.updateStatus(orderId,2));

        }catch (Exception e){

        }
        return JsonResult.Fail();
    }

    @RequestMapping("/toOrderList")
    public String toOrderList(){
        return "order/order";
    }

    /**
     * 邮箱发送
     * @param orderId
     * @return
     */
    @RequestMapping("/sendEmail")
    @ResponseBody
    public JsonResult sendEmail(@RequestParam()Integer orderId){
        try{
            OrderEntity object = orderEntityService.selectById(orderId);
            if(object == null){
                return JsonResult.Fail("数据不存在");
            }
            if(MailUtil.send(object.getEmail(),"你有一条已完成的消息！","恭喜你，下单的任务，已完工，请登录查看。此邮箱切勿回复")){
                return JsonResult.OK(orderEntityService.updateStatus(orderId,3));

            };

        }catch (Exception e){

        }
        return JsonResult.Fail();
    }
}