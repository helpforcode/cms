package com.example.cms.global.security;

import com.auth0.jwt.JWT;
import com.example.cms.annotation.AdminLogin;
import com.example.cms.storage.entity.User;
import com.example.cms.storage.repository.UserRepository;
import com.example.cms.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod m = (HandlerMethod) handler;
        Method method = m.getMethod();

        if (!method.isAnnotationPresent(AdminLogin.class)) {
            return true;
        }
        // else:

        if (!StringUtils.hasLength(token)) {
            log.warn("Token empty");
            throw new AuthenticationException("Authentication failed");
        }
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (Exception e) {
            log.warn("Token invalid");
            throw new AuthenticationException("Authentication failed");
        }

        User user = userRepository.findById(Integer.valueOf(userId)).orElseThrow(() -> {
            log.warn("User not found");
            return new AuthenticationException("Authentication failed");
        });

        try {
            TokenUtil.verifyToken(token, user);
        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage());
        }
        return true;
    }
}
