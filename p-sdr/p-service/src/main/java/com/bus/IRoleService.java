package com.bus;

import com.alibaba.fastjson.JSONObject;
import com.bus.result.PageInfo;
import org.springframework.transaction.annotation.Transactional;
import com.bus.vo.Role;

import java.util.List;

/**
 * Created by wwz on 2018-11-25.
 */
public interface IRoleService {

    public PageInfo getPageList(JSONObject jsonObject);

    @Transactional(rollbackFor = Throwable.class)
    public Integer saveRole(Role role);


    public Integer deleteRole(Integer id);


    public Role getRole(Integer id);


    public List<Role> getRoleAll();
}
