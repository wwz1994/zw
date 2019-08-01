package com.bus;

import com.alibaba.fastjson.JSONObject;
import com.bus.result.PageInfo;
import com.bus.vo.OrderEntity;
import com.bus.vo.Product;
import com.bus.vo.ProductSku;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wwz on 2019-06-28.
 */
public interface IOrderEntityService {
    Logger logger = LoggerFactory.getLogger(IOrderEntityService.class);
    List<OrderEntity> queryOrderList(Integer userId);
    PageInfo<OrderEntity> getPageList(JSONObject jsonObject);

    OrderEntity selectById(Integer id);

    @Transactional(rollbackFor = Throwable.class,isolation = Isolation.REPEATABLE_READ)
    Integer saveOrder(OrderEntity orderEntity, Product product, ProductSku productSku);

    @Transactional(rollbackFor = Throwable.class,isolation = Isolation.REPEATABLE_READ)
    Integer updateStatus(Integer id,Integer status);


}