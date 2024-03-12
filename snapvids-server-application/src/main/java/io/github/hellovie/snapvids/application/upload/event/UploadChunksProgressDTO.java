package io.github.hellovie.snapvids.application.upload.event;

import io.github.hellovie.snapvids.types.file.ChunkNumber;
import io.github.hellovie.snapvids.infrastructure.service.upload.vo.UploadProgressVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件上传进度 DTO。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class UploadChunksProgressDTO {

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
    private final List<Integer> needChunkNumbers;

    public UploadChunksProgressDTO(Boolean isUploaded, Boolean hasChunks, List<Integer> needChunkNumbers) {
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

    public List<Integer> getNeedChunkNumbers() {
        return needChunkNumbers;
    }

    /**
     * 转换器
     */
    public static class Convertor {

        private Convertor() {}

        public static UploadChunksProgressDTO toUploadChunksProgressDTO(UploadProgressVO uploadProgressVO) {
            if (uploadProgressVO == null) {
                return null;
            }

            return new UploadChunksProgressDTO(
                    uploadProgressVO.getUploaded(),
                    uploadProgressVO.getHasChunks(),
                    uploadProgressVO.getNeedChunkNumbers()
                            .stream()
                            .map(ChunkNumber::getValue)
                            .collect(Collectors.toList())
            );
        }
    }
}
