package com.clancey.apollo.common.utils.captcha;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ChenShuai
 * @date 2018/7/31 15:29
 */
public abstract class ReCaptcha {
    protected String type;
    protected Integer width;
    protected Integer height;
    protected String errorMessage;

    public ReCaptcha(String type, Integer width, Integer height, String errorMessage) {
        this.type = type;
        this.width = width;
        this.height = height;
        this.errorMessage = errorMessage;
    }

    /**
     * 获取验证码信息
     *
     * @return
     */
    public Map<String, Object> getData() {
        Map<String, Object> reCaptcha = new HashMap<>();
        reCaptcha.put("type", type);
        reCaptcha.put("width", width);
        reCaptcha.put("height", height);
        reCaptcha.putAll(imageBase64());
        return reCaptcha;
    }

    /**
     * 返回验证码图片的base64编码
     *
     * @return
     */
    protected abstract Map<String, Object> imageBase64();

    /**
     * 检查前端数据是否合法
     *
     * @param checkData
     * @return
     */
    public abstract Boolean checkValid(String checkData);

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
