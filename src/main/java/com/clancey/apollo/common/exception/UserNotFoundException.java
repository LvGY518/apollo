package com.clancey.apollo.common.exception;

/**
 * @author chens
 * @date 2018/11/12 16:44
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("获取不到当前登录的用户");
    }
}
