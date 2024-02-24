package io.github.hellovie.snapvids.domain.auth.enums;

import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.role.RoleKey;
import io.github.hellovie.snapvids.types.role.RoleName;

/**
 * 所有用户角色，省去从数据库查询的步骤。
 *
 * @author hellovie
 * @since 1.0.0
 */
public enum RoleType {

    /**
     * 普通用户
     */
    NORMAL_USER(1L, "NORMAL_USER", "普通用户"),

    /**
     * 创作者
     */
    CREATOR_USER(2L, "CREATOR_USER", "创作者"),

    /**
     * 言论审核员
     */
    SPEECH_AUDITOR(3L, "SPEECH_AUDITOR", "言论审核员"),

    /**
     * 内容审核员
     */
    CONTENT_AUDITOR(4L, "CONTENT_AUDITOR", "内容审核员"),

    /**
     * 内容管理员
     */
    CONTENT_ADMIN(5L, "CONTENT_ADMIN", "内容管理员"),

    /**
     * 用户管理员
     */
    USER_ADMIN(6L, "USER_ADMIN", "用户管理员"),

    /**
     * 超级管理员
     */
    SUPER_ADMIN(7L, "SUPER_ADMIN", "超级管理员"),

    ;

    /**
     * 角色 ID
     */
    private final Id id;

    /**
     * 角色标识
     */
    private final RoleKey roleKey;

    /**
     * 角色名称
     */
    private final RoleName roleName;

    RoleType(Long id, String roleKey, String roleName) {
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
}
