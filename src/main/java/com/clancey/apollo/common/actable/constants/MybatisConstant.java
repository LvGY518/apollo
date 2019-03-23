package com.clancey.apollo.common.actable.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="mybatis")
public class MybatisConstant {

    /**
     * 要扫描的model所在的pack
     */

    private String modelPack;

    /**
     * 自动创建模式：update表示更新，create表示删除原表重新创建
     */

    private String tableAuto;


    public String getModelPack() {

        return modelPack;
    }


    public void setModelPack(String modelPack) {

        this.modelPack = modelPack;
    }


    public String getTableAuto() {

        return tableAuto;
    }


    public void setTableAuto(String tableAuto) {

        this.tableAuto = tableAuto;
    }
}
