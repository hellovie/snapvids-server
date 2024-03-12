package io.github.hellovie.snapvids.infrastructure.service.upload.vo;

import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.common.ValueString;
import io.github.hellovie.snapvids.types.file.FileKey;

/**
 * 本地文件上传凭证。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class LocalUploadToken {

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

    public LocalUploadToken(Id fileId, FileKey fileKey, ValueString token, Long startTime, Long expiredTime) {
        this.fileId = fileId;
        this.fileKey = fileKey;
        this.token = token;
        this.startTime = startTime;
        this.expiredTime = expiredTime;
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

    @Override
    public String toString() {
        return "UploadToken{" +
                "fileId=" + fileId +
                ", fileKey=" + fileKey +
                ", token=" + token +
                ", startTime=" + startTime +
                ", expiredTime=" + expiredTime +
                '}';
    }
}
