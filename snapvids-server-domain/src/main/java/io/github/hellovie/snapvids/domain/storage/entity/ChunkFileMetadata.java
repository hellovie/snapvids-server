package io.github.hellovie.snapvids.domain.storage.entity;

import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.file.ChunkNumber;
import io.github.hellovie.snapvids.types.file.ChunkTotal;
import io.github.hellovie.snapvids.types.file.FileSize;

/**
 * 分片文件信息。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class ChunkFileMetadata {

    /**
     * id
     */
    private Id id;

    /**
     * 所属文件 ID
     */
    private Id fileId;

    /**
     * 当前分片编号（从 1 开始）
     */
    private ChunkNumber currentNum;

    /**
     * 当前分片实际大小（Byte）
     */
    private FileSize currentSize;

    /**
     * 分片大小（Byte）
     */
    private FileSize chunkSize;

    /**
     * 文件总大小（Byte）
     */
    private FileSize totalSize;

    /**
     * 文件总块数
     */
    private ChunkTotal totalChunks;

    public ChunkFileMetadata(Id fileId, ChunkNumber currentNum, FileSize currentSize, FileSize chunkSize,
                             FileSize totalSize, ChunkTotal totalChunks) {
        this.fileId = fileId;
        this.currentNum = currentNum;
        this.currentSize = currentSize;
        this.chunkSize = chunkSize;
        this.totalSize = totalSize;
        this.totalChunks = totalChunks;
    }

    public ChunkFileMetadata(Id id, Id fileId, ChunkNumber currentNum, FileSize currentSize, FileSize chunkSize,
                             FileSize totalSize, ChunkTotal totalChunks) {
        this.id = id;
        this.fileId = fileId;
        this.currentNum = currentNum;
        this.currentSize = currentSize;
        this.chunkSize = chunkSize;
        this.totalSize = totalSize;
        this.totalChunks = totalChunks;
    }

    public Id getId() {
        return id;
    }

    public ChunkFileMetadata setId(Id id) {
        this.id = id;
        return this;
    }

    public Id getFileId() {
        return fileId;
    }

    public ChunkFileMetadata setFileId(Id fileId) {
        this.fileId = fileId;
        return this;
    }

    public ChunkNumber getCurrentNum() {
        return currentNum;
    }

    public ChunkFileMetadata setCurrentNum(ChunkNumber currentNum) {
        this.currentNum = currentNum;
        return this;
    }

    public FileSize getCurrentSize() {
        return currentSize;
    }

    public ChunkFileMetadata setCurrentSize(FileSize currentSize) {
        this.currentSize = currentSize;
        return this;
    }

    public FileSize getChunkSize() {
        return chunkSize;
    }

    public ChunkFileMetadata setChunkSize(FileSize chunkSize) {
        this.chunkSize = chunkSize;
        return this;
    }

    public FileSize getTotalSize() {
        return totalSize;
    }

    public ChunkFileMetadata setTotalSize(FileSize totalSize) {
        this.totalSize = totalSize;
        return this;
    }

    public ChunkTotal getTotalChunks() {
        return totalChunks;
    }

    public ChunkFileMetadata setTotalChunks(ChunkTotal totalChunks) {
        this.totalChunks = totalChunks;
        return this;
    }
}
