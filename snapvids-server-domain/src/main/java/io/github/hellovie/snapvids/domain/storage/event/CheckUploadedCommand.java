package io.github.hellovie.snapvids.domain.storage.event;

import io.github.hellovie.snapvids.types.file.FileIdentifier;

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
    private final FileIdentifier identifier;

    public CheckUploadedCommand(FileIdentifier identifier) {
        this.identifier = identifier;
    }

    public FileIdentifier getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return "CheckUploadedCommand{" +
                "identifier=" + identifier +
                '}';
    }
}
