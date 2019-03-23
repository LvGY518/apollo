package com.clancey.apollo.common.base;

/**
 * @author ChenShuai
 * @date 2018/9/21 11:11
 */
public interface BasePhysicalService<T extends BaseEntity> extends BaseService<T> {
    /**
     * 物理删除表上所有数据
     */
    void deleteAllPhysically();
}
