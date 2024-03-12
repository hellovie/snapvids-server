package io.github.hellovie.snapvids.common.enums;

/**
 * 作品权限。
 *
 * @author hellovie
 * @since 1.0.0
 */
public enum CreationPermission {

    /**
     * 无任何权限
     */
    NONE(0),

    /**
     * 禁止分享
     */
    NO_SHARE(1),

    /**
     * 禁止下载
     */
    NO_DOWNLOAD(2)

    ;

    /**
     * 位
     */
    private final int bit;

    CreationPermission(int bit) {
        this.bit = bit;
    }

    public int getBit() {
        return bit;
    }
}
