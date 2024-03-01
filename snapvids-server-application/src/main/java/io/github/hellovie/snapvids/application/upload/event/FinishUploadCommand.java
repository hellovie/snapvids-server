package io.github.hellovie.snapvids.application.upload.event;

import io.github.hellovie.snapvids.types.common.Id;

/**
 * 完成文件上传命令。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class FinishUploadCommand {

    /**
     * id
     */
    private final Id id;

    public FinishUploadCommand(Id id) {
        this.id = id;
    }

    public Id getId() {
        return id;
    }
}
