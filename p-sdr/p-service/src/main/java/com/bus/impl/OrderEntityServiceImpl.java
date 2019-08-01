package com.bus.impl;

import com.alibaba.fastjson.JSONObject;
import com.bus.IOrderEntityService;
import com.bus.buzException.BuzEx;
import com.bus.dao.OrderEntityMapper;
import com.bus.dao.ProductSkuMapper;
import com.bus.result.PageInfo;
import com.bus.userInfo.ShiroPrincipal;
import com.bus.vo.OrderEntity;
import com.bus.vo.Product;
import com.bus.vo.ProductSku;
import com.bus.vo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by wwz on 2019-06-28.
 */
@Service
public class OrderEntityServiceImpl implements IOrderEntityService {
    @Autowired
    private OrderEntityMapper orderEntityMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Override
    public List<OrderEntity> queryOrderList(Integer userId) {
        return this.orderEntityMapper.queryOrderList(userId);
    }

    @Override
    public PageInfo<OrderEntity> getPageList(JSONObject jsonObject) {
        Integer count = this.orderEntityMapper.getCount(jsonObject);
        if(count == 0){
            return new PageInfo();
        }
        Integer pageSize = jsonObject.getInteger("pageSize")==null?10:jsonObject.getInteger("pageSize");
        Integer pageNum = jsonObject.getInteger("pageNumber")==null?0:jsonObject.getInteger("pageNumber");
        jsonObject.put("pageNum",(pageNum-1)*pageSize);
        jsonObject.put("pageSize",pageSize);
        PageInfo pageInfo = new PageInfo(pageNum,pageSize,count,orderEntityMapper.getPage(jsonObject));
        return pageInfo;
    }

    @Override
    public OrderEntity selectById(Integer id) {
        return this.orderEntityMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer saveOrder(OrderEntity orderEntity, Product product,ProductSku productSku) {
        //待支付
        orderEntity.setOrderStatus(0);
        Integer num = (orderEntity.getNum()==null?1:orderEntity.getNum());
        BigDecimal price = new BigDecimal(productSku.getPrice()).setScale(2,BigDecimal.ROUND_HALF_UP);
        BigDecimal total = price.multiply(new BigDecimal(Double.parseDouble(num.toString()))).setScale(2,BigDecimal.ROUND_HALF_UP);
        orderEntity.setTotalAmount(total.doubleValue());
        orderEntity.setProductName(product.getProductName());
        orderEntity.setOrderTime(new Date());
        orderEntity.setSkuName(productSku.getSkuName());
        orderEntity.setSkuId(productSku.getId());
        orderEntity.setPrice(productSku.getPrice());
        orderEntity.setUserId(ShiroPrincipal.get().getUser().getId());
        orderEntity.setOrderTime(new Date());
        if(this.orderEntityMapper.insertSelective(orderEntity)<=0){
            throw new BuzEx("下单失败");
        }
        return 1;
    }

    @Override
    public Integer updateStatus(Integer id,Integer status) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(id);
        orderEntity.setOrderStatus(status);
        if(this.orderEntityMapper.updateByPrimaryKeySelective(orderEntity)<=0){
            logger.error("---------更新完工状态失败-----------");
            throw new BuzEx("更新完工状态失败");
        }
        return 1;
    }
}