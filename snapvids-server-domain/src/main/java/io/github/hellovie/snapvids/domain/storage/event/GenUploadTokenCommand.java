package io.github.hellovie.snapvids.domain.storage.event;

import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.file.FileKey;

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
    private final FileKey fileKey;

    public GenUploadTokenCommand(Id fileId, FileKey fileKey) {
        this.fileId = fileId;
        this.fileKey = fileKey;
    }

    public Id getFileId() {
        return fileId;
    }

    public FileKey getFileKey() {
        return fileKey;
    }
}