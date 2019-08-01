package com.bus.impl;

import com.alibaba.fastjson.JSONObject;
import com.bus.IShowModelService;
import com.bus.dao.ShowModelMapper;
import com.bus.result.PageInfo;
import com.bus.vo.Role;
import com.bus.vo.ShowModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wwz
 * @date 2019-07-15
 * @descrption:
 */
@Service
public class ShowModelServiceImpl implements IShowModelService {
    @Autowired
    private ShowModelMapper showModelMapper;

    @Transactional(rollbackFor = Throwable.class,isolation = Isolation.REPEATABLE_READ)
    @Override
    public Integer update(ShowModel showModel) {
        return showModelMapper.updateByPrimaryKeySelective(showModel);
    }

    @Override
    public ShowModel queryModel() {
        return showModelMapper.queryModel();
    }


    @Override
    public PageInfo<Role> getPageList(JSONObject jsonObject) {
        Integer count = showModelMapper.getCount(jsonObject);
        if(count == 0){
            return new PageInfo();
        }
        Integer pageSize = jsonObject.getInteger("pageSize")==null?10:jsonObject.getInteger("pageSize");
        Integer pageNum = jsonObject.getInteger("pageNumber")==null?0:jsonObject.getInteger("pageNumber");
        jsonObject.put("pageNum",(pageNum-1)*pageSize);
        jsonObject.put("pageSize",pageSize);
        PageInfo pageInfo = new PageInfo(pageNum,pageSize,count,showModelMapper.getPage(jsonObject));
        return pageInfo;
    }
}