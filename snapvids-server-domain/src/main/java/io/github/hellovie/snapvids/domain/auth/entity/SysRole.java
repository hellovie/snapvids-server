package io.github.hellovie.snapvids.domain.auth.entity;

import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.role.RoleKey;
import io.github.hellovie.snapvids.types.role.RoleName;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户角色。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class SysRole {

    /**
     * id
     */
    private final Id id;

    /**
     * 角色标识
     */
    private final RoleKey roleKey;

    /**
     * 用户角色
     */
    private final RoleName roleName;

    /**
     * 角色权限
     */
    private final List<SysPermission> permissions = new ArrayList<>();

    public SysRole(Long id, String roleKey, String roleName) {
        this.id = new Id(id);
        this.roleKey = new RoleKey(roleKey);
        this.roleName = new RoleName(roleName);
    }

    public Id getId() {
        return id;
    }

    public RoleKey getRoleKey() {
        return roleKey;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public List<SysPermission> getPermissions() {
        return permissions;
    }

    public void addPermission(SysPermission permission) {
        permissions.add(permission);
    }

    public void addAllPermission(List<SysPermission> sysPermissions) {
        permissions.addAll(sysPermissions);
    }

    @Override
    public String toString() {
        return "SysRole{" +
                "id=" + id.getValue() +
                ", roleKey=" + roleKey.getValue() +
                ", roleName=" + roleName.getValue() +
                ", permissions=" + permissions +
                '}';
    }
}
