package io.github.hellovie.snapvids.domain.fileInfo.state;

/**
 * 文件状态跃迁事件。
 *
 * @author hellovie
 * @since 1.0.0
 */
public enum FileUpdateStateEvent {

    /**
     * 上传文件
     */
    UPLOAD_FILE,

    /**
     * 完成上传
     */
    COMPLETE_THE_UPLOAD,
}
