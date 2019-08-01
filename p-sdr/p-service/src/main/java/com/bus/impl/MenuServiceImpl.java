package com.bus.impl;

import com.alibaba.fastjson.JSONObject;
import com.bus.IMenuService;
import com.bus.dao.MenuDao;
import com.bus.result.PageInfo;
import com.bus.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bus.vo.Menu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wwz on 2018-11-22.
 */
@Service
public class MenuServiceImpl implements IMenuService {
    @Autowired
    private MenuDao menuDao;
    @Override
    public List<Menu> queryMenu(Integer userId) {
        return menuDao.queryMenu(userId);
    }

    @Override
    public List<Menu> queryMenuByUserId(Integer userId) {
        return menuDao.queryMenuByUserId(userId);
    }

    @Override
    public List<JSONObject> queryMenuList() {
        List<Menu> menus = menuDao.queryMenuList();
        List<JSONObject> list = new ArrayList<>();
        if(!ObjectUtils.isNull(menus)){
            for(Menu menu:menus){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",menu.getId());
                jsonObject.put("pId",menu.getParentId());
                jsonObject.put("name",menu.getMeunName());
                if(menu.getIsParent()==1){
                    jsonObject.put("open",true);
                }
                list.add(jsonObject);
            }
        }
        return list;
    }

    @Override
    public List<Menu> queryMenuList(Integer id) {
        return menuDao.queryMenuListByParentId(id);
    }

    @Override
    public List<Menu> queryMenuAll() {
        return menuDao.queryMenuList();
    }

    @Override
    public PageInfo<Menu> getPageList(JSONObject jsonObject) {
        Integer count = menuDao.getCount(jsonObject);
        if(count == 0){
            return new PageInfo();
        }
        Integer pageSize = jsonObject.getInteger("pageSize")==null?10:jsonObject.getInteger("pageSize");
        Integer pageNum = jsonObject.getInteger("pageNumber")==null?0:jsonObject.getInteger("pageNumber");
        jsonObject.put("pageNum",(pageNum-1)*pageSize);
        jsonObject.put("pageSize",pageSize);
        PageInfo pageInfo = new PageInfo(pageNum,pageSize,count,menuDao.getPage(jsonObject));
        return pageInfo;
    }

    @Override
    public Integer saveMenu(Menu menu) {
        Date date = new Date();
        menu.setAddTime(date);
        menu.setUpdateTime(date);
        Integer result = menuDao.insertSelective(menu);
        if(result>0){

        }
        return result;
    }

    @Override
    public Integer deleteMenu(Integer id) {
        return menuDao.deleteByPrimaryKey(id);
    }
}