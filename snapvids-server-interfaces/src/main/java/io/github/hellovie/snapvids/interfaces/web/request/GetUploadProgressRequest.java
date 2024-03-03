package io.github.hellovie.snapvids.interfaces.web.request;

/**
 * 获取上传进度请求。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class GetUploadProgressRequest {

    /**
     * 文件 id
     */
    private Long fileId;

    /**
     * 文件标识
     */
    private String fileKey;

    /**
     * 令牌
     */
    private String token;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long expiredTime;

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }
}
