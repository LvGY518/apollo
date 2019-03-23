package com.clancey.apollo.common.base;

import com.clancey.apollo.common.actable.annotation.Column;
import com.clancey.apollo.common.actable.constants.MySqlTypeConstant;
import com.clancey.apollo.common.actable.annotation.Column;
import com.clancey.apollo.common.actable.constants.MySqlTypeConstant;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ChenShuai
 * @date 2018/8/22 15:42
 */
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1068386884929087009L;

    @Id
    @Column(name = "id", type = MySqlTypeConstant.VARCHAR, isKey = true, length = 64)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected String id;

    /**
     * 创建时间
     */
    @Column(name = "create_date", type = MySqlTypeConstant.DATETIME, isNull = false)
    protected Date createDate;

    /**
     * 更新时间
     */
    @Column(name = "update_date", type = MySqlTypeConstant.DATETIME, isNull = false)
    protected Date updateDate;

    /**
     * 创建人
     */
    @Column(name = "create_user", type = MySqlTypeConstant.VARCHAR, isNull = false)
    protected String createUser;

    /**
     * 修改人
     */
    @Column(name = "update_user", type = MySqlTypeConstant.VARCHAR, isNull = false)
    protected String updateUser;

    /**
     * 是否被删除 0-正常 1-已删除
     */
    @Column(name = "deleted", type = MySqlTypeConstant.TINYINT, isNull = false, length = 1)
    protected Boolean deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
