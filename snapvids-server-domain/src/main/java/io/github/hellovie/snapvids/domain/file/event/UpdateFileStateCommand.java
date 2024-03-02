package io.github.hellovie.snapvids.domain.file.event;

import io.github.hellovie.snapvids.domain.file.state.FileUpdateStateEvent;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileState;
import io.github.hellovie.snapvids.types.file.FileIdentifier;

/**
 * 更新文件状态命令。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class UpdateFileStateCommand {

    /**
     * 文件唯一标识
     */
    private final FileIdentifier fileIdentifier;

    /**
     * 当前状态
     */
    private final FileState state;

    /**
     * 跃迁事件
     */
    private final FileUpdateStateEvent event;

    public UpdateFileStateCommand(FileIdentifier fileIdentifier, FileState state, FileUpdateStateEvent event) {
        this.fileIdentifier = fileIdentifier;
        this.state = state;
        this.event = event;
    }

    public FileIdentifier getFileIdentifier() {
        return fileIdentifier;
    }

    public FileState getState() {
        return state;
    }

    public FileUpdateStateEvent getEvent() {
        return event;
    }
}
