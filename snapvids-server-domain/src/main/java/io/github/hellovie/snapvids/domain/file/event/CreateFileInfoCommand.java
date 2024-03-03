package io.github.hellovie.snapvids.domain.file.event;

import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileExt;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileStorage;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileType;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileVisibility;
import io.github.hellovie.snapvids.types.file.FileKey;
import io.github.hellovie.snapvids.types.file.FilePath;
import io.github.hellovie.snapvids.types.file.FileSize;
import io.github.hellovie.snapvids.types.file.Filename;

/**
 * 创建文件信息命令。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class CreateFileInfoCommand {

    /**
     * 文件原始名
     */
    private final Filename originalName;

    /**
     * 文件唯一标识
     */
    private final FileKey fileKey;

    /**
     * 相对路径
     */
    private final FilePath path;

    /**
     * 文件后缀
     */
    private final FileExt ext;

    /**
     * 文件大小（Byte）
     */
    private final FileSize size;

    /**
     * 文件类型
     */
    private final FileType type;

    /**
     * 存储方式
     */
    private final FileStorage storage;

    /**
     * 文件可见范围（默认全部可见）
     */
    private final FileVisibility visibility;

    public CreateFileInfoCommand(Filename originalName, FileKey fileKey, FilePath path, FileExt ext,
                                 FileSize size, FileType type, FileStorage storage, FileVisibility visibility) {
        this.originalName = originalName;
        this.fileKey = fileKey;
        this.path = path;
        this.ext = ext;
        this.size = size;
        this.type = type;
        this.storage = storage;
        this.visibility = visibility;
    }

    public Filename getOriginalName() {
        return originalName;
    }

    public FileKey getFileKey() {
        return fileKey;
    }

    public FilePath getPath() {
        return path;
    }

    public FileExt getExt() {
        return ext;
    }

    public FileSize getSize() {
        return size;
    }

    public FileType getType() {
        return type;
    }

    public FileStorage getStorage() {
        return storage;
    }

    public FileVisibility getVisibility() {
        return visibility;
    }
}
