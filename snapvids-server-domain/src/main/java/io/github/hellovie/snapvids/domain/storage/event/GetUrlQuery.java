package io.github.hellovie.snapvids.domain.storage.event;

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

    public GetUrlQuery(FileIdentifier identifier) {
        this.identifier = identifier;
    }

    public FileIdentifier getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return "GetUrlQuery{" +
                "identifier=" + identifier +
                '}';
    }
}
