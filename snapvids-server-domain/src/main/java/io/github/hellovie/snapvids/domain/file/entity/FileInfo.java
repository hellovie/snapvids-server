package io.github.hellovie.snapvids.domain.file.entity;

import io.github.hellovie.snapvids.infrastructure.persistence.enums.*;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.file.FileKey;
import io.github.hellovie.snapvids.types.file.FilePath;
import io.github.hellovie.snapvids.types.file.FileSize;
import io.github.hellovie.snapvids.types.file.Filename;

/**
 * 文件信息。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class FileInfo {

    /**
     * id
     */
    private Id id;

    /**
     * 文件原始名
     */
    private Filename originalName;

    /**
     * 文件存储名
     */
    private Filename storageName;

    /**
     * 文件唯一标识
     */
    private FileKey fileKey;

    /**
     * 相对路径
     */
    private FilePath path;

    /**
     * 文件后缀
     */
    private FileExt ext;

    /**
     * 文件大小（Byte）
     */
    private FileSize size;

    /**
     * 文件类型
     */
    private FileType type;

    /**
     * 存储方式
     */
    private FileStorage storage;

    /**
     * 文件状态（默认上传中）
     */
    private FileState state;

    /**
     * 文件可见范围（默认全部可见）
     */
    private FileVisibility visibility;

    /**
     * 创建者
     */
    private FileOperator createdBy;

    /**
     * 更新者
     */
    private FileOperator modifiedBy;

    public FileInfo(Filename originalName, Filename storageName, FileKey fileKey, FilePath path,
                    FileExt ext, FileSize size, FileType type, FileStorage storage, FileState state,
                    FileVisibility visibility, FileOperator createdBy, FileOperator modifiedBy) {
        this.originalName = originalName;
        this.storageName = storageName;
        this.fileKey = fileKey;
        this.path = path;
        this.ext = ext;
        this.size = size;
        this.type = type;
        this.storage = storage;
        this.state = state;
        this.visibility = visibility;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }

    public FileInfo(Id id, Filename originalName, Filename storageName, FileKey fileKey, FilePath path,
                    FileExt ext, FileSize size, FileType type, FileStorage storage, FileState state,
                    FileVisibility visibility, FileOperator createdBy, FileOperator modifiedBy) {
        this.id = id;
        this.originalName = originalName;
        this.storageName = storageName;
        this.fileKey = fileKey;
        this.path = path;
        this.ext = ext;
        this.size = size;
        this.type = type;
        this.storage = storage;
        this.state = state;
        this.visibility = visibility;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }

    public Id getId() {
        return id;
    }

    public FileInfo setId(Id id) {
        this.id = id;
        return this;
    }

    public Filename getOriginalName() {
        return originalName;
    }

    public FileInfo setOriginalName(Filename originalName) {
        this.originalName = originalName;
        return this;
    }

    public Filename getStorageName() {
        return storageName;
    }

    public FileInfo setStorageName(Filename storageName) {
        this.storageName = storageName;
        return this;
    }

    public FileKey getFileKey() {
        return fileKey;
    }

    public FileInfo setFileKey(FileKey fileKey) {
        this.fileKey = fileKey;
        return this;
    }

    public FilePath getPath() {
        return path;
    }

    public FileInfo setPath(FilePath path) {
        this.path = path;
        return this;
    }

    public FileExt getExt() {
        return ext;
    }

    public FileInfo setExt(FileExt ext) {
        this.ext = ext;
        return this;
    }

    public FileSize getSize() {
        return size;
    }

    public FileInfo setSize(FileSize size) {
        this.size = size;
        return this;
    }

    public FileType getType() {
        return type;
    }

    public FileInfo setType(FileType type) {
        this.type = type;
        return this;
    }

    public FileStorage getStorage() {
        return storage;
    }

    public FileInfo setStorage(FileStorage storage) {
        this.storage = storage;
        return this;
    }

    public FileState getState() {
        return state;
    }

    public FileInfo setState(FileState state) {
        this.state = state;
        return this;
    }

    public FileVisibility getVisibility() {
        return visibility;
    }

    public FileInfo setVisibility(FileVisibility visibility) {
        this.visibility = visibility;
        return this;
    }

    public FileOperator getCreatedBy() {
        return createdBy;
    }

    public FileInfo setCreatedBy(FileOperator createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public FileOperator getModifiedBy() {
        return modifiedBy;
    }

    public FileInfo setModifiedBy(FileOperator modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }
}
