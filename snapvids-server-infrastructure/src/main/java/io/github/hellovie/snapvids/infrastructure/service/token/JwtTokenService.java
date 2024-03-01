package io.github.hellovie.snapvids.infrastructure.service.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.JWTVerifier;
import io.github.hellovie.snapvids.common.exception.system.UtilException;
import io.github.hellovie.snapvids.common.module.common.CommonExceptionType;
import io.github.hellovie.snapvids.common.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT Token 令牌服务（生成，验证）。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Component("jwtTokenService")
public class JwtTokenService implements TokenService {

    private static final Logger LOG = LoggerFactory.getLogger(JwtTokenService.class);

    /**
     * {@inheritDoc}
     *
     * @see TokenService#create(Map, long, String)
     */
    @Override
    public String create(final Map<String, String> payload, final long expiredInSeconds, final String secret)
            throws UtilException {

        try {
            Date expireDate = new Date(System.currentTimeMillis() + expiredInSeconds * 1000);
            JWTCreator.Builder builder = JWT.create();
            for (String key : payload.keySet()) {
                builder.withClaim(key, payload.get(key));
            }
            builder.withExpiresAt(expireDate);

            String token = builder.sign(Algorithm.HMAC512(secret));
            LOG.info("[Create JWT token success]>>> token={}, expiredInSeconds={}s", token, expiredInSeconds);
            return token;

        } catch (Exception ex) {
            LOG.error("[Create JWT token failed]>>> payload={}, expiredInSeconds={}", payload, expiredInSeconds);
            throw new UtilException(CommonExceptionType.CREATE_JWT_TOKEN_FAILED, ex);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see TokenService#verify(String, String)
     */
    @Override
    public boolean verify(final String secret, final String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(secret)).build();
            jwtVerifier.verify(token);

        } catch (Exception ex) {
            LOG.warn("[Validation JWT token failed]>>> message={}, token={}", ex.getMessage(), token);
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @see TokenService#getPayload(String, String)
     */
    @Override
    public Map<String, String> getPayload(final String secret, final String token) throws UtilException {
        final Map<String, String> payload = new HashMap<>();
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(secret)).build();
            Map<String, Claim> claims = jwtVerifier.verify(token).getClaims();
            for (String key : claims.keySet()) {
                // 过期时间获取
                if ("exp".equals(key)) {
                    payload.put(key, String.valueOf(claims.get(key).asDate().getTime()));
                    continue;
                }

                payload.put(key, claims.get(key).asString());
            }
            LOG.info("[Get JWT token payload success]>>> payload={}", payload);

        } catch (Exception ex) {
            LOG.info("[Get JWT token payload failed]>>> token={}", token);
            throw new UtilException(CommonExceptionType.DECRYPT_JWT_TOKEN_FAILED, ex);
        }

        return payload;
    }
}
