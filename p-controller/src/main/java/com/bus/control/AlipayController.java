package com.bus.control;


import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付宝
 * Created by ZhangShuzheng on 2017/04/04.
 */
@Controller
@RequestMapping("/api/alipay")
public class AlipayController  {
    Logger log = LoggerFactory.getLogger(AlipayController.class);

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private AlipayTradeWapPayRequest alipayTradeWapPayRequest;

    @Autowired
    private AlipayTradePagePayRequest alipayTradePagePayRequest;

    // 电脑网站支付
    @RequestMapping("/pay/pc")
    @ResponseBody
    public Object pc() throws Exception {
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", "pay2017" + System.currentTimeMillis());
        bizContent.put("total_amount", "0.01");
        bizContent.put("subject", "Iphone6 16G");
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        alipayTradePagePayRequest.setBizContent(bizContent.toString());
        return alipayClient.pageExecute(alipayTradePagePayRequest).getBody();
    }

    // 手机网站支付
    @RequestMapping("/pay/wap")
    @ResponseBody
    public Object wap() throws Exception {
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", "pay2017" + System.currentTimeMillis());
        bizContent.put("total_amount", "0.01");
        bizContent.put("subject", "Iphone6 16G");
        bizContent.put("product_code", "QUICK_WAP_PAY");
        alipayTradeWapPayRequest.setBizContent(bizContent.toString());
        return alipayClient.pageExecute(alipayTradeWapPayRequest).getBody();
    }

    // 异步通知
    @RequestMapping("/notifyUrl")
    @ResponseBody
    public Object notifyUrl(HttpServletRequest request) throws Exception {
        log.info("支付成功, 进入同步通知接口...");

        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, "","",""); //调用SDK验证签名

       // ModelAndView mv = new ModelAndView("alipaySuccess");
        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            // 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水
           // orderService.updateOrderStatus(out_trade_no, trade_no, total_amount);


         /*   Orders order = orderService.getOrderById(out_trade_no);
            Product product = productService.getProductById(order.getProductId());*/

            log.info("********************** 支付成功(支付宝同步通知) **********************");
            log.info("* 订单号: {}", out_trade_no);
            log.info("* 支付宝交易号: {}", trade_no);
            log.info("* 实付金额: {}", total_amount);
            //log.info("* 购买产品: {}", product.getName());
            log.info("***************************************************************");


           /* mv.addObject("out_trade_no", out_trade_no);
            mv.addObject("trade_no", trade_no);
            mv.addObject("total_amount", total_amount);
            mv.addObject("productName", product.getName());*/

        }else {
            log.info("支付, 验签失败...");
        }


      return null;
    }

}