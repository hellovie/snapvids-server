package io.github.hellovie.snapvids.domain.file.service.impl;

import io.github.hellovie.snapvids.common.exception.business.AuthException;
import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.common.module.file.FileExceptionType;
import io.github.hellovie.snapvids.common.module.user.UserExceptionType;
import io.github.hellovie.snapvids.common.service.IdGenerateService;
import io.github.hellovie.snapvids.domain.file.entity.FileInfo;
import io.github.hellovie.snapvids.domain.file.entity.FileOperator;
import io.github.hellovie.snapvids.domain.file.event.CreateFileInfoCommand;
import io.github.hellovie.snapvids.domain.file.event.UpdateFileStateCommand;
import io.github.hellovie.snapvids.domain.file.repository.FileRepository;
import io.github.hellovie.snapvids.domain.file.service.FileService;
import io.github.hellovie.snapvids.domain.util.ContextHolder;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileState;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileType;
import io.github.hellovie.snapvids.types.file.Filename;
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
        FileOperator operator = new FileOperator(ContextHolder.getUserOrElseThrow().getId());
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
        FileInfo fileInfo = repository.findById(command.getId());
        if (fileInfo == null) {
            throw new DataException(FileExceptionType.FILE_NOT_FOUNT);
        }
        // 文件创建者才能更改文件状态
        if (fileInfo.getCreatedBy() == null ||
                !Objects.equals(fileInfo.getCreatedBy().getId().getValue(), command.getId().getValue())) {
            throw new AuthException(UserExceptionType.USER_NOT_PERMISSION_TO_OPERATE);
        }
        // Todo：需要判断文件当前是什么状态，决定是否能够更新为下一状态

        repository.updateFileState(command.getId(), command.getState(), ContextHolder.getUserOrElseThrow().getId());

        fileInfo.setState(command.getState());
        return fileInfo;
    }
}
