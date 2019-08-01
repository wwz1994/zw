package com.bus.dao;

import org.springframework.stereotype.Repository;
import com.bus.vo.UserRole;

@Repository
public interface UserRoleDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    UserRole selectByPrimaryKey(Integer id);


    UserRole selectByUserId(Integer userId);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);
}