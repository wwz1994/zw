package com.bus.dao;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;
import com.bus.vo.User;

import java.util.List;
import java.util.Map;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/10 10:58
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
@Repository
public interface UserDao {

    public Map<String,Object> getUser(Integer id);

    public User loginByUser(String userName);
    User selectByOpenId(String openId);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    Integer getCount(JSONObject record);
    List<User> getPage(JSONObject record);
}
