package io.github.hellovie.snapvids.domain.storage.entity;

import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileExt;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileType;
import io.github.hellovie.snapvids.types.file.FileIdentifier;
import io.github.hellovie.snapvids.types.file.FilePath;
import io.github.hellovie.snapvids.types.file.FileSize;
import io.github.hellovie.snapvids.types.file.Filename;

/**
 * 文件元数据。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class FileMetadata {
    
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
    private FileIdentifier identifier;

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

    public FileMetadata(Filename originalName, Filename storageName, FileIdentifier identifier, FilePath path, 
                        FileExt ext, FileSize size, FileType type) {
        this.originalName = originalName;
        this.storageName = storageName;
        this.identifier = identifier;
        this.path = path;
        this.ext = ext;
        this.size = size;
        this.type = type;
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

    public FileIdentifier getIdentifier() {
        return identifier;
    }

    public FileMetadata setIdentifier(FileIdentifier identifier) {
        this.identifier = identifier;
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
