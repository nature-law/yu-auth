package com.xia.yuauth.infrastructure.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.xia.yuauth.domain.model.user.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class JWTUtilsTest {

    @Test
    void getToken() {
        User user = Mockito.mock(User.class);
        System.out.println(JWTUtils.getToken(user));
    }

    @Test
    void decodeToken() {
        User user = Mockito.mock(User.class);
        String token = JWTUtils.getToken(user);
        JWTUtils.validateToken(token);
        System.out.println();
    }


}