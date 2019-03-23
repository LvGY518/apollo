package com.clancey.apollo.config;

import com.clancey.apollo.common.utils.RepeatUtil;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ChenShuai
 * @date 2018/9/3 15:26
 */
@Configuration
public class RepeatHandlerInterceptor extends HandlerInterceptorAdapter implements EnvironmentAware {
    private static final String METHOD_POST = "POST";
    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_DELETE = "DELETE";
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();
    private static Set<String> EXCEPTS = new HashSet<>();

    public RepeatHandlerInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //只处理可能修改数据的请求
        if (!isModify(request.getMethod())) {
            return true;
        }

        //免检的URL
        String url = request.getRequestURI();
        for (String pattern : EXCEPTS) {
            if (pathMatcher.match(pattern, url)) {
                return true;
            }
        }

        //如果有合法的token，则正常处理请求
        String reqToken = request.getHeader(RepeatUtil.REPEAT_HEADER);
        if (RepeatUtil.valid(reqToken)) {
            return true;
        }

        //没有通过检验，返回412
        response.setHeader(RepeatUtil.REPEAT_HEADER, RepeatUtil.nextToken());
        response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //修改数据的请求，处理完毕后，会更新token
        if (isModify(request.getMethod())) {
            response.setHeader(RepeatUtil.REPEAT_HEADER, RepeatUtil.nextToken());
        }
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }

    private boolean isModify(String httpMethod) {
        return METHOD_POST.equals(httpMethod) || METHOD_PUT.equals(httpMethod) || METHOD_DELETE.equals(httpMethod);
    }

    @Override
    public void setEnvironment(Environment environment) {
        for (int i = 0; i < 100; i++) {
            String ant = environment.getProperty("custom.excepts[" + i + "]");
            if (ant == null) {
                break;
            }
            EXCEPTS.add(ant);
        }
    }
}
