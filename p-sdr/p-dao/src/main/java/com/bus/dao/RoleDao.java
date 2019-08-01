package com.bus.dao;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;
import com.bus.vo.Role;

import java.util.List;

@Repository
public interface RoleDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> getPage(JSONObject jsonObject);

    List<Role> getRoleAll();

    Integer getCount(JSONObject jsonObject);
}