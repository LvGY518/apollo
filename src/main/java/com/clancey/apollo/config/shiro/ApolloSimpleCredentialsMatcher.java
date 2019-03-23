package com.clancey.apollo.config.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ApolloSimpleCredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authToken, AuthenticationInfo info) {
        Object tokenCredentials = getCredentials(authToken);
        Object accountCredentials = getCredentials(info);
        //将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false
        return equals(tokenCredentials, accountCredentials);
    }

    @Override
    protected boolean equals(Object tokenCredentials, Object accountCredentials) {
        String tokenString = null;
        String accounString = null;
        //类型转换 authcToken中密码一般为char[]类型
        if (isByteSource(tokenCredentials) && isByteSource(accountCredentials)) {
            tokenString = new String(toBytes(tokenCredentials));
            accounString = new String(toBytes(accountCredentials));
        }else{
            tokenString = String.valueOf(tokenCredentials);
            accounString = String.valueOf(accountCredentials);
        }
        return new BCryptPasswordEncoder().matches(tokenString, accounString);
    }
}
