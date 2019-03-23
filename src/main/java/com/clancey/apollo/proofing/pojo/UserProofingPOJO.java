package com.clancey.apollo.proofing.pojo;

import com.clancey.apollo.common.actable.annotation.Column;
import com.clancey.apollo.common.actable.constants.MySqlTypeConstant;
import com.clancey.apollo.common.actable.annotation.Column;
import com.clancey.apollo.common.actable.constants.MySqlTypeConstant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProofingPOJO {
    private  String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机
     */
    @Column(name = "mobile_phone", type = MySqlTypeConstant.VARCHAR)
    private String mobilePhone;


    /**
     * 部门id
     */
    @Column(name = "department_id", type = MySqlTypeConstant.VARCHAR, isNull = false)
    private String departmentId;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 是管理人员
     */
    private Boolean leader;

    /**
     * 打样中订单数量
     */
    private Integer  proofingNum;

}
