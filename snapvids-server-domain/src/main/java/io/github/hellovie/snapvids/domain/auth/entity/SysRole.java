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
    private Id id;

    /**
     * 角色标识
     */
    private RoleKey roleKey;

    /**
     * 用户角色
     */
    private RoleName roleName;

    /**
     * 角色权限
     */
    private final List<SysPermission> permissions = new ArrayList<>();

    public SysRole(Id id, RoleKey roleKey, RoleName roleName) {
        this.id = id;
        this.roleKey = roleKey;
        this.roleName = roleName;
    }

    public Id getId() {
        return id;
    }

    public SysRole setId(Id id) {
        this.id = id;
        return this;
    }

    public RoleKey getRoleKey() {
        return roleKey;
    }

    public SysRole setRoleKey(RoleKey roleKey) {
        this.roleKey = roleKey;
        return this;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public SysRole setRoleName(RoleName roleName) {
        this.roleName = roleName;
        return this;
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
                "id=" + id +
                ", roleKey=" + roleKey +
                ", roleName=" + roleName +
                ", permissions=" + permissions +
                '}';
    }
}
