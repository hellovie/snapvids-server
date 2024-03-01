package io.github.hellovie.snapvids.domain.storage.factory.impl;

import io.github.hellovie.snapvids.common.exception.system.ConfigException;
import io.github.hellovie.snapvids.common.module.file.FileExceptionType;
import io.github.hellovie.snapvids.domain.storage.annotation.StorageServiceMark;
import io.github.hellovie.snapvids.domain.storage.factory.StorageFactory;
import io.github.hellovie.snapvids.domain.storage.service.StorageService;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认存储工厂实现。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Component("storageFactory")
public class DefaultStorageFactory implements StorageFactory {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultStorageFactory.class);

    private final Map<String, StorageService> storageServiceMap = new ConcurrentHashMap<>(FileStorage.values().length);

    /**
     * 自动装配所有存储服务。
     *
     * @param services 存储服务列表
     */
    public DefaultStorageFactory(List<StorageService> services) {
        StringBuffer registerService = new StringBuffer();
        services.forEach(storageService -> {
            StorageServiceMark service = AnnotationUtils.findAnnotation(storageService.getClass(),
                    StorageServiceMark.class);
            if (null != service) {
                storageServiceMap.put(service.type().name(), storageService);
                registerService.append(service.type().name()).append(" ");
            }
        });
        LOG.info("[These storage services are already registered]>>> [{}]{}", storageServiceMap.size(), registerService);
    }

    /**
     * {@inheritDoc}
     *
     * @see StorageFactory#getDefaultStorageService()
     */
    @Override
    public StorageService getDefaultStorageService() {
        StorageService service = storageServiceMap.get(DEFAULT_STORAGE.name());
        if (service == null) {
            throw new ConfigException(FileExceptionType.DEFAULT_STORAGE_SERVICE_NOT_FOUND);
        }
        return service;
    }

    /**
     * {@inheritDoc}
     *
     * @see StorageFactory#getStorageService(FileStorage)
     */
    @Override
    public StorageService getStorageService(FileStorage fileStorage) {
        if (storageServiceMap.isEmpty()) {
            LOG.error("[Get storage service failed]>>> storageServiceMap.size=0");
            throw new ConfigException(FileExceptionType.STORAGE_SERVICE_NOT_FOUND);
        }

        if (fileStorage == null || !storageServiceMap.containsKey(fileStorage.name())) {
            LOG.error("[Get storage service failed]>>> fileStorage={}", fileStorage);
            throw new ConfigException(FileExceptionType.STORAGE_SERVICE_NOT_FOUND);
        }

        return storageServiceMap.get(fileStorage.name());
    }

    /**
     * {@inheritDoc}
     *
     * @see StorageFactory#getAllStorageService()
     */
    @Override
    public List<StorageService> getAllStorageService() {
        return new ArrayList<>(storageServiceMap.values());
    }
}
