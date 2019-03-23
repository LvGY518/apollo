package com.clancey.apollo.common.utils.dingding;

import com.alibaba.fastjson.JSONObject;
import com.clancey.apollo.common.utils.dingding.pojo.DingDingUserInfoVo;
import com.clancey.apollo.common.utils.dingding.pojo.DingDingUserVo;
import com.clancey.apollo.config.DingDingConfig;
import com.clancey.apollo.config.DingDingConfig;
import com.clancey.apollo.common.utils.HttpUtil;
import com.clancey.apollo.common.utils.dingding.pojo.DingDingUserInfoVo;
import com.clancey.apollo.common.utils.dingding.pojo.DingDingUserVo;

public class DingDingUtil {
    private static final Integer ERROR_CODE_OK = 0;

    /**
     * 定时任务每小时自动更新
     */
    public static String ACCESS_TOKEN;

    /**
     * 获取AccessToken
     *
     * @return
     */
    public static String getAccessToken() {
        if (ACCESS_TOKEN != null) {
            return ACCESS_TOKEN;
        }

        String url = "https://oapi.dingtalk.com/sns/gettoken?appid=" + DingDingConfig.APP_ID + "&appsecret=" + DingDingConfig.APP_SECRET;
        JSONObject result = HttpUtil.httpGet(url);
        if (checkSuccess(result)) {
            return result.getString("access_token");
        }
        return null;
    }

    /**
     * 获取用户的持久授权码
     *
     * @param tmpAuthCode 临时授权码
     * @return
     */
    public static DingDingUserVo getPersistentInfo(String tmpAuthCode) {
        String url = "https://oapi.dingtalk.com/sns/get_persistent_code?access_token=" + getAccessToken();

        JSONObject params = new JSONObject();
        params.put("tmp_auth_code", tmpAuthCode);

        JSONObject result = HttpUtil.httpPost(url, params);
        if (checkSuccess(result)) {
            DingDingUserVo vo = new DingDingUserVo();
            vo.setOpenId(result.getString("openid"));
            vo.setUnionId(result.getString("unionid"));
            vo.setPersistentCode(result.getString("persistent_code"));
            return vo;
        }
        return null;
    }

    /**
     * 获取用户的SNS_TOKEN
     *
     * @param persistentToken 持久授权码
     * @return
     */
    public static String getSnsToken(String openId, String persistentToken) {
        String url = "https://oapi.dingtalk.com/sns/get_sns_token?access_token=" + getAccessToken();
        JSONObject params = new JSONObject();
        params.put("openid", openId);
        params.put("persistent_code", persistentToken);

        JSONObject result = HttpUtil.httpPost(url, params);
        if (checkSuccess(result)) {
            return result.getString("sns_token");
        }
        return null;
    }

    /**
     * 获取用户信息
     *
     * @param snsToken 用户的SNS_TOKEN
     * @return
     */
    public static DingDingUserInfoVo getUserInfo(String snsToken) {
        String url = "https://oapi.dingtalk.com/sns/getuserinfo?sns_token=" + snsToken;
        JSONObject result = HttpUtil.httpGet(url);
        if (checkSuccess(result)) {
            JSONObject userInfo = result.getJSONObject("user_info");
            DingDingUserInfoVo vo = new DingDingUserInfoVo();
            vo.setOpenId(userInfo.getString("openid"));
            vo.setUnionId(userInfo.getString("unionid"));
            vo.setNick(userInfo.getString("nick"));
            return vo;
        }
        return null;
    }

    /**
     * 检查请求结果
     * @param jsonObject
     * @return
     */
    private static boolean checkSuccess(JSONObject jsonObject) {
        return jsonObject != null && ERROR_CODE_OK.equals(jsonObject.getInteger("errcode"));
    }
}
