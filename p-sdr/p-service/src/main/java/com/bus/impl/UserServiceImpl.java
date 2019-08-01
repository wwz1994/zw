package com.bus.impl;

import com.alibaba.fastjson.JSONObject;
import com.bus.IUserService;
import com.bus.buzException.BuzEx;
import com.bus.dao.UserRoleDao;
import com.bus.result.BuzCode;
import com.bus.result.PageInfo;
import com.bus.utils.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.bus.dao.UserDao;
import com.bus.vo.User;
import com.bus.vo.UserRole;

import javax.servlet.http.HttpServletRequest;

/**
 * @Copyright (c) by adinnet information technology Co., Ltd.
 * @All right reserved.
 * @Create Date: 2018/4/10 10:53
 * @Create Author: wangweizhen
 * @File Name: project
 * @Last version: 1.0
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Override
    public Map<String, Object> getUser(Integer id) {
        return  userDao.getUser(id);
    }

    @Autowired
    private HttpServletRequest request;
    @Override
    public int login(String body) {
        JSONObject object = JSONObject.parseObject(body);
        if(object == null){
            throw new BuzEx(BuzCode.param_is_null);
        }
        String userName = object.getString("userName");
        if(userName!=null&&!"".equals(userName)) {
            request.getSession().setAttribute("token","123");
        }
        return 0;
    }

    @Override
    public User
    loginByUser(String userName) {
        return userDao.loginByUser(userName);
    }

    @Override
    public User selectByOpenId(String openId) {
        return this.userDao.selectByOpenId(openId);
    }

    private String getSalt(){
        return UUID.randomUUID().toString().substring(4,10);
    }
    private final String split = "_";
    private final String charset = "utf-8";
    @Override
    public Integer saveUser(User user) {
        UserRole userRole = new UserRole();
        Integer userId = user.getId();
        Integer roleId = user.getRoleId();
        if(userId!=null){
            UserRole userRole1 = userRoleDao.selectByUserId(userId);
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            if (userRole1 == null){
                userRoleDao.insertSelective(userRole);
            }else{
                userRole.setId(userRole1.getId());
                userRoleDao.updateByPrimaryKeySelective(userRole);
            }
            user.setPassword(null);

            return userDao.updateByPrimaryKeySelective(user);
        }
        //加密规则：md5(用户名+盐+密码)
        String salt = getSalt();
        user.setPassword(MD5Utils.MD5Encode(user.getUserName()+split+salt+split+user.getPassword(),charset));
        user.setAddTime(new Date());
        user.setSalt(salt);
        user.setUpdateTime(new Date());
        Integer result = userDao.insertSelective(user);
        if(result<=0){
            throw new BuzEx(BuzCode.Fail);
        }
        userRole.setUserId(user.getId());
        userRole.setRoleId(roleId);
        if(userRoleDao.insertSelective(userRole)<=0){
            throw new BuzEx(BuzCode.Fail);
        }
        return result;
    }

    @Override
    public PageInfo<User> getPageList(JSONObject jsonObject) {
        Integer count = userDao.getCount(jsonObject);
        if(count == 0){
            return new PageInfo();
        }
        Integer pageSize = jsonObject.getInteger("pageSize")==null?10:jsonObject.getInteger("pageSize");
        Integer pageNum = jsonObject.getInteger("pageNumber")==null?0:jsonObject.getInteger("pageNumber");
        jsonObject.put("pageNum",(pageNum-1)*pageSize);
        jsonObject.put("pageSize",pageSize);
        PageInfo pageInfo = new PageInfo(pageNum,pageSize,count,userDao.getPage(jsonObject));
        return pageInfo;
    }

    @Override
    public Integer insertUser(User user) {
        if(this.userDao.insertSelective(user)>0){
            UserRole userRole = new UserRole();
            userRole.setRoleId(user.getRoleId());
            userRole.setUserId(user.getId());
            if(this.userRoleDao.insertSelective(userRole)<=0){
                throw new BuzEx("登录失败");
            }
        }else{
            throw new BuzEx("登录失败");
        }
        return 1;
    }

    @Override
    public Integer updateUser(User user) {
        return this.userDao.updateByPrimaryKeySelective(user);
    }
}
