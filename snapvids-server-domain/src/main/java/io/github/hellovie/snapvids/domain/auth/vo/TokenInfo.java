package io.github.hellovie.snapvids.domain.auth.vo;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.types.Verifiable;
import io.github.hellovie.snapvids.domain.auth.service.AuthService;

import static io.github.hellovie.snapvids.common.module.user.UserExceptionType.*;

/**
 * 登录令牌信息。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class TokenInfo implements Verifiable {

    /**
     * 访问令牌
     */
    private final String accessToken;

    /**
     * 刷新令牌
     */
    private final String refreshToken;

    /**
     * 刷新令牌的有效时间（单位：秒）
     */
    private final Long expiresInSeconds;

    /**
     * 构造登录令牌。
     *
     * @param accessToken      访问令牌
     * @param refreshToken     刷新令牌
     * @param expiresInSeconds 刷新令牌的有效时间（单位：秒）
     */
    public TokenInfo(String accessToken, String refreshToken, Long expiresInSeconds) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresInSeconds = expiresInSeconds;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Long getExpiresInSeconds() {
        return expiresInSeconds;
    }

    /**
     * {@inheritDoc}
     *
     * @see Verifiable#verify()
     */
    @Override
    public void verify() throws InvalidParamException {
        Validation.isNotBlankOrElseThrow(accessToken, ACCESS_TOKEN_CANNOT_BE_EMPTY);
        Validation.isNotBlankOrElseThrow(refreshToken, REFRESH_TOKEN_CANNOT_BE_EMPTY);
        Validation.inLongScopeOrElseThrow(expiresInSeconds, 1, AuthService.REFRESH_TOKEN_EXPIRED_IN_SECONDS,
                INVALID_REFRESH_TOKEN_EXPIRATION_TIME);
    }

    @Override
    public String toString() {
        return "TokenInfo{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresInSeconds=" + expiresInSeconds +
                '}';
    }
}
