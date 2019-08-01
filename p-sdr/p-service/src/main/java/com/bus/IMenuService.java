package com.bus;

import com.alibaba.fastjson.JSONObject;
import com.bus.result.PageInfo;
import org.springframework.transaction.annotation.Transactional;
import com.bus.vo.Menu;

import java.util.List;

/**
 * Created by wwz on 2018-11-22.
 */
public interface IMenuService {
    public List<Menu> queryMenu(Integer userId);

    public List<Menu> queryMenuByUserId(Integer userId);

    public List<JSONObject> queryMenuList();


    public List<Menu> queryMenuList(Integer id);

    public List<Menu> queryMenuAll();



    public PageInfo<Menu> getPageList(JSONObject jsonObject);


    @Transactional
    public Integer saveMenu(Menu menu);

    @Transactional
    public Integer deleteMenu(Integer id);
}
