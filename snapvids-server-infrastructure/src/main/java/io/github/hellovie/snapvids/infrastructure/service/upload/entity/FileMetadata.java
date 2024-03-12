package io.github.hellovie.snapvids.infrastructure.service.upload.entity;

import io.github.hellovie.snapvids.common.enums.FileExt;
import io.github.hellovie.snapvids.common.enums.FileType;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.file.FileKey;
import io.github.hellovie.snapvids.types.file.FilePath;
import io.github.hellovie.snapvids.types.file.FileSize;
import io.github.hellovie.snapvids.types.file.Filename;

/**
 * 文件元数据。
 * <p>仅查询，无需 id。</p>
 *
 * @author hellovie
 * @since 1.0.0
 */
public class FileMetadata {

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

    public FileMetadata(Id id, Filename originalName, Filename storageName, FileKey fileKey, FilePath path,
                        FileExt ext, FileSize size, FileType type) {
        this.id = id;
        this.originalName = originalName;
        this.storageName = storageName;
        this.fileKey = fileKey;
        this.path = path;
        this.ext = ext;
        this.size = size;
        this.type = type;
    }

    public Id getId() {
        return id;
    }

    public FileMetadata setId(Id id) {
        this.id = id;
        return this;
    }

    public Filename getOriginalName() {
        return originalName;
    }

    public FileMetadata setOriginalName(Filename originalName) {
        this.originalName = originalName;
        return this;
    }

    public Filename getStorageName() {
        return storageName;
    }

    public FileMetadata setStorageName(Filename storageName) {
        this.storageName = storageName;
        return this;
    }

    public FileKey getFileKey() {
        return fileKey;
    }

    public FileMetadata setFileKey(FileKey fileKey) {
        this.fileKey = fileKey;
        return this;
    }

    public FilePath getPath() {
        return path;
    }

    public FileMetadata setPath(FilePath path) {
        this.path = path;
        return this;
    }

    public FileExt getExt() {
        return ext;
    }

    public FileMetadata setExt(FileExt ext) {
        this.ext = ext;
        return this;
    }

    public FileSize getSize() {
        return size;
    }

    public FileMetadata setSize(FileSize size) {
        this.size = size;
        return this;
    }

    public FileType getType() {
        return type;
    }

    public FileMetadata setType(FileType type) {
        this.type = type;
        return this;
    }
}
