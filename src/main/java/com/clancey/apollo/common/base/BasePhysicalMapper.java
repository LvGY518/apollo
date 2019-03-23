package com.clancey.apollo.common.base;

import org.apache.ibatis.annotations.DeleteProvider;

/**
 * @author ChenShuai
 * @date 2018/8/31 14:40
 */
public interface BasePhysicalMapper<T extends BaseEntity> extends BaseMapper<T> {
    /**
     * 物理删除表上所有数据
     */
    @DeleteProvider(type = BasePhysicalSqlProvider.class, method = "dynamicSQL")
    void deleteAllPhysically();
}
