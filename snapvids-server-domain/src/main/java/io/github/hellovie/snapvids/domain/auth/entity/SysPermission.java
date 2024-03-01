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
    private Id id;

    /**
     * 权限编码
     */
    private PermissionCode code;

    /**
     * 父权限 ID
     */
    private Id pid;

    /**
     * 权限图标（资源图标）
     */
    private PermissionIcon icon;

    /**
     * 权限名称
     */
    private PermissionName name;

    /**
     * 权限路径（资源地址）
     */
    private PermissionUrl url;

    /**
     * 权限类型
     */
    private PermissionType type;

    /**
     * 权限树路径（根节点到当前节点的父节点的路径，英文逗号分割）
     */
    private PermissionPath path;

    /**
     * 权限树层次
     */
    private PermissionLevel level;

    /**
     * 权限排序（同层次节点的排序号）
     */
    private PermissionSort sort;

    public SysPermission(Id id, PermissionCode code, Id pid, PermissionIcon icon, PermissionName name,
                         PermissionUrl url, PermissionType type, PermissionPath path, PermissionLevel level,
                         PermissionSort sort) {
        this.id = id;
        this.code = code;
        this.pid = pid;
        this.icon = icon;
        this.name = name;
        this.url = url;
        this.type = type;
        this.path = path;
        this.level = level;
        this.sort = sort;
    }

    public Id getId() {
        return id;
    }

    public SysPermission setId(Id id) {
        this.id = id;
        return this;
    }

    public PermissionCode getCode() {
        return code;
    }

    public SysPermission setCode(PermissionCode code) {
        this.code = code;
        return this;
    }

    public Id getPid() {
        return pid;
    }

    public SysPermission setPid(Id pid) {
        this.pid = pid;
        return this;
    }

    public PermissionIcon getIcon() {
        return icon;
    }

    public SysPermission setIcon(PermissionIcon icon) {
        this.icon = icon;
        return this;
    }

    public PermissionName getName() {
        return name;
    }

    public SysPermission setName(PermissionName name) {
        this.name = name;
        return this;
    }

    public PermissionUrl getUrl() {
        return url;
    }

    public SysPermission setUrl(PermissionUrl url) {
        this.url = url;
        return this;
    }

    public PermissionType getType() {
        return type;
    }

    public SysPermission setType(PermissionType type) {
        this.type = type;
        return this;
    }

    public PermissionPath getPath() {
        return path;
    }

    public SysPermission setPath(PermissionPath path) {
        this.path = path;
        return this;
    }

    public PermissionLevel getLevel() {
        return level;
    }

    public SysPermission setLevel(PermissionLevel level) {
        this.level = level;
        return this;
    }

    public PermissionSort getSort() {
        return sort;
    }

    public SysPermission setSort(PermissionSort sort) {
        this.sort = sort;
        return this;
    }

    @Override
    public String toString() {
        return "SysPermission{" +
                "id=" + id +
                ", code=" + code +
                ", pid=" + pid +
                ", icon=" + icon +
                ", name=" + name +
                ", url=" + url +
                ", type=" + type +
                ", path=" + path +
                ", level=" + level +
                ", sort=" + sort +
                '}';
    }
}
