package com.example.cms.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.cms.storage.entity.User;

import java.util.Date;

public class TokenUtil {

    public static String getToken(User user) {
        return JWT.create().withAudience(user.getId().toString())
                .withExpiresAt(DateUtil.offsetDay(new Date(), 1))
                .sign(Algorithm.HMAC256(user.getPassword()));
    }

    public static void verifyToken(String token, User user) {

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();

        jwtVerifier.verify(token);
    }

    public static String pwdEncrypt(String username, String password) {
        return SecureUtil.sha1(String.format("%s%s%s", username, salt, password));
    }
    private static final String salt = "encryptor";
}
