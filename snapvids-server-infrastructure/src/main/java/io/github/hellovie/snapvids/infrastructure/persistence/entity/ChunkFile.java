package io.github.hellovie.snapvids.infrastructure.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 分片文件表实体。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Entity(name = "ChunkFile")
@Table(name = "chunk_file")
public class ChunkFile extends BaseEntity {

    /**
     * 所属文件 ID
     * <p>没必要每个文件分片都包含文件信息。</p>
     */
    private Long fileId;

    /**
     * 当前分片编号（从1开始）
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

    public ChunkFile() {}

    public ChunkFile(Long id) {
        super(id);
    }

    public Long getFileId() {
        return fileId;
    }

    public ChunkFile setFileId(Long fileId) {
        this.fileId = fileId;
        return this;
    }

    public Integer getCurrentNum() {
        return currentNum;
    }

    public ChunkFile setCurrentNum(Integer currentNum) {
        this.currentNum = currentNum;
        return this;
    }

    public Long getCurrentSize() {
        return currentSize;
    }

    public ChunkFile setCurrentSize(Long currentSize) {
        this.currentSize = currentSize;
        return this;
    }

    public Long getChunkSize() {
        return chunkSize;
    }

    public ChunkFile setChunkSize(Long chunkSize) {
        this.chunkSize = chunkSize;
        return this;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public ChunkFile setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
        return this;
    }

    public Integer getTotalChunks() {
        return totalChunks;
    }

    public ChunkFile setTotalChunks(Integer totalChunks) {
        this.totalChunks = totalChunks;
        return this;
    }
}
