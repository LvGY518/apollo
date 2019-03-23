package com.clancey.apollo.config.shiro;

import com.clancey.apollo.common.utils.ApplicationContextUtil;
import com.clancey.apollo.sys.entity.User;
import com.clancey.apollo.sys.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class ApolloShiroRealm extends AuthorizingRealm {
    private static final Logger log = LoggerFactory.getLogger(ApolloSimpleCredentialsMatcher.class);

    /**
     * 验证用户身份
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        if (authToken == null)
            throw new AuthenticationException("parameter token is null");
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        // 校验用户名密码
        User user = ApplicationContextUtil.getBean(UserService.class).getUserByUsername(token.getUsername());
        if (user != null) {
//            if(!pawDES.equals(user.getPassword())){
//                throw new IncorrectCredentialsException();
//            }
            //这样前端页面可取到数据
            SecurityUtils.getSubject().getSession().setAttribute("user", user);
            SecurityUtils.getSubject().getSession().setAttribute("userId", user.getId());
            // 注意此处的返回值没有使用加盐方式,如需要加盐，可以在密码参数上加
            return new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
        }
        throw new UnknownAccountException();
    }

    /**
     * 授权用户权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取用户
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取用户角色
        Set<String> roleSet = new HashSet<>();
        roleSet.add("100002");
        info.setRoles(roleSet);

        //获取用户权限
        Set<String> permissionSet = new HashSet<>();
        permissionSet.add("权限添加");
        permissionSet.add("权限删除");
        info.setStringPermissions(permissionSet);
        return info;
    }
}
