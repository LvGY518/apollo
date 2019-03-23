package com.clancey.apollo.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author ChenShuai
 * @date 2018/9/6 17:08
 */
public class RequestUtil {
    /**
     * 获取session对象
     *
     * @return
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取request对象
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static Params getParams() {
        return new Params(getRequest().getParameterMap());
    }

    public static class Params {
        private Map<String, String[]> requestParams;

        Params(Map<String, String[]> requestParamMap) {
            this.requestParams = requestParamMap;
        }

        public String get(String key) {
            return getFirst(key);
        }

        public String getString(String key) {
            return getFirst(key);
        }

        public Integer getInteger(String key) {
            return Integer.valueOf(getFirst(key));
        }

        public Double getDouble(String key) {
            return Double.valueOf(getFirst(key));
        }

        private String getFirst(String key) {
            return requestParams.get(key)[0];
        }
    }
}
