package io.github.hellovie.snapvids.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Jpa Configuration.
 *
 * @author hellovie
 * @since 1.0.0
 */
@Configuration
@EnableJpaRepositories(basePackages = {"io.github.hellovie.snapvids.infrastructure.persistence.dao"})
@EntityScan(basePackages = {"io.github.hellovie.snapvids.infrastructure.persistence.entity"})
public class JpaConfig {
}
