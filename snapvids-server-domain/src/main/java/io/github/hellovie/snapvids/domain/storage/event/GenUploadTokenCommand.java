package io.github.hellovie.snapvids.domain.storage.event;

import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.file.FileIdentifier;

/**
 * 生成上传令牌命令。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class GenUploadTokenCommand {

    /**
     * 文件 id
     */
    private final Id fileId;

    /**
     * 文件唯一标识
     */
    private final FileIdentifier fileIdentifier;

    public GenUploadTokenCommand(Id fileId, FileIdentifier fileIdentifier) {
        this.fileId = fileId;
        this.fileIdentifier = fileIdentifier;
    }

    public Id getFileId() {
        return fileId;
    }

    public FileIdentifier getFileIdentifier() {
        return fileIdentifier;
    }
}