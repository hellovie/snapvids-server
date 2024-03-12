package io.github.hellovie.snapvids.domain.fileInfo.service.impl;

import io.github.hellovie.snapvids.common.enums.FileState;
import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.common.module.file.FileExceptionType;
import io.github.hellovie.snapvids.domain.auth.entity.SysUser;
import io.github.hellovie.snapvids.domain.fileInfo.entity.FileInfo;
import io.github.hellovie.snapvids.domain.fileInfo.entity.FileOperator;
import io.github.hellovie.snapvids.domain.fileInfo.event.CreateFileInfoCommand;
import io.github.hellovie.snapvids.domain.fileInfo.event.UpdateFileStateCommand;
import io.github.hellovie.snapvids.domain.fileInfo.repository.FileInfoRepository;
import io.github.hellovie.snapvids.domain.fileInfo.service.FileInfoService;
import io.github.hellovie.snapvids.domain.fileInfo.state.FileStateMachine;
import io.github.hellovie.snapvids.domain.util.ContextHolder;
import io.github.hellovie.snapvids.infrastructure.service.generator.IdGenerateService;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.file.Filename;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 文件信息服务默认实现。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Service("fileInfoService")
public class DefaultFileInfoService implements FileInfoService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultFileInfoService.class);

    @Resource(name = "fileInfoRepository")
    private FileInfoRepository repository;

    @Resource(name = "ksuidGenerator")
    private IdGenerateService<String> ksuidGenerator;

    /**
     * {@inheritDoc}
     *
     * @see FileInfoService#create(CreateFileInfoCommand)
     */
    @Override
    public FileInfo create(CreateFileInfoCommand command) throws DataException {
        Id curUserId = ContextHolder.getUserOrElseThrow().getId();
        FileInfo dbFileInfo = repository.findByFileKeyAndUserId(command.getFileKey(), curUserId);
        // 文件已存在，抛出异常
        if (dbFileInfo != null) {
            throw new DataException(FileExceptionType.FILE_ALREADY_EXIST);
        }

        FileOperator operator = new FileOperator(curUserId);
        FileInfo fileInfo = new FileInfo(
                command.getOriginalName(),
                new Filename(ksuidGenerator.genId()),
                command.getFileKey(),
                command.getPath(),
                command.getExt(),
                command.getSize(),
                command.getType(),
                command.getStorage(),
                FileState.UPLOADING,
                command.getVisibility(),
                operator,
                operator
        );
        return repository.saveFileInfo(fileInfo);
    }

    /**
     * {@inheritDoc}
     *
     * @see FileInfoService#updateState(UpdateFileStateCommand)
     */
    @Override
    public FileInfo updateState(UpdateFileStateCommand command) throws DataException {
        SysUser curUser = ContextHolder.getUserOrElseThrow();
        FileInfo fileInfo = repository.findByFileKeyAndUserId(command.getFileKey(), curUser.getId());
        if (fileInfo == null) {
            throw new DataException(FileExceptionType.FILE_NOT_FOUNT);
        }

        // 文件状态机自动获取下一个状态
        FileState nextState = FileStateMachine.transform(command.getState(), command.getEvent());
        // 无下一状态，保持当前状态不变
        if (nextState == null) {
            LOG.info("[没有能够跃迁到的状态]>>> 当前状态={}，跃迁动作={}，下一状态={}",
                    command.getState(), command.getEvent(), nextState);
            nextState = fileInfo.getState();
        }
        // 状态已经更新过了，无需再更新
        if (nextState == fileInfo.getState()) {
            return fileInfo;
        }

        repository.updateFileState(fileInfo.getId(), nextState, curUser.getId());

        fileInfo.setState(nextState);
        return fileInfo;
    }
}
