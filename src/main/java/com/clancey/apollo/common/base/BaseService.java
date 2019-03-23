package com.clancey.apollo.common.base;

import com.clancey.apollo.common.utils.Query;
import com.clancey.apollo.common.utils.Query;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @author ChenShuai
 * @date 2018/9/21 11:03
 */
public interface BaseService<T extends BaseEntity> {
    T selectOne(T entity);

    T selectById(String id);

    @SuppressWarnings("unchecked")
    List<T> selectByIds(Iterable<String> ids);

    List<T> selectList(T entity);

    @SuppressWarnings("unchecked")
    List<T> selectList();

    List<T> selectList(Map<String, Object> params);

    List<T> selectListWithDeleted();

    Long selectCount(T entity);

    void insert(T entity);

    void insertSelective(T entity);

    void insertAll(List<T> entities);

    void insertAllSelective(List<T> entities);

    void delete(T entity);

    void deleteById(Object id);

    void updateById(T entity);

    void updateAllById(List<T> entities);

    void updateAllSelectiveById(List<T> entities);

    void updateSelectiveById(T entity);

    List<T> selectByExample(Example example);

    int selectCountByExample(Example example);

    Map<String, Object> selectByQuery(Query query);

    /**
     *
     * @param query
     * @return
     * 排除删除状态的并且以创建时间排序
     */
    Map<String, Object> selectByQueryNoDelted(Query query);

    Map<String, Object> selectByQueryHasDate(Query query);

    public T selectBySearchInfo(Map<String,Object> param);

    public List<T> findBySearchInfo(Map<String,Object> param);

    public void insertOrUpdate(T entity);

}
