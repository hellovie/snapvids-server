package io.github.hellovie.snapvids.application.upload.event;

import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileExt;
import io.github.hellovie.snapvids.types.file.FileIdentifier;
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
    private final FileIdentifier identifier;

    /**
     * 文件后缀
     */
    private final FileExt ext;

    /**
     * 文件大小（Byte）
     */
    private final FileSize size;

    public InitUploadCommand(Filename originalName, FileIdentifier identifier, FileExt ext, FileSize size) {
        this.originalName = originalName;
        this.identifier = identifier;
        this.ext = ext;
        this.size = size;
    }

    public Filename getOriginalName() {
        return originalName;
    }

    public FileIdentifier getIdentifier() {
        return identifier;
    }

    public FileExt getExt() {
        return ext;
    }

    public FileSize getSize() {
        return size;
    }
}
