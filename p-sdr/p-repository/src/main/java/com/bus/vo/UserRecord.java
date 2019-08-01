package com.bus.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author wwz(1512180909@qq.com)<br>
 * @date 2019-01-29
 * 
 * @version 3.0
 */
public class UserRecord {

	/**主键*/
	private Integer id;
	
	/**用户标识*/
	private Integer userId;
	
	/**登录标识*/
	private String loginToken;
	
	/**登录日期*/
	private Date loginDate;
	
	/**有效期（分钟）*/
	private Integer loginExpire;
	
	
	public Integer getId(){
		return id;
	}
	
	/**
	 * 方法: 设置主键
	 *@param: java.lang.Integer  id
	 */
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getUserId(){
		return userId;
	}
	
	/**
	 * 方法: 设置用户标识
	 *@param: java.lang.Integer  userId
	 */
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public String getLoginToken(){
		return loginToken;
	}
	
	/**
	 * 方法: 设置登录标识
	 *@param: java.lang.String  loginToken
	 */
	public void setLoginToken(String loginToken){
		this.loginToken = loginToken;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getLoginDate(){
		return loginDate;
	}
	
	/**
	 * 方法: 设置登录日期
	 *@param: java.util.Date  loginDate
	 */
	public void setLoginDate(Date loginDate){
		this.loginDate = loginDate;
	}
	public Integer getLoginExpire(){
		return loginExpire;
	}
	
	/**
	 * 方法: 设置有效期（分钟）
	 *@param: java.lang.Integer  loginExpire
	 */
	public void setLoginExpire(Integer loginExpire){
		this.loginExpire = loginExpire;
	}
}
