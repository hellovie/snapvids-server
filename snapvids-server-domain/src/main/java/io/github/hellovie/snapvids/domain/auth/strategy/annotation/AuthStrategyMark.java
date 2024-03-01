package io.github.hellovie.snapvids.domain.auth.strategy.annotation;

import io.github.hellovie.snapvids.domain.auth.strategy.AuthStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 认证策略注解。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthStrategyMark {

    /**
     * 策略类型
     *
     * @return {@link AuthStrategy.AuthType}
     */
    AuthStrategy.AuthType type();
}
