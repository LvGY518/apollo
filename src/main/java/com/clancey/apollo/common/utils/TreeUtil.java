package com.clancey.apollo.common.utils;


import com.clancey.apollo.common.base.LevelCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenShuai
 * @date 2018/9/13 14:39
 */
public class TreeUtil {
    /**
     * 生成树
     * @param array
     * @param codeSpan
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends LevelCode> List<T> generateTree(List<T> array, final int codeSpan) {
        if (array.isEmpty()) {
            return new ArrayList<>();
        }

        //找出最短的合法levelCode长度
        int codeStartLength = getMinimumCodeLength(array, codeSpan);
        List<T> result = new ArrayList<>();
        for (T t : array) {
            String levelCode = t.getLevelCode();
            if (levelCode.length() == codeStartLength) {
                t.setChildren(generateChildren(array, levelCode, codeSpan));
                result.add(t);
            }
        }
        return result;
    }

    /**
     * 生成子节点
     * @param array
     * @param levelCode
     * @param codeSpan
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    private static <T extends LevelCode> List<T> generateChildren(List<T> array, String levelCode, final int codeSpan) {
        List<T> result = new ArrayList<>();
        for (T t : array) {
            String tempLevelCode = t.getLevelCode();
            if (tempLevelCode.startsWith(levelCode) && tempLevelCode.length() - levelCode.length() == codeSpan) {
                t.setChildren(generateChildren(array, tempLevelCode, codeSpan));
                result.add(t);
            }
        }
        return result;
    }

    private static <T extends LevelCode> int getMinimumCodeLength(List<T> array, int codeSpan) {
        int minimumCode = -1;
        for (T t : array) {
            int codeLength = t.getLevelCode().length();
            //只处理满足levelCode层级要求的数据
            if (codeLength % codeSpan != 0) {
                continue;
            }
            //如果当前长度小于最小levelCode长度，则替换
            if (codeLength < minimumCode) {
                minimumCode = codeLength;
                continue;
            }
            //如果当前的比对是初始值，则设置为最小值
            if (minimumCode == -1) {
                minimumCode = codeLength;
            }
        }
        //最小的编码长度必须大于等于codeSpan
        return minimumCode > codeSpan ? minimumCode : codeSpan;
    }
}
