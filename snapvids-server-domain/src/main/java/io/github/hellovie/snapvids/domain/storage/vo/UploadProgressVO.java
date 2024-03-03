package io.github.hellovie.snapvids.domain.storage.vo;

import io.github.hellovie.snapvids.types.file.ChunkNumber;

import java.util.List;

/**
 * 分片上传进度。
 * <ol>
 *     <li>从未上传分片</li>
 *     <li>上传一部分分片</li>
 *     <li>上传所有分片，但未合并</li>
 *     <li>已上传</li>
 * </ol>
 *
 * @author hellovie
 * @since 1.0.0
 */
public class UploadProgressVO {

    /**
     * 是否上传成功（true：成功）
     */
    private final Boolean isUploaded;

    /**
     * 是否上传过分片（true：上传过）
     */
    private final Boolean hasChunks;

    /**
     * 还需要的分片编号
     */
    private final List<ChunkNumber> needChunkNumbers;

    public UploadProgressVO(Boolean isUploaded, Boolean hasChunks, List<ChunkNumber> needChunkNumbers) {
        this.isUploaded = isUploaded;
        this.hasChunks = hasChunks;
        this.needChunkNumbers = needChunkNumbers;
    }

    public Boolean getUploaded() {
        return isUploaded;
    }

    public Boolean getHasChunks() {
        return hasChunks;
    }

    public List<ChunkNumber> getNeedChunkNumbers() {
        return needChunkNumbers;
    }
}
