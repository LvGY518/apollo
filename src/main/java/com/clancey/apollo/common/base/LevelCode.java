package com.clancey.apollo.common.base;

import java.util.List;

/**
 * @author ChenShuai
 * @date 2018/9/13 14:37
 */
public interface LevelCode<T extends LevelCode> {

    /**
     * 层级编码
     *
     * @return
     */
    String getLevelCode();

    /**
     * 子节点
     *
     * @param children
     */
    void setChildren(List<T> children);
}
