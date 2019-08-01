package com.bus.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bus.IProductService;
import com.bus.dao.ProductDao;
import com.bus.dao.ProductSkuMapper;
import com.bus.result.PageInfo;
import com.bus.userInfo.ShiroPrincipal;
import com.bus.utils.ObjectUtils;
import com.bus.vo.Product;
import com.bus.vo.ProductSku;
import com.bus.vo.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wwz on 2019-06-26.
 */
@Service
public class ProductServiceImpl implements IProductService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Override
    public List<Map<String, Object>> queryProductSku(String businessType) {
        return productDao.queryProductSku(businessType);
    }

    @Override
    public PageInfo<Role> getPageList(JSONObject jsonObject) {
        Integer count = this.productDao.getCount(jsonObject);
        if(count == 0){
            return new PageInfo();
        }
        Integer pageSize = jsonObject.getInteger("pageSize")==null?10:jsonObject.getInteger("pageSize");
        Integer pageNum = jsonObject.getInteger("pageNumber")==null?0:jsonObject.getInteger("pageNumber");
        jsonObject.put("pageNum",(pageNum-1)*pageSize);
        jsonObject.put("pageSize",pageSize);
        PageInfo pageInfo = new PageInfo(pageNum,pageSize,count,productDao.getPage(jsonObject));
        return pageInfo;
    }

    /**
     * 删除商品以及商品sku
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Integer deleteProduct(Integer id)throws Exception {
        int result;
        if((result = productDao.deleteByPrimaryKey(id))<=0){
            logger.error("删除商品失败");
            throw new Exception("删除失败");
        }
        if((result += productSkuMapper.deleteByProductId(id))<=0){
            logger.error("删除商品sku失败");
            throw new Exception("删除失败");
        }
        return result;
    }

    @Override
    public List<Map<String,Object>> queryProduct(String type) {

        return this.productDao.queryProductSku(type);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Integer saveProduct(JSONObject jsonObject)throws Exception {
        Integer productId = jsonObject.getInteger("productId");
        Product product = productDao.selectByPrimaryKey(productId);
        if(product == null){
            throw new Exception("商品不存在");
        }
        JSONArray skuName = jsonObject.getJSONArray("skuName");
        JSONArray price = jsonObject.getJSONArray("price");
        JSONArray skuId = jsonObject.getJSONArray("skuId");
        if(ObjectUtils.isNull(skuName)){
            throw new Exception("商品参数为空");
        }
        String productName = jsonObject.getString("productName");
        if(!product.getProductName().equals(productName)){
            Product productVo = new Product();
            productVo.setId(productId);
            productVo.setProductName(productName);
            if(productDao.updateByPrimaryKey(productVo)<=0){
                throw new Exception("商品更新失败");
            }
        }
        if(productSkuMapper.deleteByProductId(productId)<=0){
            throw new Exception("商品sku更新失败");
        }
            for(int i = 0;i<skuName.size();i++){
               ProductSku productSku = new ProductSku();
                productSku.setCreateUser(ShiroPrincipal.get().getUser().getUserName());
                productSku.setPrice(Double.valueOf(price.get(i)+""));
                productSku.setProductId(productId);
                productSku.setSkuName(skuName.get(i)+"");
                if(productSkuMapper.insert(productSku)<=0){
                    throw new Exception("商品sku更新失败");
                }

        }

        return 1;
    }

    @Override
    public Product selectById(Integer id) {
        return this.productDao.selectByPrimaryKey(id);
    }
}