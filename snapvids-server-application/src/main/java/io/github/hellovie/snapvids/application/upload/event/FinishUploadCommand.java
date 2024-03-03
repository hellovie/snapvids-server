package io.github.hellovie.snapvids.application.upload.event;

import io.github.hellovie.snapvids.types.file.FileKey;

/**
 * 完成文件上传命令。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class FinishUploadCommand {

    /**
     * 文件唯一标识
     */
    private final FileKey fileKey;

    public FinishUploadCommand(FileKey fileKey) {
        this.fileKey = fileKey;
    }

    public FileKey getFileKey() {
        return fileKey;
    }
}
