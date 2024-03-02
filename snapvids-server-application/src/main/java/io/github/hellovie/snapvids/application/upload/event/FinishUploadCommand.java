package io.github.hellovie.snapvids.application.upload.event;

import io.github.hellovie.snapvids.types.file.FileIdentifier;

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
    private final FileIdentifier fileIdentifier;

    public FinishUploadCommand(FileIdentifier fileIdentifier) {
        this.fileIdentifier = fileIdentifier;
    }

    public FileIdentifier getFileIdentifier() {
        return fileIdentifier;
    }
}
