package io.github.hellovie.snapvids.interfaces.web.request;

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件请求。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class UploadRequest {

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

    /**
     * 当前文件哈希（用于后端校验数据是否被篡改）
     * <p>单文件上传为它本身的哈希值，分片上传为分片的哈希值。</p>
     */
    private String md5;

    /**
     * 文件
     */
    private MultipartFile file;

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

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
