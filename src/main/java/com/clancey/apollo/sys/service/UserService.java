package com.clancey.apollo.sys.service;

import com.clancey.apollo.common.base.BaseService;
import com.clancey.apollo.common.utils.Query;
import com.clancey.apollo.common.vo.TableResultResponse;
import com.clancey.apollo.proofing.pojo.UserProofingPOJO;
import com.clancey.apollo.sys.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ChenShuai
 * @date 2018/9/18 11:23
 */
public interface UserService extends BaseService<User> {
    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */

    User getUserByUsername(String username);

    void insertSelective(User entity);

    TableResultResponse<User> selectByName(Query query);

    TableResultResponse<User> selectUsersWithRoleId(Query query);

    TableResultResponse<User> selectUsersWithoutRoleId(Query query);

    /**
     * 通过role id 列表 查询用户
     * @param roleIdList
     * @return
     */
    List<UserProofingPOJO> findListByRoleIdList(@Param("roleIdList")List<String> roleIdList);

    /**
     * 通过user id 列表 查询用户
     * @param userIdList
     * @return
     */
    List<UserProofingPOJO> findListByUserIdList(@Param("userIdList")List<String> userIdList);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User selectByUserName(String username);

    List<User> findUserListByDeptIdList(List<String> deptIdList);
}
