package io.github.hellovie.snapvids.infrastructure.persistence.entity;

import javax.persistence.*;
import java.util.List;

/**
 * 角色表实体
 *
 * @author hellovie
 * @since 1.0.0
 */
@Entity(name = "Role")
@Table(name = "role")
public class Role extends BaseEntity {

    /**
     * 角色标识
     */
    @Column(updatable = false)
    private String roleKey;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色权限列表
     * <p>每次（保存 / 删除）角色时，会自动（保存 / 删除）角色与权限的关联（中间表）。</p>
     * <p>若需要在角色已经关联的权限上（新增 / 删除），需要将角色权限全部查出修改后再进行保存。</p>
     * <p>权限 ID 不存在时，无法新增角色。</p>
     */
    @OneToMany
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id")
    )
    private List<Permission> permissions;

    public Role() {}

    public Role(Long id) {
        super(id);
    }

    public String getRoleKey() {
        return roleKey;
    }

    public Role setRoleKey(String roleKey) {
        this.roleKey = roleKey;
        return this;
    }

    public String getName() {
        return name;
    }

    public Role setName(String name) {
        this.name = name;
        return this;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
