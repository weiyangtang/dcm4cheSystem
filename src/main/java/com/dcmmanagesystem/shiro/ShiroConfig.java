package com.dcmmanagesystem.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ShiroConfig配置类
 */
@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /**
         * 添加内置Shiro过滤器，可以实现权限的拦截器
         * anon:无需认证登录，可以访问
         * authc:必须认证才可以访问
         * user:如果remember,可以直接访问
         * perms:必须得到资源权限才能访问
         * role:必须得到角色权限才能访问
         * */
        Map<String, String> filterMap = new LinkedHashMap<>();

        filterMap.put("/login", "anon");
        // 设置login地址
        shiroFilterFactoryBean.setLoginUrl("/login");
        /*验证码放行*/
        filterMap.put("/getCaptchaCode", "anon");
        /*天气预报放行*/
        filterMap.put("/weatherInfo", "anon");
        filterMap.put("/todayWeatherInfo", "anon");
        filterMap.put("/static/**", "anon");
        // 针对Swagger拦截放行
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/swagger/**", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/v2/**", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/configuration/**", "anon");

        filterMap.put("/downloadfile", "anon");
        /**
         * 认证拦截authc必须放在最后
         * */
//        filterMap.put("/*", "authc");
        filterMap.put("/**", "authc");
        //设置未授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/401");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;
    }

    /*
     * 创建DefaultWebSecurityManager
     * */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /*
     *创建Realm
     * */
    @Bean(name = "userRealm")
    public UserRealm getRealm() {
        return new UserRealm();
    }

    /***
     *thymeleaf页面设置shiro权限
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
