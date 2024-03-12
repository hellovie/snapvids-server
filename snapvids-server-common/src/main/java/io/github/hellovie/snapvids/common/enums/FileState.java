package io.github.hellovie.snapvids.common.enums;

import java.util.HashSet;
import java.util.Set;

/**
 * 文件状态。
 *
 * @author hellovie
 * @since 1.0.0
 */
public enum FileState {

    /**
     * 上传中
     */
    UPLOADING,

    /**
     * 已上传
     */
    UPLOADED,

    /**
     * 已下架
     */
    OFFLINE,

    ;

    /**
     * 判断状态是否有效。
     *
     * @param state 文件状态
     * @return true：有效
     */
    public static boolean isValid(FileState state) {
        Set<String> set = new HashSet<>(1);
        set.add(FileState.OFFLINE.name());

        return !set.contains(state.name());
    }
}
