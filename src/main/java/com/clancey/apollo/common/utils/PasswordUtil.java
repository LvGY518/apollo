package com.clancey.apollo.common.utils;

import com.clancey.apollo.constants.UserConstant;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author ChenShuai
 * @date 2018/8/20 17:56
 */
public class PasswordUtil {
    /**
     * 加密
     *
     * @param password
     * @return
     */
    public static String encrypt(String password) {
        return new BCryptPasswordEncoder(UserConstant.PW_ENCODER_SALT).encode(password);
    }

    /**
     * 验证密码
     *
     * @param rawPassword
     * @param encodedPassword
     * @return
     */
    public static boolean match(String rawPassword, String encodedPassword) {
        return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
    }
}
