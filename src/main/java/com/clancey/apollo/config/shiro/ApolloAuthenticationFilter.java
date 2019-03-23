package com.clancey.apollo.config.shiro;

import com.clancey.apollo.common.utils.JwtUtil;
import com.clancey.apollo.common.utils.StringUtil;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ChenShuai
 * @date 2018/8/20 15:35
 */
public class ApolloAuthenticationFilter extends AuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        //默认按照cookie方式认证
        if (this.isLoginRequest(servletRequest, servletResponse)) {
            return true;
        }

        //根据Http请求的Header部分，验证是否为JWT请求，不是JWT请求则直接返回登录页
        String authToken = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        if (StringUtil.isNullOrEmpty(authToken) || !authToken.startsWith("Bearer ")) {
            this.saveRequestAndRedirectToLogin(servletRequest, servletResponse);
            return false;
        }

        //处理JWT请求
        return issueJwt(authToken.substring("Bearer ".length()), servletRequest, servletResponse);
    }

    /**
     * 处理JWT请求
     * @param jwtToken
     * @param servletRequest
     * @param servletResponse
     * @return
     */
    private boolean issueJwt(String jwtToken, ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (JwtUtil.validateToken(jwtToken)) {
            return true;
        }
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return false;
    }
}
