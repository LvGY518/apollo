package com.clancey.apollo.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Pattern;

/**
 * @author chens
 * @date 2018/11/15 16:19
 */
public class PinyinUtil {
    /**
     * 将多个汉字转换为拼音(取首字母或全拼)
     *
     * @param source
     * @param full
     * @return
     */
    public static String convertToPinyin(String source, boolean full) {
        if (source == null || source.trim().isEmpty()) {
            return "";
        }

        /***
         * ^[\u2E80-\u9FFF]+$ 匹配所有东亚区的语言
         * ^[\u4E00-\u9FFF]+$ 匹配简体和繁体
         * ^[\u4E00-\u9FA5]+$ 匹配简体
         */
        Pattern pattern = Pattern.compile("^[\u4E00-\u9FFF]+$");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            if (!match(pattern, String.valueOf(c))) {
                sb.append(c);
                continue;
            }

            if (full) {
                sb.append(convertSingleToPingYin(c));
            } else {
                sb.append(convertSingleToPingYin(c).charAt(0));
            }
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 将单个汉字转换为拼音
     *
     * @param word
     * @return
     */
    private static String convertSingleToPingYin(char word) {
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try {
            String[] results = PinyinHelper.toHanyuPinyinStringArray(word, outputFormat);
            return results[0];
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 检查是否为汉字
     *
     * @param pattern
     * @param str
     * @return
     */
    private static boolean match(Pattern pattern, String str) {
        return pattern.matcher(str).find();
    }
}
