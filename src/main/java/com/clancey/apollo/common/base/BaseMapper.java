package com.clancey.apollo.common.base;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author ChenShuai
 * @date 2018/9/4 15:20
 */
public interface BaseMapper<T extends BaseEntity> extends Mapper<T> {

}
