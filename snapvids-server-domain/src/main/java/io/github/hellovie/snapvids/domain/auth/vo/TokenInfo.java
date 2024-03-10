package io.github.hellovie.snapvids.domain.auth.vo;

import io.github.hellovie.snapvids.common.exception.system.CodeException;
import io.github.hellovie.snapvids.domain.auth.service.AuthService;
import org.apache.commons.lang3.StringUtils;

import static io.github.hellovie.snapvids.common.module.user.UserExceptionType.*;

/**
 * 登录令牌信息。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class TokenInfo {

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
    public TokenInfo(String accessToken, String refreshToken, long expiresInSeconds) {
        if (StringUtils.isBlank(accessToken)) {
            throw new CodeException(ACCESS_TOKEN_CANNOT_BE_EMPTY);
        }
        if (StringUtils.isBlank(refreshToken)) {
            throw new CodeException(REFRESH_TOKEN_CANNOT_BE_EMPTY);
        }
        if (expiresInSeconds < 1 || expiresInSeconds > AuthService.REFRESH_TOKEN_EXPIRED_IN_SECONDS) {
            throw new CodeException(INVALID_REFRESH_TOKEN_EXPIRATION_TIME);
        }

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

    @Override
    public String toString() {
        return "TokenInfo{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresInSeconds=" + expiresInSeconds +
                '}';
    }
}
