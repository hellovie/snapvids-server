package io.github.hellovie.snapvids.infrastructure.persistence.dao;

import io.github.hellovie.snapvids.infrastructure.persistence.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户角色仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("roleDao")
public interface RoleDao extends JpaRepository<Role, Long> {
}
