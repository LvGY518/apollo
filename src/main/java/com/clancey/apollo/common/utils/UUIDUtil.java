package com.clancey.apollo.common.utils;

import java.util.UUID;

/**
 * @author ChenShuai
 * @date 2018/8/1 16:48
 */
public class UUIDUtil {
    public static String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
