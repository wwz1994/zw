package com.bus;

import com.alibaba.fastjson.JSONObject;
import com.bus.result.PageInfo;
import com.bus.vo.ShowModel;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wwz
 * @date 2019-07-15
 * @descrption:
 */
public interface IShowModelService {
    @Transactional(rollbackFor = Throwable.class)

    Integer update(ShowModel showModel);

    ShowModel queryModel();

    PageInfo getPageList(JSONObject jsonObject);
}
