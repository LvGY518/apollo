package com.clancey.apollo.common.vo;

/**
 * @author bill
 *
 */
public class Result {

    /**
     * 返回状态码
     */
    private String code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 结果集
     */
    private Object data;


    public Result() {

        this.message = "SUCCESS";
        this.code = "00000";
    }

    public Result(String code,String message,Object data) {

        this.data = data;
        this.message = message;
        this.code = code;
    }


    public Result(String code, String message) {

        this.code = code;
        this.message = message;
    }


    public String getCode() {

        return code;
    }

    public void setCode(String code) {

        this.code = code;
    }


    public Object getData() {

        return data;
    }

    public void setData(Object data) {

        this.data = data;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

}
