package com.bus.control;


import com.bus.buzException.BuzEx;
import com.bus.result.BuzCode;
import com.bus.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;


import com.bus.vo.UserRecord;
import com.bus.IUserRecordService;

/**
 * 用户统计
 * @author wwz
 * @date 2019-01-29
 * @version 3.0
 */
@RequestMapping("api/userRecord")
public class UserRecordController {
    
    @Autowired
	private IUserRecordService userRecordService;

	
	 /**
     * 分页列表
     * @param object
     * @return
     */
	@RequestMapping(value="list")
	@ResponseBody
	public Object list(@RequestBody JSONObject object){
		try {
			return userRecordService.getPageList(object);
		} catch (Exception e){
            e.printStackTrace();
        }
        return null;
	}
	
	/**
     * 保存
     * @param userRecord
     * @return
     */
 	@RequestMapping(value="add")
	@ResponseBody
	public JsonResult add(@RequestBody UserRecord userRecord){
	 try{
            Integer count = userRecordService.save(userRecord);
            return JsonResult.OK(count);
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonResult.Fail(BuzCode.Fail);
	}
	
	/**
     * 删除
     * @param id
     * @return
     */
 	@RequestMapping(value="delUserRecord")
	@ResponseBody
	public JsonResult del(Integer id){
		try {
			return JsonResult.OK(userRecordService.deleteByPrimaryKey(id));
		} catch (BuzEx ex){
            return JsonResult.Fail(ex.getBuzCode());
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonResult.Fail(BuzCode.Fail);
	}
	
	/**
     * 更新
     * @param userRecord
     * @return
     */
	@RequestMapping(value = "update")
	@ResponseBody
	public JsonResult update(UserRecord userRecord) {
		try {
			int result=userRecordService.updateByPrimaryKey(userRecord);
			return JsonResult.OK(result);
        } catch (BuzEx ex){
            return JsonResult.Fail(ex.getBuzCode());
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonResult.Fail(BuzCode.Fail);
	}
	
	
	@RequestMapping(value="detail/{id}")
	@ResponseBody
	public JsonResult detail(@PathVariable Integer id){
		try {
			  return JsonResult.OK(userRecordService.selectByPrimaryKey(id));
        }catch (BuzEx ex){
            return JsonResult.Fail(ex.getBuzCode());
        }catch (Exception e){
            e.printStackTrace();
        }
        return JsonResult.Fail(BuzCode.Fail);
	}
}
