package com.clancey.apollo.common.base;

/**
 * @author ChenShuai
 * @date 2018/8/31 14:54
 */
public abstract class BasePhysicalServiceImpl<M extends BasePhysicalMapper<T>, T extends BaseEntity> extends BaseServiceImpl<M, T> implements BasePhysicalService<T> {
    /**
     * 物理删除表上所有数据
     */
    public void deleteAllPhysically() {
        mapper.deleteAllPhysically();
    }
}
