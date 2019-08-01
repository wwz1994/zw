package com.bus;

import com.alibaba.fastjson.JSONObject;
import com.bus.result.PageInfo;
import com.bus.vo.Product;

import java.util.List;
import java.util.Map;

/**
 * Created by wwz on 2019-06-26.
 */
public interface IProductService {
     List<Map<String, Object>> queryProductSku(String businessType);

     PageInfo getPageList(JSONObject jsonObject);

     Integer deleteProduct (Integer id)throws Exception;


     List<Map<String,Object>> queryProduct (String type);

     Integer saveProduct(JSONObject jsonObject)throws Exception;

     Product selectById(Integer id);
}