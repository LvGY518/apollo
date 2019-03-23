package com.clancey.apollo.common.utils;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * @author ChenShuai
 * @date 2018/8/2 14:14
 */
public class SessionUtil {

    /**
     * 设置session
     *
     * @param key
     * @param value
     */
    public static void set(String key, Object value) {
        RequestUtil.getSession().setAttribute(key, value);
    }

    /**
     * 获取session
     *
     * @param key
     * @return
     */
    public static Object get(String key) {
        return RequestUtil.getSession().getAttribute(key);
    }

    /**
     * 统计指定前缀session的数量
     *
     * @param prefix
     * @return
     */
    public static int sum(String prefix) {
        int count = 0;
        HttpSession session = RequestUtil.getSession();
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            if (name.startsWith(prefix)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 清空指定前缀的session
     *
     * @param prefix
     * @return
     */
    public static int empty(String prefix) {
        int count = 0;
        HttpSession session = RequestUtil.getSession();
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String name = attributeNames.nextElement();
            if (name.startsWith(prefix)) {
                count++;
                session.removeAttribute(name);
            }
        }
        return count;
    }

    public static String getId() {
        return RequestUtil.getSession().getId();
    }
}
