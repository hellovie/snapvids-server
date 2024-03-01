package io.github.hellovie.snapvids.application.upload.dto;

import io.github.hellovie.snapvids.domain.storage.vo.UploadToken;

import java.util.Date;

/**
 * 上传文件凭证 DTO。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class UploadTokenDTO {

    /**
     * 文件 id
     */
    private final Long fileId;

    /**
     * 文件标识
     */
    private final String identifier;

    /**
     * 令牌
     */
    private final String token;

    /**
     * 开始时间
     */
    private final Date startTime;

    /**
     * 结束时间
     */
    private final Date expiredTime;

    public UploadTokenDTO(Long fileId, String identifier, String token, Date startTime, Date expiredTime) {
        this.fileId = fileId;
        this.identifier = identifier;
        this.token = token;
        this.startTime = startTime;
        this.expiredTime = expiredTime;
    }

    public Long getFileId() {
        return fileId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getToken() {
        return token;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    /**
     * 转换器
     */
    public static class Convertor {

        private Convertor() {}

        public static UploadTokenDTO toUploadTokenDTO(UploadToken uploadToken) {
            if (uploadToken == null) {
                return null;
            }

            long fileId = uploadToken.getFileId() != null ? uploadToken.getFileId().getValue() : 0L;
            String identifier = uploadToken.getIdentifier() != null ? uploadToken.getIdentifier().getValue()
                    : "no identifier";
            String token = uploadToken.getToken() != null ? uploadToken.getToken().getValue() : "no token";
            return new UploadTokenDTO(
                    fileId,
                    identifier,
                    token,
                    uploadToken.getStartTime(),
                    uploadToken.getExpiredTime()
            );
        }
    }
}
