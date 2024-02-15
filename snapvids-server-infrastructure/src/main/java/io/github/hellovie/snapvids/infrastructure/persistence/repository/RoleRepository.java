package io.github.hellovie.snapvids.infrastructure.persistence.repository;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户角色仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
