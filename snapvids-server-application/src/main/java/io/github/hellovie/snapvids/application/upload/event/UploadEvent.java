package io.github.hellovie.snapvids.application.upload.event;

import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.common.ValueString;
import io.github.hellovie.snapvids.types.file.EffectiveFile;
import io.github.hellovie.snapvids.types.file.FileKey;

/**
 * 文件上传事件。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class UploadEvent {

    /**
     * 文件 id
     */
    private final Id fileId;

    /**
     * 文件标识
     */
    private final FileKey fileKey;

    /**
     * 令牌
     */
    private final ValueString token;

    /**
     * 开始时间
     */
    private final Long startTime;

    /**
     * 结束时间
     */
    private final Long expiredTime;

    /**
     * 当前文件哈希（用于后端校验数据是否被篡改）
     * <p>单文件上传为它本身的哈希值，分片上传为分片的哈希值。</p>
     */
    private final FileKey currentFileHash;

    /**
     * 文件
     */
    private final EffectiveFile file;

    public UploadEvent(Id fileId, FileKey fileKey, ValueString token, Long startTime, Long expiredTime,
                       FileKey currentFileHash, EffectiveFile file) {
        this.fileId = fileId;
        this.fileKey = fileKey;
        this.token = token;
        this.startTime = startTime;
        this.expiredTime = expiredTime;
        this.currentFileHash = currentFileHash;
        this.file = file;
    }

    public Id getFileId() {
        return fileId;
    }

    public FileKey getFileKey() {
        return fileKey;
    }

    public ValueString getToken() {
        return token;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public FileKey getCurrentFileHash() {
        return currentFileHash;
    }

    public EffectiveFile getFile() {
        return file;
    }
}
