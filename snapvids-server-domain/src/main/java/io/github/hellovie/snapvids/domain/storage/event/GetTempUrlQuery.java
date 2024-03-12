package io.github.hellovie.snapvids.domain.storage.event;

import io.github.hellovie.snapvids.types.file.FileKey;

/**
 * 获取临时 URL 的查询参数。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class GetTempUrlQuery {

    /**
     * 文件唯一标识
     */
    private final FileKey fileKey;

    public GetTempUrlQuery(FileKey fileKey) {
        this.fileKey = fileKey;
    }

    public FileKey getFileKey() {
        return fileKey;
    }

    @Override
    public String toString() {
        return "GetTempUrlQuery{" +
                "fileKey=" + fileKey +
                '}';
    }
}
