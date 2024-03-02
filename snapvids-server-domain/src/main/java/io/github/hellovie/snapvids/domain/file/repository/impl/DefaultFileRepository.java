package io.github.hellovie.snapvids.domain.file.repository.impl;

import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.common.module.file.FileExceptionType;
import io.github.hellovie.snapvids.common.module.user.UserExceptionType;
import io.github.hellovie.snapvids.domain.file.entity.FileInfo;
import io.github.hellovie.snapvids.domain.file.entity.FileOperator;
import io.github.hellovie.snapvids.domain.file.repository.FileRepository;
import io.github.hellovie.snapvids.infrastructure.persistence.dao.FileDao;
import io.github.hellovie.snapvids.infrastructure.persistence.dao.UserDao;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.File;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.User;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileState;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.file.FileIdentifier;
import io.github.hellovie.snapvids.types.file.FilePath;
import io.github.hellovie.snapvids.types.file.FileSize;
import io.github.hellovie.snapvids.types.file.Filename;
import io.github.hellovie.snapvids.types.user.Username;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

/**
 * 文件仓储默认实现。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("fileRepository")
public class DefaultFileRepository implements FileRepository {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultFileRepository.class);

    @Resource(name = "fileDao")
    private FileDao fileDao;

    @Resource(name = "userDao")
    private UserDao userDao;

    /**
     * {@inheritDoc}
     *
     * @see FileRepository#saveFileInfo(FileInfo)
     */
    @Override
    public FileInfo saveFileInfo(FileInfo fileInfo) {
        File file = new File();
        file.setOriginalName(fileInfo.getOriginalName().getValue())
                .setStorageName(fileInfo.getStorageName().getValue())
                .setIdentifier(fileInfo.getIdentifier().getValue())
                .setPath(fileInfo.getPath().getValue())
                .setExt(fileInfo.getExt())
                .setSize(fileInfo.getSize().getValue())
                .setType(fileInfo.getType())
                .setStorage(fileInfo.getStorage())
                .setState(fileInfo.getState())
                .setVisibility(fileInfo.getVisibility())
                .setCreatedById(fileInfo.getCreatedBy().getId().getValue())
                .setModifiedById(fileInfo.getModifiedBy().getId().getValue());
        File saved = fileDao.save(file);
        return toFileInfo(saved);
    }

    /**
     * {@inheritDoc}
     *
     * @see FileRepository#updateFileState(Id, FileState, Id)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFileState(Id fileId, FileState fileState, Id userId) {
        long updateRows = fileDao.updateState(fileId.getValue(), fileState, userId.getValue());
        if (updateRows != 1) {
            LOG.error("[更新文件状态失败]>>> 影响行数={}，文件ID={}，文件状态={}，用户ID={}",
                    updateRows, fileId, fileState, userId);
            throw new DataException(FileExceptionType.UPDATE_FILE_STATE_FAILED);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see FileRepository#findById(Id)
     */
    @Override
    public FileInfo findById(Id id) {
        Optional<File> optional = fileDao.findById(id.getValue());
        return optional.map(this::toFileInfo).orElse(null);
    }

    /**
     * {@inheritDoc}
     *
     * @see FileRepository#findByIdentifierAndUserId(FileIdentifier, Id)
     */
    @Override
    public FileInfo findByIdentifierAndUserId(FileIdentifier fileIdentifier, Id userId) {
        if (fileIdentifier == null || userId == null) {
            return null;
        }
        Optional<File> optional = fileDao.findByIdentifierAndCreatedById(fileIdentifier.getValue(), userId.getValue());
        return optional.map(this::toFileInfo).orElse(null);
    }

    /**
     * {@link File} to {@link FileInfo}.
     * <p>自动查询操作者信息。</p>
     *
     * @param file {@link File}
     * @return {@link FileInfo}
     */
    private FileInfo toFileInfo(File file) {
        if (file == null) {
            return null;
        }

        FileOperator createdBy = findFileOperatorByUserId(new Id(file.getCreatedById()));
        FileOperator modifiedBy = createdBy;
        if (!Objects.equals(file.getCreatedById(), file.getModifiedById())) {
            modifiedBy = findFileOperatorByUserId(new Id(file.getModifiedById()));
        }
        return new FileInfo(
                new Id(file.getId()),
                new Filename(file.getOriginalName()),
                new Filename(file.getStorageName()),
                new FileIdentifier(file.getIdentifier()),
                new FilePath(file.getPath()),
                file.getExt(),
                new FileSize(file.getSize()),
                file.getType(),
                file.getStorage(),
                file.getState(),
                file.getVisibility(),
                createdBy,
                modifiedBy
        );
    }

    /**
     * 根据用户 id 查询 {@link FileOperator}。
     *
     * @param userId 用户 id
     * @return {@link FileOperator}
     */
    private FileOperator findFileOperatorByUserId(Id userId) {
        Optional<User> optional = userDao.findById(userId.getValue());
        User user = optional.orElseThrow(() -> new DataException(UserExceptionType.USER_NOT_FOUND));
        return toFileOperator(user);
    }

    /**
     * {@link User} to {@link FileOperator}.
     *
     * @param user {@link User}
     * @return {@link FileOperator}
     */
    private FileOperator toFileOperator(User user) {
        if (user == null) {
            return null;
        }

        return new FileOperator(new Id(user.getId()), new Username(user.getUsername()));
    }
}
