package com.clancey.apollo.sys.service.impl;

import com.clancey.apollo.common.RedisService;
import com.clancey.apollo.common.base.BaseServiceImpl;
import com.clancey.apollo.common.constant.RedisConstant;
import com.clancey.apollo.common.utils.Query;
import com.clancey.apollo.common.vo.TableResultResponse;
import com.clancey.apollo.sys.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.clancey.apollo.constants.UserConstant;
import com.clancey.apollo.common.vo.TableResultResponse;
import com.clancey.apollo.common.utils.Query;
import com.clancey.apollo.common.RedisService;
import com.clancey.apollo.common.base.BaseServiceImpl;
import com.clancey.apollo.common.constant.RedisConstant;
import com.clancey.apollo.proofing.pojo.UserProofingPOJO;
import com.clancey.apollo.sys.entity.User;
import com.clancey.apollo.sys.mapper.UserMapper;
import com.clancey.apollo.sys.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ChenShuai
 * @date 2018/9/21 11:38
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {
    @Resource
    RedisService redisService;

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */

    @Override
    public User getUserByUsername(String username) {
        String key = RedisConstant.USER_PREFIX + username;
        User user = new User();
//        try {
//            user = (User) redisService.get(key);
//        } catch (Exception e) {
//            redisService.remove(key);
//        }
//        if (user != null) {
//            return user;
//        } else {
//            user = new User();
            user.setUsername(username);
            user = this.selectOne(user);
//            redisService.set(key, user);
            return user;
//        }
    }

    @Override
    public void insertSelective(User entity) {
        String password = new BCryptPasswordEncoder(UserConstant.PW_ENCODER_SALT).encode(entity.getPassword());
        entity.setPassword(password);
        super.insertSelective(entity);
    }

    @Override
    public TableResultResponse<User> selectByName(Query query) {
        Example example = new Example(User.class);
        Example.Criteria criteria1 = example.createCriteria();
        Example.Criteria criteria2 = example.createCriteria();
        if (query.get("name") != null) {
            criteria1.andLike("name", "%" + query.get("name").toString() + "%");
            criteria2.andLike("username", "%" + query.get("name").toString() + "%");
        }
        example.or(criteria2);
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<User> list = mapper.selectByExample(example);
        return new TableResultResponse<>(result.getTotal(), list);
    }

    @Override
    public TableResultResponse<User> selectUsersWithRoleId(Query query) {
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<User> list = mapper.selectUsersWithRoleId(query.get("roleId").toString(),
                query.get("name") != null ? query.get("name").toString() : null);
        return new TableResultResponse<>(result.getTotal(), list);
    }

    @Override
    public TableResultResponse<User> selectUsersWithoutRoleId(Query query) {
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<User> list = mapper.selectUsersWithoutRoleId(query.get("roleId").toString(),
                query.get("name") != null ? query.get("name").toString() : null);
        return new TableResultResponse<>(result.getTotal(), list);
    }

    /**
     * 通过role id 列表 查询用户
     *
     * @param roleIdList
     * @return
     */
    @Override
    public List<UserProofingPOJO> findListByRoleIdList(@Param("roleIdList") List<String> roleIdList) {
        if (roleIdList != null && roleIdList.size() > 0) {
            return mapper.findListByRoleIdList(roleIdList);
        }
        return null;
    }

    /**
     * 通过user id 列表 查询用户
     * @param userIdList
     * @return
     */
    @Override
    public List<UserProofingPOJO> findListByUserIdList(List<String> userIdList){
        if(userIdList!=null&&userIdList.size()>0){
            return mapper.findListByUserIdList(userIdList);
        }
        return null;
    }

    @Override
    public User selectByUserName(String username) {
        return mapper.selectByUserName(username);
    }

    @Override
    public List<User> findUserListByDeptIdList(List<String> deptIdList) {
        if(deptIdList!=null&&deptIdList.size()>0) {
            Example example = new Example(User.class);
            example.createCriteria().andIn("departmentId", deptIdList);
            example.selectProperties("id");
            example.selectProperties("name");
            example.selectProperties("departmentId");
            return mapper.selectByExample(example);
        }
        return null;
    }
}
