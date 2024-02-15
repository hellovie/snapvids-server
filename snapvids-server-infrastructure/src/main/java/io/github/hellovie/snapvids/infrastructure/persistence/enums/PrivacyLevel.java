package io.github.hellovie.snapvids.infrastructure.persistence.enums;

/**
 * 用户信息私密级别。
 *
 * @author hellovie
 * @since 1.0.0
 */
public enum PrivacyLevel {

    /**
     * 全展示
     */
    ALL(0),

    /**
     * 私密用户（全不展示）
     */
    PRIVATE(1),

    /**
     * 展示用户信息
     */
    INFO(2),

    /**
     * 展示作品
     */
    CREATIONS(4),

    /**
     * 展示喜欢
     */
    LIKES(8),

    /**
     * 展示收藏
     */
    COLLECTIONS(16),

    ;

    /**
     * 位
     */
    private final int bit;

    PrivacyLevel(int bit) {
        this.bit = bit;
    }

    public int getBit() {
        return bit;
    }
}
