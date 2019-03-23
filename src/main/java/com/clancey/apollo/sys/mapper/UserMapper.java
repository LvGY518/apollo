package com.clancey.apollo.sys.mapper;

import com.clancey.apollo.common.base.BaseMapper;
import com.clancey.apollo.common.base.BaseMapper;
import com.clancey.apollo.proofing.pojo.UserProofingPOJO;
import com.clancey.apollo.sys.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    List<User> selectUsersWithRoleId(@Param("roleId") String roleId, @Param("name") String name);

    List<User> selectUsersWithoutRoleId(@Param("roleId") String roleId, @Param("name") String name);

    User selectByUserName(@Param("username") String username);

    /**
     * 通过role id 列表 查询用户
     *
     * @param roleIdList
     * @return
     */
    List<UserProofingPOJO> findListByRoleIdList(@Param("roleIdList") List<String> roleIdList);


    /**
     * 通过user id 列表 查询用户
     *
     * @param userIdList
     * @return
     */
    List<UserProofingPOJO> findListByUserIdList(@Param("userIdList") List<String> c);
}
