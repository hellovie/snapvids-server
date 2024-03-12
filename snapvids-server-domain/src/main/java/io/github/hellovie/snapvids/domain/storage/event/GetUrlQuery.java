package io.github.hellovie.snapvids.domain.storage.event;

import io.github.hellovie.snapvids.types.common.Id;

/**
 * 获取 URL 的查询参数。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class GetUrlQuery {

    /**
     * 文件 id
     */
    private final Id fileId;

    public GetUrlQuery(Id fileId) {
        this.fileId = fileId;
    }

    public Id getFileId() {
        return fileId;
    }

    @Override
    public String toString() {
        return "GetUrlQuery{" +
                "fileId=" + fileId +
                '}';
    }
}
