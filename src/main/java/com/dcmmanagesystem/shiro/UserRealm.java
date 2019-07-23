package com.dcmmanagesystem.shiro;


import com.dcmmanagesystem.model.User;
import com.dcmmanagesystem.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    /*打印日志*/
    private static Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    UserService userService;

    /***
     * 执行授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");
        //给资源进行授权
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //要与 filterMap.put("/user/list", "perms[1]");中的perms[]的1一致
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
//        User dbUser = userService.getUserById(user.getUserId());
        String userRole = userService.getUserRole(user.getUserId());

        /***
         *shiro设置用户的权限角色，admin,user,guest
         */
        simpleAuthorizationInfo.addStringPermission(userRole);

        return simpleAuthorizationInfo;
    }

    /***
     * 执行登录认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        logger.info(token.getUsername() + "登录认证");

        User user = userService.getUserById(token.getUsername());

        if (user == null) {
            //用户不存在
            logger.info(token.getUsername() + "用户不存在");
            return null;
        }
        return new SimpleAuthenticationInfo(user, user.getUserPassword(), "");
    }
}
