package com.xia.yuauth.infrastructure.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xia.yuauth.common.exception.ServiceException;
import com.xia.yuauth.controller.web.vo.Result;
import com.xia.yuauth.domain.model.user.User;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.UUID;

/**
 * description:JWT
 *
 * @author wanghaoxin
 * date     2021/12/20 23:07
 * @version 1.0
 */
public class JWTUtils {

    private static final String SECRET_SALT = "!@#SAFA^$ASDDFsdafads123687232";

    private static final String ISSUER = "yu-auth";

    private static final int TOKEN_EXPIRED_TIME_OF_HOURS = 2;

    private static final String USER_ID = "user_id";

    public static String getToken(@NonNull User user) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_SALT);
        String token = JWT.create()
                .withIssuer(ISSUER)
                .withClaim(USER_ID, user.getId())
                .withJWTId(UUID.randomUUID().toString())
                .withAudience(user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(DateUtils.addHours(new Date(), TOKEN_EXPIRED_TIME_OF_HOURS))
                .sign(algorithm);
        return token;
    }

    public static boolean validateToken(String token) throws ExpiredJwtException {
        DecodedJWT decodedJWT = JWT.decode(token);

        if (!StringUtils.equals(decodedJWT.getIssuer(), ISSUER)) {
            throw new ServiceException(new Result<>().withCode("A0340"));
        }
        Date expiresAt = decodedJWT.getExpiresAt();
        if (!expiresAt.after(new Date())) {
            throw new ServiceException(new Result<>().withCode("A0311"));
        }
        return true;
    }
}
