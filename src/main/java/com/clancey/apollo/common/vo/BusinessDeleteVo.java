package com.clancey.apollo.common.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @author chens
 * @date 2018/12/4 16:17
 */
@Getter
@Setter
public class BusinessDeleteVo {
    /**
     * 业务表名称
     */
    @NotEmpty
    private String tableName;

    /**
     * 业务表id
     */
    @NotEmpty
    private String tableId;
}
