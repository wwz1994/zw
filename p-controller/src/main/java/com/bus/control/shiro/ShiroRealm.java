package com.bus.control.shiro;

import com.bus.IMenuService;
import com.bus.IUserService;
import com.bus.constant.ResultCode;
import com.bus.userInfo.ShiroPrincipal;
import com.bus.utils.MD5Utils;
import com.bus.utils.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bus.vo.Menu;
import com.bus.vo.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wwz on 2018-11-19.
 */
@Component
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private IUserService userService;
    @Autowired
    private IMenuService menuService;
    private final static  String permission = "_permission:";
    private final static  String key = "com.bus.control.shiro.ShiroRealm_0";
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        List<String> roles=new ArrayList<>();
        info.addRoles(roles);//设置角色
        List<String> permissions = new ArrayList<>();// RedisUtils.getList(permission+principals);
        //if(ObjectUtils.isNull(permissions)){

            User user = userService.loginByUser(principals.toString());
              List<Menu> menus =  menuService.queryMenuByUserId(user.getId());
            for(Menu menu:menus){
                permissions.add(menu.getMenuCode());
            }
            //RedisUtils.setList(permission+principals,permissions);
       // }
        info.addStringPermissions(permissions);//设置权限
        return info;

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        Object username = token.getPrincipal();
        if(StringUtils.isEmpty(username)){
            throw new AuthenticationException(ResultCode.username_error.getCode().toString());
        }
        Object password = token.getCredentials();
        if(StringUtils.isEmpty(password)){
            throw new AuthenticationException(ResultCode.pwd_error.getCode().toString());
        }
        User user = userService.loginByUser(username.toString());
        if(user == null){
            throw new AuthenticationException(ResultCode.username_error.getCode().toString());
        }
        StringBuilder builder = new StringBuilder();
        char[] chars =(char[]) password;
        for(int i = 0;i<chars.length;i++){
            builder.append(chars[i]) ;
        }
        String userName = user.getUserName();
        String pwd = MD5Utils.MD5Encode(user.getUserName()+"_"+user.getSalt()+"_"+ builder.toString(),"utf-8");
        if(!pwd.equals(user.getPassword())) {
            throw new AuthenticationException(ResultCode.pwd_error.getCode().toString());
        }
        List<Menu> menus = menuService.queryMenu(user.getId());
        List<String> list = getPermissions(user.getId(),menus);
        ShiroPrincipal shiroPrincipal =  new ShiroPrincipal(menus,user,list);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username.toString(),password,this.getName());
        return info;
    }

    /**
     * 获取菜单权限
     * @param id
     * @return
     */
    public List<String> getPermissions(Integer id,List<Menu> menus){
            List<String> permissions = new ArrayList<>();
          //  List<Menu> menus =  menuService.queryMenuByUserId(id);
            for(Menu menu:menus){
                permissions.add(menu.getMenuCode());
            }
        return permissions;
    }
    /*private List<String> getRoles(Integer id){
            List<String> permissions = new ArrayList<>();
            List<Menu> menus =  roleService.(id);
            for(Menu menu:menus){
                permissions.add(menu.getMenuCode());
            }
        return permissions;
    }*/
}