package com.bus.control;

import com.alibaba.fastjson.JSONObject;
import com.bus.IUserService;
import com.bus.constant.ShareLoginDict;
import com.bus.result.JsonResult;
import com.bus.utils.MD5Utils;
import com.bus.utils.QQHttpClient;
import com.bus.vo.User;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.PageFans;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.PageFansBean;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.javabeans.weibo.Company;
import com.qq.connect.oauth.Oauth;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by wwz on 2019-07-01.
 */
@Controller
@RequestMapping({"/api","/web-v"})
public class LoginController {
    @Autowired
    private IUserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/wxLogin")
    public void wx(HttpServletResponse response) {
        try {
            response.sendRedirect("https://open.weixin.qq.com/connect/qrconnect?appid="
                    + ShareLoginDict.WX.getAppId()
                    + "&redirect_uri="
                    + URLEncoder.encode(ShareLoginDict.WX.getWxUrl())
                    + "&response_type=code&scope=snsapi_login&state=66666#wechat_redirect");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @RequestMapping("/qqAuth")
    public void qqLogin(HttpServletResponse response, HttpServletRequest request) {
        response.setContentType("text/html;charset=utf-8");
        try {
            response.sendRedirect(new Oauth().getAuthorizeURL(request));
        } catch (QQConnectException e) {
            e.printStackTrace();
        }catch (IOException e){

        }
    }

    @RequestMapping("/qqLogin")
    public void  qqLoginAfter(HttpServletResponse response, HttpServletRequest request) {
        try{
            HttpSession session = request.getSession();

            String code = request.getParameter("code");
            String state = request.getParameter("state");
            String uuid = (String) session.getAttribute("state");

            if(uuid != null){
                if(!uuid.equals(state)){
                    //throw new Exception("QQ,state错误");
                }
            }
            //Step2：通过Authorization Code获取Access Token
            String url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code"+
                    "&client_id=" + QQHttpClient.APPID +
                    "&client_secret=" + QQHttpClient.APPKEY +
                    "&code=" + code +
                    "&redirect_uri=" + QQHttpClient.CALLBACK;

            String access_token = QQHttpClient.getAccessToken(url);

            //Step3: 获取回调后的 openid 值
            url = "https://graph.qq.com/oauth2.0/me?access_token=" + access_token;
            String openid = QQHttpClient.getOpenID(url);

            //Step4：获取QQ用户信息
            url = "https://graph.qq.com/user/get_user_info?access_token=" + access_token +
                    "&oauth_consumer_key="+ QQHttpClient.APPID +
                    "&openid=" + openid;
              User user =   userService.selectByOpenId(openid);
            JSONObject jsonObject = QQHttpClient.getUserInfo(url);
            String avatarImg = jsonObject.getString("figureurl_qq");
            String nickname = jsonObject.getString("nickname");
            String gender = jsonObject.getString("gender");
            User user1 = new User();
            Subject subject = SecurityUtils.getSubject();
              if(user == null){
                  user1.setRealName(nickname);
                  user1.setPicture(avatarImg);
                  user1.setOpenID(openid);
                  user1.setUserName(openid);
                  String salt = getSalt();
                  user1.setSalt(salt);
                  String password = MD5Utils.MD5Encode(openid+"_"+salt+"_"+"123456","utf-8");
                  user1.setPassword(password);
                  user1.setRoleId(5);
                  user1.setAddTime(new Date());
                  this.userService.insertUser(user1);
                  UsernamePasswordToken info = new UsernamePasswordToken(openid, "123456");
                  subject.login(info);
                 // response.sendRedirect("/api/index");
              }else{
                  user1.setRealName(nickname);
                  user1.setPicture(avatarImg);
                  user1.setOpenID(openid);
                  user1.setUserName(openid);
                  user1.setId(user.getId());
                  user1.setUpdateTime(new Date());
                  this.userService.updateUser(user1);
                  UsernamePasswordToken info = new UsernamePasswordToken(openid, "123456");
                  subject.login(info);
                  //response.sendRedirect("/api/index");
              }
            redisTemplate.opsForValue().set("user:info"+":"+subject.getSession().getId(),user1,1L, TimeUnit.HOURS);

            response.sendRedirect("/api/index");
            response.setStatus(200);
        }catch (IOException e){

        }
    }
    private String getSalt(){
        return UUID.randomUUID().toString().substring(4,10);
    }

   /* @RequestMapping("/getWxUserInfo")
    public JsonResult userwx(String return_code) {
        JSONObject result = new JSONObject();
        Map<String, Object> token = (Map<String, Object>) WeiXinAPI
                .getToken(return_code);
        if (token != null && token.get("access_token") != null) {
            Map<String, Object> user = (Map<String, Object>) WeiXinAPI
                    .getWxUser(token.get("access_token").toString(),
                            token.get("openid").toString());
            if (user != null) {
                result.put("openid", user.get("openid"));
                result.put("nickname", user.get("nickname"));
                result.put("headimgurl", user.get("headimgurl"));
                result.put("data", "data_success");
            }else{
                result.put("data", "data_null");
            }
        }else{
            result.put("data", "data_null");
        }
        return JsonResult.OK(result);
    }*/
}