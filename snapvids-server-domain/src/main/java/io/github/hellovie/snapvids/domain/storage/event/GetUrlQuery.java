package io.github.hellovie.snapvids.domain.storage.event;

import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.file.FileIdentifier;

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
    private final FileIdentifier identifier;

    /**
     * 创建者 id
     */
    private final Id createdById;

    public GetUrlQuery(FileIdentifier identifier, Id createdById) {
        this.identifier = identifier;
        this.createdById = createdById;
    }

    public FileIdentifier getIdentifier() {
        return identifier;
    }

    public Id getCreatedById() {
        return createdById;
    }

    @Override
    public String toString() {
        return "GetUrlQuery{" +
                "identifier=" + identifier +
                ", createdById=" + createdById +
                '}';
    }
}
