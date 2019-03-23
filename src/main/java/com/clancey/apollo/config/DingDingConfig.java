package com.clancey.apollo.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author ChenShuai
 * @date 2018/7/26 15:49
 */
@Configuration
public class DingDingConfig implements EnvironmentAware {
    /**
     * 钉钉appId
     */
    public static String APP_ID;

    /**
     * 钉钉appSecret
     */
    public static String APP_SECRET;

    /**
     * 钉钉应用corpid
     */
    public static String CORP_ID;

    /**
     * 钉钉应用secret
     */
    public static String CORP_SECRET;

    /**
     * 钉钉应用ID
     */
    public static String AGENT_ID;

    /**
     * 钉钉应用ID
     */
    public static String AGENT_ID_APPROVE;

    /**
     * 钉钉302的url
     */
    public static String URL;

    /**
     * URL
     */
    public static String GAIA_URL;

    /**
     * 回调解密token
     */
    public static String CALL_BACK_TOKEN;

    @Override
    public void setEnvironment(Environment environment) {
        APP_ID = environment.getProperty("dingding.appId");
        APP_SECRET = environment.getProperty("dingding.appSecret");
        CORP_ID = environment.getProperty("dingding.corpId");
        CORP_SECRET = environment.getProperty("dingding.corpSecret");
        AGENT_ID = environment.getProperty("dingding.agentId");
        AGENT_ID_APPROVE = environment.getProperty("dingding.agentIdApprove");
        URL = environment.getProperty("dingding.url");
        GAIA_URL = environment.getProperty("dingding.apolloUrl");
        CALL_BACK_TOKEN = environment.getProperty("dingding.callBackToken");
    }
}
