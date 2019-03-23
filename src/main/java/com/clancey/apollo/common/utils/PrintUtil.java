package com.clancey.apollo.common.utils;

import com.alibaba.fastjson.JSON;

/**
 * @author ChenShuai
 * @date 2018/8/23 16:18
 */
public class PrintUtil {
    public static void printString(String s) {
        System.out.println(s);
    }

    public static void printJson(Object o) {
        System.out.println(JSON.toJSONString(o, true));
    }
}
