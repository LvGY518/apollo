package com.clancey.apollo.common.utils;

import com.clancey.apollo.config.JwtConfig;
import com.clancey.apollo.config.JwtConfig;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ChenShuai
 * @date 2018/8/13 19:18
 */
public class JwtUtil {
    private static JwtParser parser = Jwts.parser().setSigningKey(JwtConfig.SECRET_KEY);

    /**
     * 生成token
     * @param uid
     * @return
     */
    public static String createToken(String uid) {
        long currentTimeMillis = System.currentTimeMillis();

        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", JwtConfig.ALG);
        headers.put("typ", JwtConfig.TYPE);

        Map<String, Object> claimMap = new HashMap<>();
        claimMap.put("uid", uid);
        Claims claims = Jwts.claims(claimMap);

        claims.setIssuer("alanber");
        claims.setId(UUIDUtil.randomUUID());
        claims.setExpiration(new Date(currentTimeMillis + 2 * 3600 * 1000));
        claims.setIssuedAt(new Date(currentTimeMillis));
        claims.setNotBefore(new Date(currentTimeMillis));

        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setHeader(headers);
        jwtBuilder.setClaims(claims);
        jwtBuilder.signWith(SignatureAlgorithm.HS256, JwtConfig.SECRET_KEY);
        return jwtBuilder.compact();
    }

    /**
     * 解析claims
     * @param token
     * @return
     * @throws ExpiredJwtException
     * @throws UnsupportedJwtException
     * @throws MalformedJwtException
     * @throws SignatureException
     * @throws IllegalArgumentException
     */
    public static Claims parseToken(String token) throws ExpiredJwtException, UnsupportedJwtException,
            MalformedJwtException, SignatureException, IllegalArgumentException {
        return parser.parseClaimsJws(token).getBody();
    }

    /**
     * 验证token有效性
     * @param token
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean validateToken(String token) {
        try {
            parseToken(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
