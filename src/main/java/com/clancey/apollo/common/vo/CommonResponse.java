package com.clancey.apollo.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

/**
 * @author ChenShuai
 * @date 2018/8/14 17:27
 */
@Getter
public class CommonResponse {
    /**
     * 正常返回码
     */
    private final static String ERROR_OK = "000000";

    /**
     * 错误码
     */
    private String errcode;

    /**
     * 错误信息
     */
    private String errmsg;

    /**
     * 数据
     */
    private Object data;

    /**
     * 分页时总条数
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long total;

    public CommonResponse(String errcode, String errmsg, Object data) {
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.data = data;
    }

    public CommonResponse(String errcode, String errmsg, Object data, Long total) {
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.data = data;
        this.total = total;
    }

    public static CommonResponse success() {
        return new CommonResponse(ERROR_OK, "ok", null);
    }

    public static CommonResponse success(Object data) {
        return new CommonResponse(ERROR_OK, "ok", data);
    }

    public static CommonResponse success(long total, Object data) {
        return new CommonResponse(ERROR_OK, "ok", data, total);
    }

    public static CommonResponse success(String msg, Object data) {
        return new CommonResponse(ERROR_OK, msg, data);
    }

    public static CommonResponse fail(String errcode, String errmsg) {
        return fail(errcode, errmsg, null);
    }

    public static CommonResponse fail(String errcode, String errmsg, Object data) {
        return new CommonResponse(errcode, errmsg, data);
    }
}
