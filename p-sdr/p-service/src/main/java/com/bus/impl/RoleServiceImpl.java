package com.bus.impl;

import com.alibaba.fastjson.JSONObject;
import com.bus.IMenuService;
import com.bus.IRoleService;
import com.bus.buzException.BuzEx;
import com.bus.dao.RoleDao;
import com.bus.result.BuzCode;
import com.bus.result.PageInfo;
import com.bus.userInfo.ShiroPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.bus.vo.Menu;
import com.bus.vo.Role;
import com.bus.vo.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wwz on 2018-11-25.
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleDao roleDao;
  /*  @Autowired
    private RedisCacheManager redisCacheManager;*/
    @Autowired
    private IMenuService menuService;
    @Override
    public PageInfo<Role> getPageList(JSONObject jsonObject) {
        Integer count = roleDao.getCount(jsonObject);
        if(count == 0){
            return new PageInfo();
        }
        Integer pageSize = jsonObject.getInteger("pageSize")==null?10:jsonObject.getInteger("pageSize");
        Integer pageNum = jsonObject.getInteger("pageNumber")==null?0:jsonObject.getInteger("pageNumber");
        jsonObject.put("pageNum",(pageNum-1)*pageSize);
        jsonObject.put("pageSize",pageSize);
        PageInfo pageInfo = new PageInfo(pageNum,pageSize,count,roleDao.getPage(jsonObject));
        return pageInfo;
    }

    @Override
    public Integer saveRole(Role role) {
        int count = 0;
        if(role.getId()!=null){
            count = roleDao.updateByPrimaryKeySelective(role);
        }else{
            count = roleDao.insertSelective(role);
        }
        ShiroPrincipal shiroPrincipal =  ShiroPrincipal.get();
        if(shiroPrincipal!=null){
           User user =  shiroPrincipal.getUser();
           // redisCacheManager.lSet("_permission:"+user.getUserName(),getPermissions(user.getId()));
            //RedisUtils.setList("_permission:"+user.getUserName(),getPermissions(user.getId()));
        }

        if(count<=0){
            throw new BuzEx(BuzCode.Fail);
        }
        return count;
    }


    private List<String> getPermissions(Integer id){
        List<String> permissions = new ArrayList<>();
        List<Menu> menus =  menuService.queryMenuByUserId(id);
        for(Menu menu:menus){
            permissions.add(menu.getMenuCode());
        }
        return permissions;
    }

    @Override
    public Integer deleteRole(Integer id) {
        return roleDao.deleteByPrimaryKey(id);
    }

    @Override
    public Role getRole(Integer id) {
        return roleDao.selectByPrimaryKey(id);
    }

    @Override
    @Cacheable(value="role:list",key="caches[0].name")
    public List<Role> getRoleAll() {
        return roleDao.getRoleAll();
    }
}