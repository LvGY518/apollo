package com.clancey.apollo.common.base;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

/**
 * @author ChenShuai
 * @date 2018/8/31 14:38
 */
public class BasePhysicalSqlProvider extends MapperTemplate {
    public BasePhysicalSqlProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String deleteAllPhysically(MappedStatement ms) {
        Class<?> entityClass = getEntityClass(ms);
        return SqlHelper.deleteFromTable(entityClass, tableName(entityClass));
    }
}
