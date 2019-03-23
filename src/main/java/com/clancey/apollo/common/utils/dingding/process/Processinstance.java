package com.clancey.apollo.common.utils.dingding.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
//import com.dingtalk.api.DefaultDingTalkClient;
//import com.dingtalk.api.DingTalkClient;
//import com.dingtalk.api.request.SmartworkBpmsProcessinstanceCreateRequest;
//import com.dingtalk.api.request.SmartworkBpmsProcessinstanceCreateRequest.FormComponentValueVo;
//import com.dingtalk.api.response.SmartworkBpmsProcessinstanceCreateResponse;
//import com.taobao.api.ApiException;

public class Processinstance {

	/*public static void main(String[] args) throws ApiException {
	    String access_token = null;
	    String corpid = "ding2c2ba48e9c25b94f35c2f4657eb6378f";
	    String secrect = "t54YkYspyNNMj4DBqxgZ0EDBGqbyDGSK16AgPeKouZWULKlr5ZhVJBAFH3NTdT9W";
	    String url = "https://oapi.dingtalk.com/gettoken?corpid="+corpid+"&corpsecret="+secrect;
//	    access_token= JSONObject.parseObject(HttpUtil.sendGet(url, "utf-8")).getString("access_token");
	    access_token="ef3c46215663335ba4f21ad9c79713c0";
	    System.out.println(access_token);

	    DingTalkClient client = new DefaultDingTalkClient("https://eco.taobao.com/router/rest");
	    SmartworkBpmsProcessinstanceCreateRequest req = new SmartworkBpmsProcessinstanceCreateRequest();
	    //企业微应用id
	//    req.setAgentId(41605932L);
	    req.setProcessCode("PROC-WIYJONZV-O79V310OU59EG1QAQQMD2-N4RECAHJ-2");
	    //发起人
	    req.setOriginatorUserId("036610366535124405");
	    //发起人部门
	    req.setDeptId(30627836L);

	    req.setApprovers("manager8052,0325045200842365");
	//    req.setCcList("zhangsan,lisi");
	//    req.setCcPosition("START");
	    List<FormComponentValueVo> list2 = new ArrayList<FormComponentValueVo>();
	    FormComponentValueVo obj3 = new FormComponentValueVo();
	    obj3.setName("名称");
	    obj3.setValue("1111122211");
	    list2.add(obj3);
	    FormComponentValueVo obj4 = new FormComponentValueVo();
	    obj4.setName("地点");
	    obj4.setValue("222222");
	    list2.add(obj4);
	    obj4 = new FormComponentValueVo();
	    obj4.setName("图片");
	    obj4.setValue("[\"https://img.alicdn.com/top/i1/LB14BcwgDTI8KJjSsphXXcFppXa\"]");
	    list2.add(obj4);
	    obj4 = new FormComponentValueVo();
	    obj4.setName("详情点击查看");
	    obj4.setValue("http://www.clancey.com");
	    list2.add(obj4);
	    obj3 = new FormComponentValueVo();
	    obj3.setName("客户");
	    obj3.setValue("孙奉了");
	    obj4 = new FormComponentValueVo();
	    obj4.setName("详细信息");
	    obj4.setValue("没有了");

	    FormComponentValueVo obj7 = new FormComponentValueVo();
	    obj7.setName("详细");
	    obj7.setValue(JSON.toJSONString(Arrays.asList(Arrays.asList(obj3, obj4),Arrays.asList(obj3, obj4))));
	    list2.add(obj7);

	    req.setFormComponentValues(list2);
	    SmartworkBpmsProcessinstanceCreateResponse rsp = client.execute(req, access_token);
	    System.out.println(JSONObject.toJSONString(rsp));

	}*/
}
