package io.github.hellovie.snapvids.domain.storage.event;

import io.github.hellovie.snapvids.types.file.FileKey;

/**
 * 检查文件是否已上传命令。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class CheckUploadedCommand {

    /**
     * 文件唯一标识
     */
    private final FileKey fileKey;

    public CheckUploadedCommand(FileKey fileKey) {
        this.fileKey = fileKey;
    }

    public FileKey getFileKey() {
        return fileKey;
    }

    @Override
    public String toString() {
        return "CheckUploadedCommand{" +
                "fileKey=" + fileKey +
                '}';
    }
}
