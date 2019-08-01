package com.bus.dao;

import com.alibaba.fastjson.JSONObject;
import com.bus.vo.OrderEntity;
import com.bus.vo.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderEntityMapper {
    int deleteByPrimaryKey(Integer orderId);

    int insert(OrderEntity record);

    int insertSelective(OrderEntity record);

    OrderEntity selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(OrderEntity record);

    int updateByPrimaryKey(OrderEntity record);

    List<OrderEntity> queryOrderList(Integer userId);

    Integer getCount(JSONObject jsonObject);
    List<OrderEntity> getPage(JSONObject jsonObject);
}