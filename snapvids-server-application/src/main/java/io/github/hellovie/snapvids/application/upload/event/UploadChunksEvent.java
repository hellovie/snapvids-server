package io.github.hellovie.snapvids.application.upload.event;

import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.common.ValueString;
import io.github.hellovie.snapvids.types.file.ChunkNumber;
import io.github.hellovie.snapvids.types.file.ChunkTotal;
import io.github.hellovie.snapvids.types.file.FileKey;
import io.github.hellovie.snapvids.types.file.FileSize;
import org.springframework.web.multipart.MultipartFile;

/**
 * 分片上传事件。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class UploadChunksEvent {

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
     * 当前分片编号（从 1 开始）
     */
    private final ChunkNumber currentNum;

    /**
     * 当前分片实际大小（Byte）
     */
    private final FileSize currentSize;

    /**
     * 分片大小（Byte）
     */
    private final FileSize chunkSize;

    /**
     * 文件总大小（Byte）
     */
    private final FileSize totalSize;

    /**
     * 文件总块数
     */
    private final ChunkTotal totalChunks;

    /**
     * 分片文件哈希
     */
    private final FileKey chunkHash;

    /**
     * 文件
     */
    private final MultipartFile file;

    public UploadChunksEvent(Id fileId, FileKey fileKey, ValueString token, Long startTime, Long expiredTime,
                             ChunkNumber currentNum, FileSize currentSize, FileSize chunkSize, FileSize totalSize,
                             ChunkTotal totalChunks, FileKey chunkHash, MultipartFile file) {
        this.fileId = fileId;
        this.fileKey = fileKey;
        this.token = token;
        this.startTime = startTime;
        this.expiredTime = expiredTime;
        this.currentNum = currentNum;
        this.currentSize = currentSize;
        this.chunkSize = chunkSize;
        this.totalSize = totalSize;
        this.totalChunks = totalChunks;
        this.chunkHash = chunkHash;
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

    public ChunkNumber getCurrentNum() {
        return currentNum;
    }

    public FileSize getCurrentSize() {
        return currentSize;
    }

    public FileSize getChunkSize() {
        return chunkSize;
    }

    public FileSize getTotalSize() {
        return totalSize;
    }

    public ChunkTotal getTotalChunks() {
        return totalChunks;
    }

    public FileKey getChunkHash() {
        return chunkHash;
    }

    public MultipartFile getFile() {
        return file;
    }
}
