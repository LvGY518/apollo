package com.clancey.apollo.common.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ChenShuai
 * @date 2018/8/20 15:40
 */
public class StringUtil {
    public static boolean isNullOrEmpty(String s) {
        return s == null || "".equals(s);
    }

    public static String null2Empty(String s) {
        return s == null ? "" : s;
    }

    public static String valueOf(Object s) {
        return s == null ? null : s.toString();
    }

    public static List<String> parseStrByTag(String text, String beginChar, String endChar) {

        if (text == null) {
            return new ArrayList<String>();
        }
        // 去掉空格
        text = text.replace(" ", "");
        // 不要删掉下面这句话
        text = " " + text;
        Set<String> resultList = new HashSet<>();
        int startIndex = 0, endIndex = 0;
        try {
            while (startIndex != -1 && endIndex != -1) {
                startIndex = text.indexOf(beginChar, startIndex + 1);
                endIndex = text.indexOf(endChar, endIndex + 1);
                if (endIndex > 0) {
                    resultList.add(text.substring(startIndex + 2, endIndex));
                }
            }
        } catch (Exception ep) {
            ep.printStackTrace();
        }
        return new ArrayList<>(resultList);
    }
}
