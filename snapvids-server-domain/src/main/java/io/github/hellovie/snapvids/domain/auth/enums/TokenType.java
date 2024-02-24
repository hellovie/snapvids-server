package io.github.hellovie.snapvids.domain.auth.enums;

/**
 * Token 令牌类型。
 *
 * @author hellovie
 * @since 1.0.0
 */
public enum TokenType {

    /**
     * 访问令牌
     */
    ACCESS_TOKEN("access-token"),

    /**
     * 刷新令牌
     */
    REFRESH_TOKEN("refresh-token");

    /**
     * Token Key
     */
    private final String key;

    TokenType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
