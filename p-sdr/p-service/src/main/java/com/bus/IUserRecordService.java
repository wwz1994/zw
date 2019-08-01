package com.bus;

import com.alibaba.fastjson.JSONObject;
import com.bus.result.PageInfo;
import org.springframework.transaction.annotation.Transactional;
import com.bus.vo.UserRecord;

import java.util.List;

/**
 * @author wwz(1512180909@qq.com)<br>
 * @date 2019-01-29
 * 
 * @version 3.0
 */
public interface IUserRecordService {


     /**
     *
     * 插入数据
     * @param userRecord
     * @return 影响数
     **/
    @Transactional
    public Integer save(UserRecord userRecord);
    
    /**
     * 删除通过主键
     * @param id 主键
     * @return 影响数
     **/
    @Transactional
    public int deleteByPrimaryKey(Integer id);
    
    /**
     * 更新不为空的字段通过主键
     * @param userRecord
     * @return 影响数
     **/
    @Transactional
    public int updateByPrimaryKey(UserRecord userRecord);
    
    /**
     * 查询通过主键
     * @param id 主键
     * @return 影响数
     **/
    public UserRecord selectByPrimaryKey(Integer id);
    
   /**
     *
     * 查找所有
     * @author 66
     * @return
     **/
    public List<UserRecord> queryUserRecordAll(); 
    
    /**
	 * 查询通过分页
	 * @param helper
	 * @return
	 */
	 public PageInfo getPageList(JSONObject object);
    
	/**
     * 根据索引查询
     * @author 66
     * @param  id 主键
     * @return 集合
     **/
    public List<UserRecord> queryUserRecordById(Integer id);
}