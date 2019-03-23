package com.clancey.apollo.common.base;

import com.clancey.apollo.common.vo.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;


public class BaseController<Service extends BaseService, Entity extends BaseEntity> {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected Service baseService;

    @ApiIgnore
    public CommonResponse success() {
        return success("ok");
    }

    @ApiIgnore
    public CommonResponse success(Object data) {
        return CommonResponse.success(data);
    }

    @ApiIgnore
    public CommonResponse success(String msg, Object data) {
        return CommonResponse.success(msg, data);
    }

    @ApiIgnore
    public CommonResponse success(Long total, Object data) {
        return CommonResponse.success(total, data);
    }

    @ApiIgnore
    public CommonResponse fail(String errcode, String errmsg) {
        return CommonResponse.fail(errcode, errmsg, null);
    }

    @ApiIgnore
    public CommonResponse fail(String errcode, String errmsg, Object data) {
        return new CommonResponse(errcode, errmsg, data);
    }
}
