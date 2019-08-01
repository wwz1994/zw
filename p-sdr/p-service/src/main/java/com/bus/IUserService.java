package com.bus;

import com.alibaba.fastjson.JSONObject;
import com.bus.result.PageInfo;
import org.springframework.transaction.annotation.Transactional;
import com.bus.vo.User;

import java.util.Map;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/10 10:52
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
public interface IUserService {
    public Map<String,Object> getUser(Integer id);

    public int login(String body);

    public User loginByUser(String userName);
    public User selectByOpenId(String openId);

    @Transactional
    public Integer saveUser(User user);

    public PageInfo<User> getPageList(JSONObject jsonObject);
    @Transactional
    Integer insertUser(User user);
    @Transactional
    Integer updateUser(User user);
}
