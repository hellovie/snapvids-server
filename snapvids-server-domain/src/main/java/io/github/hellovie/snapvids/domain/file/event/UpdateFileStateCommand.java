package io.github.hellovie.snapvids.domain.file.event;

import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileState;
import io.github.hellovie.snapvids.types.common.Id;

/**
 * 更新文件状态命令。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class UpdateFileStateCommand {

    /**
     * id
     */
    private final Id id;

    /**
     * 文件状态（默认上传中）
     */
    private final FileState state;

    public UpdateFileStateCommand(Id id, FileState state) {
        this.id = id;
        this.state = state;
    }

    public Id getId() {
        return id;
    }

    public FileState getState() {
        return state;
    }
}
