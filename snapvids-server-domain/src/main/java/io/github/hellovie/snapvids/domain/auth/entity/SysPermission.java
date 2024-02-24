package io.github.hellovie.snapvids.domain.auth.entity;

import io.github.hellovie.snapvids.infrastructure.persistence.enums.PermissionType;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.permission.*;

/**
 * 系统用户权限。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class SysPermission {

    /**
     * id
     */
    private final Id id;

    /**
     * 权限编码
     */
    private final PermissionCode code;

    /**
     * 父权限 ID
     */
    private final Id pid;

    /**
     * 权限图标（资源图标）
     */
    private final PermissionIcon icon;

    /**
     * 权限名称
     */
    private final PermissionName name;

    /**
     * 权限路径（资源地址）
     */
    private final PermissionUrl url;

    /**
     * 权限类型
     */
    private final PermissionType type;

    /**
     * 权限树路径（根节点到当前节点的父节点的路径，英文逗号分割）
     */
    private final PermissionPath path;

    /**
     * 权限树层次
     */
    private final PermissionLevel level;

    /**
     * 权限排序（同层次节点的排序号）
     */
    private final PermissionSort sort;

    public SysPermission(Long id, String code, Long pid, String icon, String name, String url, PermissionType type,
                         String path, Integer level, Integer sort) {

        this.id = new Id(id);
        this.code = new PermissionCode(code);
        this.pid = new Id(pid);
        this.icon = new PermissionIcon(icon);
        this.name = new PermissionName(name);
        this.url = new PermissionUrl(url);
        this.type = type;
        this.path = new PermissionPath(path);
        this.level = new PermissionLevel(level);
        this.sort = new PermissionSort(sort);
    }

    public Id getId() {
        return id;
    }

    public PermissionCode getCode() {
        return code;
    }

    public Id getPid() {
        return pid;
    }

    public PermissionIcon getIcon() {
        return icon;
    }

    public PermissionName getName() {
        return name;
    }

    public PermissionUrl getUrl() {
        return url;
    }

    public PermissionType getType() {
        return type;
    }

    public PermissionPath getPath() {
        return path;
    }

    public PermissionLevel getLevel() {
        return level;
    }

    public PermissionSort getSort() {
        return sort;
    }

    @Override
    public String toString() {
        return "SysPermission{" +
                "id=" + id.getValue() +
                ", code=" + code.getValue() +
                ", pid=" + pid.getValue() +
                ", icon=" + icon.getName() +
                ", name=" + name.getValue() +
                ", url=" + url.getValue() +
                ", type=" + type +
                ", path=" + path.getValue() +
                ", level=" + level.getValue() +
                ", sort=" + sort.getValue() +
                '}';
    }
}
