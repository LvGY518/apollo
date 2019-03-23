package com.clancey.apollo.common.utils;

/**
 * @author ChenShuai
 * @date 2018/9/3 15:41
 */
public class RepeatUtil {
    public static final String REPEAT_HEADER = "Next-Request-Token";

    public static boolean valid(String token) {
        if (token == null) return false;
        return token.equals(SessionUtil.get(REPEAT_HEADER));
    }

    public static String nextToken() {
        String token = UUIDUtil.randomUUID();
        SessionUtil.set(REPEAT_HEADER, token);
        return token;
    }
}
