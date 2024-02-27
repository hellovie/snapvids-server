package io.github.hellovie.snapvids.application.auth.dto;

/**
 * Token DTO.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class TokenDTO {

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

    public TokenDTO(String accessToken, String refreshToken, Long expiresInSeconds) {
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
}
