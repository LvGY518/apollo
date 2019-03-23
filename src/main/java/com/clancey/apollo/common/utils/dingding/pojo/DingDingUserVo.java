package com.clancey.apollo.common.utils.dingding.pojo;

/**
 * @author ChenShuai
 * @date 2018/7/27 13:58
 */
public class DingDingUserVo {
    private String openId;
    private String unionId;
    private String persistentCode;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPersistentCode() {
        return persistentCode;
    }

    public void setPersistentCode(String persistentCode) {
        this.persistentCode = persistentCode;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
