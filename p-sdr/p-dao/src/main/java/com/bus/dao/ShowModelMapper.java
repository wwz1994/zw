package com.bus.dao;

import com.alibaba.fastjson.JSONObject;
import com.bus.vo.Role;
import com.bus.vo.ShowModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShowModel record);

    int insertSelective(ShowModel record);

    ShowModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShowModel record);

    int updateByPrimaryKey(ShowModel record);

    ShowModel queryModel();

    Integer getCount(JSONObject jsonObject);
    List<Role> getPage(JSONObject jsonObject);

}