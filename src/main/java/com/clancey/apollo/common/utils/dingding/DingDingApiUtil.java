package com.clancey.apollo.common.utils.dingding;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.clancey.apollo.common.utils.HttpUtil;

public class DingDingApiUtil {

    public static void sendOaMsg(String access_token,String userId,String msg) {
        String url = "https://oapi.dingtalk.com/message/send?access_token="+access_token;
      String str4  = HttpUtil.sendPostJson(url,msg,"UTF-8");
      System.out.println(str4);

    }
    public static void main(String[] args) {

        try {
            System.out.println(URLEncoder.encode("{:", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
