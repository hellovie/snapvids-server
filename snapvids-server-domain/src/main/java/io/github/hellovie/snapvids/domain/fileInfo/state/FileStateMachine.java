package io.github.hellovie.snapvids.domain.fileInfo.state;

import io.github.hellovie.snapvids.common.enums.FileState;
import io.github.hellovie.snapvids.common.exception.system.CodeException;
import io.github.hellovie.snapvids.common.module.file.FileExceptionType;

/**
 * 文件状态机。
 * <ul>
 *     <li>状态：{@link FileState}。</li>
 *     <li>事件：{@link FileUpdateStateEvent}</li>
 * </ul>
 *
 * @author hellovie
 * @since 1.0.0
 */
public enum FileStateMachine {

    /**
     * (初始态) - 上传文件 -> [上传中]
     */
    UPLOAD_FILE(null, FileUpdateStateEvent.UPLOAD_FILE, FileState.UPLOADING),

    /**
     * [上传中] - 完成上传 -> [已上传]
     */
    COMPLETE_THE_UPLOAD(FileState.UPLOADING, FileUpdateStateEvent.COMPLETE_THE_UPLOAD, FileState.UPLOADED),

    ;

    /**
     * 当前状态
     */
    private final FileState currentState;

    /**
     * 跃迁动作
     */
    private final FileUpdateStateEvent event;

    /**
     * 下一状态
     */
    private final FileState nextState;

    /**
     * 状态跃迁。
     *
     * @param currentState 当前状态
     * @param event        跃迁动作
     * @return 下一状态，无下一状态返回 null
     * @throws CodeException 状态跃迁表找不到跃迁映射抛出
     */
    public static FileState transform(FileState currentState, FileUpdateStateEvent event) throws CodeException {
        for (FileStateMachine value : FileStateMachine.values()) {
            if (value.currentState == currentState && value.event == event) {
                return value.nextState;
            }
        }
        throw new CodeException(FileExceptionType.UPDATE_FILE_STATE_FAILED);
    }

    FileStateMachine(FileState currentState, FileUpdateStateEvent event, FileState nextState) {
        this.currentState = currentState;
        this.event = event;
        this.nextState = nextState;
    }

    public FileState getCurrentState() {
        return currentState;
    }

    public FileUpdateStateEvent getEvent() {
        return event;
    }

    public FileState getNextState() {
        return nextState;
    }
}
