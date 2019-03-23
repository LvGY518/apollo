package com.clancey.apollo.sys.entity;

import com.clancey.apollo.common.actable.annotation.Column;
import com.clancey.apollo.common.actable.annotation.Table;
import com.clancey.apollo.common.actable.constants.MySqlTypeConstant;
import com.clancey.apollo.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import java.io.Serializable;

@Getter
@Setter
@javax.persistence.Table(name = "tb_sys_user")
@Table(name = "tb_sys_user")
public class User extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -2629874294346696734L;

    /**
     * 系统用户（虚拟）
     */
    @Transient
    public static final User SYSTEM = new User("SYSTEM", "SYSTEM");

    /**
     * 系统用户(自动任务、虚拟)
     */
    @Transient
    public static final User TASK = new User("TASK", "TASK");

    public User() {

    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 用户名
     */
    @Column(name = "username", type = MySqlTypeConstant.VARCHAR, isNull = false, isUnique = true)
    private String username;

    /**
     * 密码
     */
    @Column(name = "password", type = MySqlTypeConstant.VARCHAR, isNull = false)
    private String password;

    /**
     * 姓名
     */
    @Column(name = "name", type = MySqlTypeConstant.VARCHAR, isNull = false)
    private String name;

    /**
     * 生日
     */
    @Column(name = "birthday", type = MySqlTypeConstant.VARCHAR)
    private String birthday;

    /**
     * 地址
     */
    @Column(name = "address", type = MySqlTypeConstant.VARCHAR)
    private String address;

    /**
     * 手机
     */
    @Column(name = "mobile_phone", type = MySqlTypeConstant.VARCHAR)
    private String mobilePhone;

    /**
     * 固定电话
     */
    @Column(name = "tel_phone", type = MySqlTypeConstant.VARCHAR)
    private String telPhone;

    /**
     * 电子邮件
     */
    @Column(name = "email", type = MySqlTypeConstant.VARCHAR)
    private String email;

    /**
     * 性别
     */
    @Column(name = "sex", type = MySqlTypeConstant.VARCHAR)
    private String sex;

    /**
     * 用户类型
     */
    @Column(name = "type", type = MySqlTypeConstant.VARCHAR)
    private String type;

    /**
     * 部门id
     */
    @Column(name = "department_id", type = MySqlTypeConstant.VARCHAR, isNull = false)
    private String departmentId;

    /**
     * 角色id
     */
    @Column(name = "role_id", type = MySqlTypeConstant.VARCHAR, isNull = false)
    private String roleId;

    /**
     * 是管理人员
     */
    @Column(name = "leader", type = MySqlTypeConstant.TINYINT, isNull = false)
    private Boolean leader;

    /**
     * 状态
     */
    @Column(name = "disable", type = MySqlTypeConstant.TINYINT, isNull = false)
    private Integer disable;

    /**
     * 备注
     */
    @Column(name = "description", type = MySqlTypeConstant.VARCHAR)
    private String description;

    /**
     * 钉钉userid
     */
    @Column(name = "dingding_user_id", type = MySqlTypeConstant.VARCHAR,length = 64)
    private String dingdingUserId;
}
