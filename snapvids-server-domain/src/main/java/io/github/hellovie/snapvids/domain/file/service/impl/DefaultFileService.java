package io.github.hellovie.snapvids.domain.file.service.impl;

import io.github.hellovie.snapvids.common.exception.business.AuthException;
import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.common.module.file.FileExceptionType;
import io.github.hellovie.snapvids.common.module.user.UserExceptionType;
import io.github.hellovie.snapvids.common.service.IdGenerateService;
import io.github.hellovie.snapvids.domain.auth.entity.SysUser;
import io.github.hellovie.snapvids.domain.file.entity.FileInfo;
import io.github.hellovie.snapvids.domain.file.entity.FileOperator;
import io.github.hellovie.snapvids.domain.file.event.CreateFileInfoCommand;
import io.github.hellovie.snapvids.domain.file.event.UpdateFileStateCommand;
import io.github.hellovie.snapvids.domain.file.repository.FileRepository;
import io.github.hellovie.snapvids.domain.file.service.FileService;
import io.github.hellovie.snapvids.domain.file.state.FileStateMachine;
import io.github.hellovie.snapvids.domain.util.ContextHolder;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileState;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.file.Filename;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 文件服务默认实现。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Service("fileService")
public class DefaultFileService implements FileService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultFileService.class);

    @Resource(name = "fileRepository")
    private FileRepository repository;

    @Resource(name = "ksuidGenerator")
    private IdGenerateService<String> ksuidGenerator;

    /**
     * {@inheritDoc}
     *
     * @see FileService#create(CreateFileInfoCommand)
     */
    @Override
    public FileInfo create(CreateFileInfoCommand command) {
        Id curUserId = ContextHolder.getUserOrElseThrow().getId();
        FileInfo dbFileInfo = repository.findByIdentifier(command.getIdentifier());
        // 文件已存在
        if (dbFileInfo != null) {
            // 当前用户不是文件创建者，无法获取文件信息
            // 出现 Hash 碰撞，不同用户上传同一个文件
            Id createdById = dbFileInfo.getCreatedBy().getId();
            if (!Objects.equals(createdById.getValue(), curUserId.getValue())) {
                LOG.error("[文件发生哈希碰撞，不同用户上传同一文件]>>> 当前请求用户ID={}，文件创建者ID={}，文件哈希={}",
                        curUserId, createdById, command.getIdentifier());
                throw new DataException(FileExceptionType.FILE_HASH_COLLISIONS, "文件已违规");
            }
            return dbFileInfo;
        }

        FileOperator operator = new FileOperator(curUserId);
        FileInfo fileInfo = new FileInfo(
                command.getOriginalName(),
                new Filename(ksuidGenerator.genId()),
                command.getIdentifier(),
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
     * @see FileService#updateState(UpdateFileStateCommand)
     */
    @Override
    public FileInfo updateState(UpdateFileStateCommand command) throws DataException {
        FileInfo fileInfo = repository.findByIdentifier(command.getFileIdentifier());
        if (fileInfo == null) {
            throw new DataException(FileExceptionType.FILE_NOT_FOUNT);
        }
        // 文件创建者才能更改文件状态
        SysUser curUser = ContextHolder.getUserOrElseThrow();
        if (fileInfo.getCreatedBy() == null ||
                !Objects.equals(fileInfo.getCreatedBy().getId().getValue(), curUser.getId().getValue())) {
            throw new AuthException(UserExceptionType.USER_NOT_PERMISSION_TO_OPERATE);
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
