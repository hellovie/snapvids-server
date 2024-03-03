package io.github.hellovie.snapvids.infrastructure.persistence.entity;

import io.github.hellovie.snapvids.infrastructure.persistence.enums.*;

import javax.persistence.*;

/**
 * 文件表实体。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Entity(name = "File")
@Table(name = "file")
public class File extends AuditEntity {

    /**
     * 文件原始名
     */
    private String originalName;

    /**
     * 文件存储名
     */
    private String storageName;

    /**
     * 文件唯一标识
     */
    @Column(updatable = false)
    private String fileKey;

    /**
     * 相对路径
     */
    private String path;

    /**
     * 文件后缀
     */
    @Enumerated(EnumType.STRING)
    private FileExt ext;

    /**
     * 文件大小（Byte）
     */
    private Long size;

    /**
     * 文件类型
     */
    @Enumerated(EnumType.ORDINAL)
    private FileType type;

    /**
     * 存储方式
     */
    @Enumerated(EnumType.ORDINAL)
    private FileStorage storage;

    /**
     * 文件状态（默认上传中）
     */
    @Enumerated(EnumType.ORDINAL)
    private FileState state;

    /**
     * 文件可见范围（默认全部可见）
     */
    @Enumerated(EnumType.ORDINAL)
    private FileVisibility visibility;

    /**
     * 创建者 ID
     * <p>使用创建者 ID 去查询 {@link UserInfo}。</p>
     */
    private Long createdById;

    /**
     * 更新者 ID
     * <p>使用更新者 ID 去查询 {@link UserInfo}。</p>
     */
    private Long modifiedById;

    public File() {}

    public File(Long id) {
        super(id);
    }

    public String getOriginalName() {
        return originalName;
    }

    public File setOriginalName(String originalName) {
        this.originalName = originalName;
        return this;
    }

    public String getStorageName() {
        return storageName;
    }

    public File setStorageName(String storageName) {
        this.storageName = storageName;
        return this;
    }

    public String getFileKey() {
        return fileKey;
    }

    public File setFileKey(String fileKey) {
        this.fileKey = fileKey;
        return this;
    }

    public String getPath() {
        return path;
    }

    public File setPath(String path) {
        this.path = path;
        return this;
    }

    public FileExt getExt() {
        return ext;
    }

    public File setExt(FileExt ext) {
        this.ext = ext;
        return this;
    }

    public Long getSize() {
        return size;
    }

    public File setSize(Long size) {
        this.size = size;
        return this;
    }

    public FileType getType() {
        return type;
    }

    public File setType(FileType type) {
        this.type = type;
        return this;
    }

    public FileStorage getStorage() {
        return storage;
    }

    public File setStorage(FileStorage storage) {
        this.storage = storage;
        return this;
    }

    public FileState getState() {
        return state;
    }

    public File setState(FileState state) {
        this.state = state;
        return this;
    }

    public FileVisibility getVisibility() {
        return visibility;
    }

    public File setVisibility(FileVisibility visibility) {
        this.visibility = visibility;
        return this;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public File setCreatedById(Long createdById) {
        this.createdById = createdById;
        return this;
    }

    public Long getModifiedById() {
        return modifiedById;
    }

    public File setModifiedById(Long modifiedById) {
        this.modifiedById = modifiedById;
        return this;
    }
}
