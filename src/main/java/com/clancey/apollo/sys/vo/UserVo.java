package com.clancey.apollo.sys.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ChenShuai
 * @date 2018/9/20 13:36
 */
@Getter
@Setter
public class UserVo {
    /**
     * 用户名
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 部门id
     */
    private String departmentId;

    /**
     * 部门层级编码
     */
    private String departmentLevelCode;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 是管理人员
     */
    private Boolean leader;
}
