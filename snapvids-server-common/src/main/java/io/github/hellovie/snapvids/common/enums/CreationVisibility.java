package io.github.hellovie.snapvids.common.enums;

/**
 * 作品可见范围。
 *
 * @author hellovie
 * @since 1.0.0
 */
public enum CreationVisibility {

    /**
     * 所有可见
     */
    ALL,

    /**
     * 仅自己可见
     */
    PRIVATE,

    /**
     * 仅关注可见
     */
    FOLLOW,

    /**
     * 仅朋友可见（互关）
     */
    FRIEND,

    /**
     * 部分可见
     */
    PARTIALLY_VISIBLE,

    /**
     * 不给谁看
     */
    WHO_IS_NOT_VISIBLE
}
