package io.github.hellovie.snapvids.domain.storage.vo;

import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.common.ValueString;
import io.github.hellovie.snapvids.types.file.FileKey;
import io.github.hellovie.snapvids.infrastructure.service.upload.vo.LocalUploadToken;

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

    public UploadToken(Id fileId, FileKey fileKey, ValueString token, Long startTime, Long expiredTime) {
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

    /**
     * 转换器
     */
    public static class Convertor {

        private Convertor() {}

        public static UploadToken toUploadToken(LocalUploadToken localUploadToken) {
            if (localUploadToken == null) {
                return null;
            }

            return new UploadToken(
                    localUploadToken.getFileId(),
                    localUploadToken.getFileKey(),
                    localUploadToken.getToken(),
                    localUploadToken.getStartTime(),
                    localUploadToken.getExpiredTime()
            );
        }
    }
}
