package com.bus.dao;


import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;
import com.bus.vo.Menu;

import java.util.List;
@Repository
public interface MenuDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu>  queryMenu(Integer userId);

    List<Menu>  queryMenuByUserId(Integer userId);

    List<Menu>  queryMenuList();


    List<Menu>  queryMenuListByParentId(Integer id);

    Integer getCount(JSONObject jsonObject);

    List<Menu> getPage(JSONObject jsonObject);
}