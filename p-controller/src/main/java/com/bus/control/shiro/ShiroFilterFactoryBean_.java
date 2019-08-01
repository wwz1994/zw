package com.bus.control.shiro;

import com.bus.IMenuService;
import com.bus.utils.StringUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import com.bus.vo.Menu;

import java.util.List;
import java.util.Map;

/**
 * Created by wwz on 2018-11-30.
 */
public class ShiroFilterFactoryBean_ extends ShiroFilterFactoryBean {

    @Autowired
    private IMenuService menuService;
    public    String definitions;

    private boolean open;

    private final  static String split=",";
    private final  static String perms="perms";
    private final  static String authc="/**=authc";

    private static  StringBuilder build  = new StringBuilder();
    private static  String url;
    static {
        url = "/api/logout = logout\n" +
                "/api/user/login=anon\n" +
                "/api/user/validLogin=anon\n";
       /* build.append("/api/logout = logout\n");
        build.append("/api/user/login=anon\n");
        build.append("/api/user/validLogin=anon\n");
        build.append("*//**=authc");//此项要放到最后*/
    }

    public  String getDefinitions() {
        return definitions;
    }

    public  void setDefinitions(String definitions) {
        this.definitions = definitions;
    }

    @Override
    public void setFilterChainDefinitions(String definitions) {
        if(this.open){
            build.append(definitions);
            try{
                this.definitions = getMenuCode();
            }catch (Exception e){
                e.printStackTrace();
            }
            super.setFilterChainDefinitions(this.definitions);
        }else{
            this.definitions = definitions+"\n"+authc;
            super.setFilterChainDefinitions(this.definitions);
        }

    }




    private String getMenuCode(){
        List<Menu> menus = menuService.queryMenuAll();
        /*if(!ObjectUtils.isNull(menus)){
            build.append(split);
        }*/
        int count = menus.size();
        if(count== 0){
            return "";
        }

        for(int i = 0;i<count;i++){
            String url = menus.get(i).getMenuUrl();
            int len = url.length();
            if(url.equals("#")|| StringUtils.isEmpty(url)||url.contains(".html")){
                continue;
            }
            if(url.substring(len-1,len).equals("/")){
                build.append(menus.get(i).getMenuUrl()+"**=");
            }else{
                build.append(menus.get(i).getMenuUrl()+"=");
            }
            build.append(perms + "[" + menus.get(i).getMenuCode() + "]\n");
        }
        String result = build.append(authc).toString();
        System.out.println("************菜单权限****************" + result + "****************************");
        return result;
    }


    public void setOpen(boolean open) {
        this.open = open;
    }

    public  void reloadFilterChains() {
            AbstractShiroFilter shiroFilter = null;

            try {
                shiroFilter = (AbstractShiroFilter) getObject();

                PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver) shiroFilter
                        .getFilterChainResolver();
                // 过滤管理器
                DefaultFilterChainManager manager = (DefaultFilterChainManager) resolver.getFilterChainManager();
                // 清除权限配置
                manager.getFilterChains().clear();
                getFilterChainDefinitionMap().clear();
                setOpen(true);
                // 重新设置权限
                setFilterChainDefinitions(url+this.definitions);//传入配置中的filterchains

                Map<String, String> chains = getFilterChainDefinitionMap();
                //重新生成过滤链
                if (!CollectionUtils.isEmpty(chains)) {
                    chains.forEach((url, definitionChains) -> {
                        manager.createChain(url, definitionChains.trim().replace(" ", ""));
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
