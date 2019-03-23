package com.clancey.apollo.common.base;

import com.clancey.apollo.common.utils.EntityUtil;
import com.clancey.apollo.common.utils.Query;
import com.clancey.apollo.common.utils.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.clancey.apollo.common.utils.Query;
import com.clancey.apollo.common.utils.EntityUtil;

import com.clancey.apollo.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

@Slf4j
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> implements BaseService<T> {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    private SqlSessionFactory sqlSessionFactory;

    /**
     * void
     *
     * @author ZhengZongYuan
     * 初始化泛型类型,以及为批量操作做初始化
     */
    @PostConstruct
    public void initNecessaryCondition() {
        //初始化
        sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        rawType = getEntityClass();
    }


    @Autowired
    protected M mapper;
    protected T entity;

    /**
     *
     */
    protected Class<T> rawType;

    public void setMapper(M mapper) {
        this.mapper = mapper;
    }

    public M getMapper() {
        return this.mapper;
    }

    @Override
    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }

    @Override
    public T selectById(String id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> selectByIds(Iterable<String> ids) {
        Example example = new Example(rawType);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        return mapper.selectByExample(example);
    }

    @Override
    public List<T> selectList(T entity) {
        return mapper.select(entity);
    }

    @Override
    public List<T> selectList() {
        Example example = new Example(rawType);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("deleted", "0");
        return mapper.selectByExample(example);
    }

    @Override
    public List<T> selectList(Map<String, Object> params) {
        Example example = new Example(rawType);
        Example.Criteria criteria = example.createCriteria();
        params.forEach(criteria::andEqualTo);
        return mapper.selectByExample(example);
    }

    @Override
    public List<T> selectListWithDeleted() {
        return mapper.selectAll();
    }

    @Override
    public Long selectCount(T entity) {
        return (long) mapper.selectCount(entity);
    }

    @Override
    public void insert(T entity) {
        EntityUtil.setCreateInfoIfNull(entity);
        mapper.insert(entity);
    }

    @Override
    public void insertSelective(T entity) {
        EntityUtil.setCreateInfoIfNull(entity);
        mapper.insertSelective(entity);
    }

    @Override
    public void insertAll(List<T> entities) {
        entities.forEach(this::insert);
    }

    @Override
    public void insertAllSelective(List<T> entities) {
        entities.forEach(this::insertSelective);
    }

    @Override
    public void delete(T entity) {
        mapper.delete(entity);
    }

    @Override
    public void deleteById(Object id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateById(T entity) {
        EntityUtil.setUpdatedInfoIfNull(entity);
        mapper.updateByPrimaryKey(entity);
    }

    @Override
    public void updateAllById(List<T> entities) {
        entities.forEach(this::updateById);
    }

    @Override
    public void updateAllSelectiveById(List<T> entities) {
        entities.forEach(this::updateSelectiveById);
    }

    @Override
    public void updateSelectiveById(T entity) {
        EntityUtil.setUpdatedInfoIfNull(entity);
        mapper.updateByPrimaryKeySelective(entity);

    }

    @Override
    public List<T> selectByExample(Example example) {
        return mapper.selectByExample(example);
    }

    @Override
    public Map<String, Object> selectByQueryHasDate(Query query) {
        Map<String, Object> dateParams = new HashMap<>(2);
        Example example = new Example(rawType);
        Example.Criteria criteria = example.createCriteria();
        for (Map.Entry<String, Object> entry : query.entrySet()) {
            if (!StringUtil.isNullOrEmpty(entry.getKey()) && entry.getKey().getClass().equals(Date.class)) {
                if (entry.getKey().equalsIgnoreCase("startRangeBeginDate") || entry.getKey().equalsIgnoreCase("startRangeEndDate")) {
                    dateParams.put(entry.getKey(),entry.getValue());
                }

            } else {
                criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
            }
        }
        criteria.andBetween("createDate",dateParams.get("startRangeBeginDate"),dateParams.get("startRangeEndDate"));

        Page<Object> page = PageHelper.startPage(query.getPage(), query.getLimit());
        List<T> data = mapper.selectByExample(example);
        Map<String, Object> result = new HashMap<>();
        result.put("rowTotal", page.getTotal());
        result.put("pageNum", page.getPageNum());
        result.put("pageSize", page.getPageSize());
        result.put("pageTotal", page.getPages());
        result.put("rows", data);
        return result;
    }

    @Override
    public int selectCountByExample(Example example) {
        return mapper.selectCountByExample(example);
    }

    @Override
    public Map<String, Object> selectByQuery(Query query) {
        Example example = new Example(rawType);
        Example.Criteria criteria = example.createCriteria();
        for (Map.Entry<String, Object> entry : query.entrySet()) {
            criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
        }
        Page<Object> page = PageHelper.startPage(query.getPage(), query.getLimit());
        List<T> data = mapper.selectByExample(example);
        Map<String, Object> result = new HashMap<>();
        result.put("rowTotal", page.getTotal());
        result.put("pageNum", page.getPageNum());
        result.put("pageSize", page.getPageSize());
        result.put("pageTotal", page.getPages());
        result.put("rows", data);
        return result;
    }


    @Override
    public Map<String, Object> selectByQueryNoDelted(Query query) {
        Example example = new Example(rawType);
        example.setOrderByClause("CREATE_DATE DESC");
        Example.Criteria criteria = example.createCriteria();
        for (Map.Entry<String, Object> entry : query.entrySet()) {
            criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
        }
        criteria.andEqualTo("deleted",false);
        Page<Object> page = PageHelper.startPage(query.getPage(), query.getLimit());
        List<T> data = mapper.selectByExample(example);
        Map<String, Object> result = new HashMap<>();
        result.put("rowTotal", page.getTotal());
        result.put("pageNum", page.getPageNum());
        result.put("pageSize", page.getPageSize());
        result.put("pageTotal", page.getPages());
        result.put("rows", data);
        return result;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getEntityClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @SuppressWarnings({"unchecked", "unused"})
    private Class<M> getMapperClass() {
        return (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * @param entitys void
     * @author ZhengZongYuan
     * 待定批处理 因为使用通用mapper批处理失效
     */
    @SuppressWarnings("unused")
    private void bacthSave(List<T> entitys) {
        //开启批量功能
        SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        //通过mapper执行批量
        try {
            entitys.forEach(entity -> {
                mapper.insert(entity);
            });
            //成功提交事务
            openSession.commit();
            openSession.clearCache();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("批量保存失败" + e.getStackTrace());
            openSession.rollback();
            openSession.clearCache();
        }
    }


    /**
     * 根据搜索信息查询数据
     * @param param
     * @return
     */
    @Override
    public T selectBySearchInfo(Map<String,Object> param) {
        Example example = new Example(rawType);
        Example.Criteria criteria = example.createCriteria();
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
        }
        List<T> data = mapper.selectByExample(example);
        if(data.size()>0){
            return data.get(0);
        }
        return null;
    }

    /**
     * 根据搜索信息查询数据(集合)
     * @param param
     * @return
     */
    @Override
    public List<T> findBySearchInfo(Map<String,Object> param) {
        Example example = new Example(rawType);
        Example.Criteria criteria = example.createCriteria();
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
            criteria.andEqualTo("deleted",false);
        }
        List<T> data = mapper.selectByExample(example);
        return data;
    }

    /**
     * 插入或者更新实体
     * @param entity
     */
    @Override
    public void insertOrUpdate(T entity){
        if(entity.getId() == null){
            EntityUtil.setCreateInfoIfNull(entity);
            mapper.insert(entity);
        }else{
            EntityUtil.setUpdatedInfoIfNull(entity);
            mapper.updateByPrimaryKey(entity);
        }
    }
}
