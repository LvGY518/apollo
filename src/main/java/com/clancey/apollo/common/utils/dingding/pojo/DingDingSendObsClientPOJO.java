package com.clancey.apollo.common.utils.dingding.pojo;

import java.util.List;

/**
 * pojo类
 * 钉钉推送 待分配/已分配未接单   客户POJO
 * @author DK
 *
 */
public class DingDingSendObsClientPOJO {

	private String clientId, clientName,salesman;

	private List<DingDingSendObsOrderPOJO> dingDingSendObsOrderPOJO;

    public String getClientId() {

        return clientId;
    }


    public void setClientId(String clientId) {

        this.clientId = clientId;
    }


    public String getClientName() {

        return clientName;
    }


    public void setClientName(String clientName) {

        this.clientName = clientName;
    }


    public String getSalesman() {

        return salesman;
    }


    public void setSalesman(String salesman) {

        this.salesman = salesman;
    }



    public List<DingDingSendObsOrderPOJO> getDingDingSendObsOrderPOJO() {

        return dingDingSendObsOrderPOJO;
    }



    public void setDingDingSendObsOrderPOJO(List<DingDingSendObsOrderPOJO> dingDingSendObsOrderPOJO) {

        this.dingDingSendObsOrderPOJO = dingDingSendObsOrderPOJO;
    }




}
