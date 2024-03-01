package io.github.hellovie.snapvids.domain.storage.annotation;

import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileStorage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 存储服务注解。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface StorageServiceMark {

    /**
     * 服务类型
     *
     * @return {@link FileStorage}
     */
    FileStorage type();
}
