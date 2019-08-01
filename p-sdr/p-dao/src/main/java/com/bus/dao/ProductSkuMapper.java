package com.bus.dao;

import com.bus.vo.ProductSku;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductSkuMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByProductId(Integer productId);

    int insert(ProductSku record);

    int insertSelective(ProductSku record);

    ProductSku selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductSku record);

    int updateByPrimaryKey(ProductSku record);

    List<Object> queryProductSku(Integer productId);
}