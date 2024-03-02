package io.github.hellovie.snapvids.domain.storage.vo;

import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.common.ValueString;
import io.github.hellovie.snapvids.types.file.FileIdentifier;

/**
 * 文件上传凭证。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class UploadToken {

    /**
     * 文件 id
     */
    private final Id fileId;

    /**
     * 文件标识
     */
    private final FileIdentifier identifier;

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

    public UploadToken(Id fileId, FileIdentifier identifier, ValueString token, Long startTime, Long expiredTime) {
        this.fileId = fileId;
        this.identifier = identifier;
        this.token = token;
        this.startTime = startTime;
        this.expiredTime = expiredTime;
    }

    public Id getFileId() {
        return fileId;
    }

    public FileIdentifier getIdentifier() {
        return identifier;
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
                ", identifier=" + identifier +
                ", token=" + token +
                ", startTime=" + startTime +
                ", expiredTime=" + expiredTime +
                '}';
    }
}
