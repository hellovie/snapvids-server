package io.github.hellovie.snapvids.infrastructure.persistence.entity;

import io.github.hellovie.snapvids.infrastructure.persistence.enums.PermissionType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * 权限表实体。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Entity(name = "Permission")
@Table(name = "permission")
public class Permission extends BaseEntity {

    /**
     * 权限编码
     */
    private String code;

    /**
     * 父权限 ID
     */
    private Long pid;

    /**
     * 权限图标（资源图标）
     */
    private String icon;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限路径（资源地址）
     */
    private String url;

    /**
     * 权限类型
     */
    @Enumerated(EnumType.ORDINAL)
    private PermissionType type;

    /**
     * 权限树路径（根节点到当前节点的父节点的路径，英文逗号分割）
     */
    private String path;

    /**
     * 权限树层次（根节点为 1）
     */
    private Integer level = 1;

    /**
     * 权限排序（同层次节点的排序号）
     */
    private Integer sort = 0;

    public Permission() {}

    public Permission(Long id) {
        super(id);
    }

    public String getCode() {
        return code;
    }

    public Permission setCode(String code) {
        this.code = code;
        return this;
    }

    public Long getPid() {
        return pid;
    }

    public Permission setPid(Long pid) {
        this.pid = pid;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public Permission setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getName() {
        return name;
    }

    public Permission setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Permission setUrl(String url) {
        this.url = url;
        return this;
    }

    public PermissionType getType() {
        return type;
    }

    public Permission setType(PermissionType type) {
        this.type = type;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Permission setPath(String path) {
        this.path = path;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public Permission setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public Integer getSort() {
        return sort;
    }

    public Permission setSort(Integer sort) {
        this.sort = sort;
        return this;
    }
}
