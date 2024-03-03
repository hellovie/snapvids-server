package io.github.hellovie.snapvids.application.upload.event;

import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileExt;
import io.github.hellovie.snapvids.types.file.FileKey;
import io.github.hellovie.snapvids.types.file.FilePath;
import io.github.hellovie.snapvids.types.file.FileSize;
import io.github.hellovie.snapvids.types.file.Filename;

/**
 * 初始化上传命令。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class InitUploadCommand {

    /**
     * 文件原始名
     */
    private final Filename originalName;

    /**
     * 文件唯一标识
     */
    private final FileKey fileKey;

    /**
     * 文件后缀
     */
    private final FileExt ext;

    /**
     * 文件大小（Byte）
     */
    private final FileSize size;

    /**
     * 文件路径
     */
    private final FilePath path;

    public InitUploadCommand(Filename originalName, FileKey fileKey, FileExt ext, FileSize size, FilePath path) {
        this.originalName = originalName;
        this.fileKey = fileKey;
        this.ext = ext;
        this.size = size;
        this.path = path;
    }

    public Filename getOriginalName() {
        return originalName;
    }

    public FileKey getFileKey() {
        return fileKey;
    }

    public FileExt getExt() {
        return ext;
    }

    public FileSize getSize() {
        return size;
    }

    public FilePath getPath() {
        return path;
    }
}
