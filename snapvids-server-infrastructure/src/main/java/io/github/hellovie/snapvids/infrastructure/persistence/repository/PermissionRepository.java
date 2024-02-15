package io.github.hellovie.snapvids.infrastructure.persistence.repository;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户权限仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
