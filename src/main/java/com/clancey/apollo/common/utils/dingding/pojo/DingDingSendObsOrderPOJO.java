package com.clancey.apollo.common.utils.dingding.pojo;

import java.math.BigInteger;

/**
 * pojo类
 * 钉钉推送 待分配/已分配未接单   订单POJO
 * @author DK
 *
 */
public class DingDingSendObsOrderPOJO {

	private String clientId, productId, productName;

	private Integer productNum;

	private BigInteger overDay;

    public String getClientId() {

        return clientId;
    }


    public void setClientId(String clientId) {

        this.clientId = clientId;
    }


    public String getProductId() {

        return productId;
    }


    public void setProductId(String productId) {

        this.productId = productId;
    }


    public String getProductName() {

        return productName;
    }


    public void setProductName(String productName) {

        this.productName = productName;
    }


    public BigInteger getOverDay() {

        return overDay;
    }


    public void setOverDay(BigInteger overDay) {

        this.overDay = overDay;
    }



    public Integer getProductNum() {

        return productNum;
    }



    public void setProductNum(Integer productNum) {

        this.productNum = productNum;
    }

}
