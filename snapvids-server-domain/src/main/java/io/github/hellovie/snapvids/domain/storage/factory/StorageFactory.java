package io.github.hellovie.snapvids.domain.storage.factory;

import io.github.hellovie.snapvids.common.enums.FileStorage;
import io.github.hellovie.snapvids.domain.storage.service.StorageService;

import java.util.List;

/**
 * 存储服务工厂，生产存储服务。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface StorageFactory {

    /**
     * 默认存储方式
     */
    FileStorage DEFAULT_STORAGE = FileStorage.LOCAL;

    /**
     * 获取默认存储服务。
     *
     * @return {@link StorageService}
     */
    StorageService getDefaultStorageService();

    /**
     * 获取指定存储服务。
     *
     * @param fileStorage 存储类型
     * @return {@link StorageService}
     */
    StorageService getStorageService(FileStorage fileStorage);

    /**
     * 获取所有存储服务。
     *
     * @return {@link StorageService} List
     */
    List<StorageService> getAllStorageService();
}
