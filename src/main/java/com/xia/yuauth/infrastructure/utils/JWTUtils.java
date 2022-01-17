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

    /**
     * Token前缀
     */
    private static final String TOKEN_PREFIX = "Bearer ";

    private static final String SECRET_SALT = "!@#SAFA^$ASDDFsdafads123687232";

    private static final String ISSUER = "yu-auth";

    private static final int TOKEN_EXPIRED_TIME = 2 * 60 * 60 * 1000;

    public static final String USER_ID = "user_id";

    public static String getToken(@NonNull User user) {
        return getToken(user, TOKEN_EXPIRED_TIME);
    }


    public static String getToken(@NonNull User user, int mills) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_SALT);
        String token = JWT.create()
                .withIssuer(ISSUER)
                .withClaim(USER_ID, user.getUsername())
                .withJWTId(UUID.randomUUID().toString())
                .withAudience(user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(DateUtils.addMilliseconds(new Date(), mills))
                .sign(algorithm);
        return token;
    }

    public static boolean validateToken(String token) throws ExpiredJwtException {
        if (isBearerStart(token)) {
            token = token.replace(TOKEN_PREFIX, "");
        } else {
            throw new ServiceException(new Result<>().withCode("A0304"));
        }
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

    public static DecodedJWT decodeToken(String token) {
        if (isBearerStart(token)) {
            token = token.replace(TOKEN_PREFIX, "");
        } else {
            throw new ServiceException(new Result<>().withCode("A0304"));
        }
        return JWT.decode(token);
    }

    private static boolean isBearerStart(String token) {
        return token.startsWith(TOKEN_PREFIX);
    }
}
