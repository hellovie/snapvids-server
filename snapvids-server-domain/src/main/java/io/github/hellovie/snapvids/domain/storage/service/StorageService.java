package io.github.hellovie.snapvids.domain.storage.service;

import io.github.hellovie.snapvids.domain.storage.vo.UploadToken;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileStorage;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.file.FileIdentifier;

/**
 * 文件存储服务。
 * <p>实现类的 Bean 名称来自 {@link FileStorage}。</p>
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface StorageService {

    /**
     * 生成上传凭证。
     *
     * @param fileId         文件 id
     * @param fileIdentifier 唯一文件标识
     * @return 上传凭证
     */
    UploadToken generateUploadToken(Id fileId, FileIdentifier fileIdentifier);
}
