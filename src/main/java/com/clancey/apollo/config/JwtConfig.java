package com.clancey.apollo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author ChenShuai
 * @date 2018/8/13 19:19
 */
@Configuration
public class JwtConfig {
    /**
     * JWT密钥
     */
    public static String ALG = "HS256";
    public static String TYPE = "JWT";
    public static String SECRET_KEY = "MzQxNSZeQCM0MzQ5ITNkc2pmJTIzNGRzZmQmNjUzNDI7NHAtKzktNHBpbzIzNHUyM2Fk";

    @Value("${jwt.alg}")
    public static void setALG(String alg) {
        ALG = alg;
    }

    @Value("${jwt.type}")
    public static void setType(String type) {
        TYPE = type;
    }

    @Value("${jwt.secret-key!MzQxNSZeQCM0MzQ5ITNkc2pmJTIzNGRzZmQmNjUzNDI7NHAtKzktNHBpbzIzNHUyM2Fk}")
    public static void setSecretKey(String secretKey) {
        SECRET_KEY = secretKey;
    }
}
