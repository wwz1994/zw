package com.bus.userInfo;

import com.bus.vo.Menu;
import com.bus.vo.User;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wwz on 2018-11-22.
 */
public class ShiroPrincipal implements Serializable{
    private static  ThreadLocal<ShiroPrincipal> threadLocal ;
    private  List<Menu> menuList;
    private List<String> permissions;//菜单权限
    private List<String> roles;//角色
    private User user;
    public  List<Menu> getMenus() {
        return menuList;
    }
    static {
        threadLocal =  new ThreadLocal<ShiroPrincipal>();
    }

    public ShiroPrincipal(){}
    public ShiroPrincipal(List<Menu> menu){
        this.menuList = menu;
        threadLocal.set(this);
    }
    public ShiroPrincipal(List<Menu> menu, User user, List<String> permissions){
        this.user = user;
        this.menuList = menu;
        this.permissions = permissions;
        threadLocal.set(this);
        /*Subject subject = SecurityUtils.getSubject();
        SubjectUtils.setUser(user, subject);*/
    }

    public static ShiroPrincipal get() {
        return threadLocal.get();
    }

    public ShiroPrincipal(User user){
        this.user = user;
        threadLocal.set(this);
        /*Subject subject = SecurityUtils.getSubject();
        SubjectUtils.setUser(user, subject);*/
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}