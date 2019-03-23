package com.clancey.apollo.common.utils;

import com.clancey.apollo.sys.entity.User;
import com.clancey.apollo.sys.service.UserService;
import io.jsonwebtoken.Claims;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ChenShuai
 * @date 2018/8/30 11:05
 */
@Component
public class UserUtil {
    private static UserService userService;

    @Autowired
    public UserUtil(UserService userService) {
        UserUtil.userService = userService;
    }

    public static User currentUser() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            User user = (User) session.getAttribute("user");
            if (user != null) {
                return user;
            }
        } catch (UnavailableSecurityManagerException exp) {
            exp.printStackTrace();
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        //JWT 请求
        String token = request.getHeader("Authorization");
        if (!StringUtil.isNullOrEmpty(token) && token.startsWith("Bearer ")) {
            token = token.substring("Bearer ".length());
            if (JwtUtil.validateToken(token)) {
                Claims claims = JwtUtil.parseToken(token);
                return userService.selectByUserName(claims.get("uid").toString());
            }
        }

        if (Thread.currentThread().getName().startsWith("task-")) {
            return User.TASK;
        }
        return User.SYSTEM;
    }

    public static String currentUserId() {
        try {
            if(Thread.currentThread().getName().equals("main")){
                return User.SYSTEM.getId();
            }
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            User user = (User) session.getAttribute("user");
            if (user != null) {
                return user.getId();
            }
        } catch (UnavailableSecurityManagerException exp) {
            //DO nothing
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        //JWT 请求
        String token = request.getHeader("Authorization");
        if (!StringUtil.isNullOrEmpty(token) && token.startsWith("Bearer ")) {
            token = token.substring("Bearer ".length());
            if (JwtUtil.validateToken(token)) {
                Claims claims = JwtUtil.parseToken(token);
                return claims.get("uid").toString();
            }
        }

        if (Thread.currentThread().getName().startsWith("task-")) {
            return User.TASK.getId();
        }
        return User.SYSTEM.getId();
    }
}
