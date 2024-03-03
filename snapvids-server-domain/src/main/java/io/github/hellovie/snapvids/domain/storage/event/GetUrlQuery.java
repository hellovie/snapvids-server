package io.github.hellovie.snapvids.domain.storage.event;

import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.file.FileKey;

/**
 * 获取 URL 的查询参数。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class GetUrlQuery {

    /**
     * 文件唯一标识
     */
    private final FileKey fileKey;

    /**
     * 创建者 id
     */
    private final Id createdById;

    public GetUrlQuery(FileKey fileKey, Id createdById) {
        this.fileKey = fileKey;
        this.createdById = createdById;
    }

    public FileKey getFileKey() {
        return fileKey;
    }

    public Id getCreatedById() {
        return createdById;
    }

    @Override
    public String toString() {
        return "GetUrlQuery{" +
                "fileKey=" + fileKey +
                ", createdById=" + createdById +
                '}';
    }
}
