package io.github.hellovie.snapvids.infrastructure.persistence.dao;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户权限仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("permissionDao")
public interface PermissionDao extends JpaRepository<Permission, Long> {
}
