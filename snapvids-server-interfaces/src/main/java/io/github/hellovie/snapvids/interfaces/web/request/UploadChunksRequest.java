package io.github.hellovie.snapvids.interfaces.web.request;

import org.springframework.web.multipart.MultipartFile;

/**
 * 分片上传请求。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class UploadChunksRequest {

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
     * 当前分片编号（从 1 开始）
     */
    private Integer currentNum;

    /**
     * 当前分片实际大小（Byte）
     */
    private Long currentSize;

    /**
     * 分片大小（Byte）
     */
    private Long chunkSize;

    /**
     * 文件总大小（Byte）
     */
    private Long totalSize;

    /**
     * 文件总块数
     */
    private Integer totalChunks;

    /**
     * 分片文件哈希
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

    public Integer getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(Integer currentNum) {
        this.currentNum = currentNum;
    }

    public Long getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(Long currentSize) {
        this.currentSize = currentSize;
    }

    public Long getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(Long chunkSize) {
        this.chunkSize = chunkSize;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public Integer getTotalChunks() {
        return totalChunks;
    }

    public void setTotalChunks(Integer totalChunks) {
        this.totalChunks = totalChunks;
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
