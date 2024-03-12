package io.github.hellovie.snapvids.domain.fileInfo.event;

import io.github.hellovie.snapvids.common.enums.FileState;
import io.github.hellovie.snapvids.domain.fileInfo.state.FileUpdateStateEvent;
import io.github.hellovie.snapvids.types.file.FileKey;

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
    private final FileKey fileKey;

    /**
     * 当前状态
     */
    private final FileState state;

    /**
     * 跃迁事件
     */
    private final FileUpdateStateEvent event;

    public UpdateFileStateCommand(FileKey fileKey, FileState state, FileUpdateStateEvent event) {
        this.fileKey = fileKey;
        this.state = state;
        this.event = event;
    }

    public FileKey getFileKey() {
        return fileKey;
    }

    public FileState getState() {
        return state;
    }

    public FileUpdateStateEvent getEvent() {
        return event;
    }
}
