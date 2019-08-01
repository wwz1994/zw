package com.bus.dao;

import com.alibaba.fastjson.JSONObject;
import com.bus.vo.Product;
import com.bus.vo.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by wwz on 2019-06-26.
 */
@Repository
public interface ProductDao {

    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Map<String, Object>> queryProductSku(String businessType);


    List<Product> getPage(JSONObject jsonObject);

    Integer getCount(JSONObject jsonObject);


}