package com.clancey.apollo.common.exception;


public class HttpUtilException extends Exception{

    /**
     *
     */
    private static final long serialVersionUID = 5968292526188539265L;

    public HttpUtilException(int errCode, String errMsg) {

        super("error code: " + errCode + ", error message: " + errMsg);

    }
}
