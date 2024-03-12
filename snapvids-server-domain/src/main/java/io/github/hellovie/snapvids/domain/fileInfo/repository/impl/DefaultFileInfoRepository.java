package io.github.hellovie.snapvids.domain.fileInfo.repository.impl;

import io.github.hellovie.snapvids.common.enums.FileState;
import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.common.module.file.FileExceptionType;
import io.github.hellovie.snapvids.common.module.user.UserExceptionType;
import io.github.hellovie.snapvids.domain.fileInfo.entity.FileInfo;
import io.github.hellovie.snapvids.domain.fileInfo.entity.FileOperator;
import io.github.hellovie.snapvids.domain.fileInfo.repository.FileInfoRepository;
import io.github.hellovie.snapvids.infrastructure.persistence.dao.FileDao;
import io.github.hellovie.snapvids.infrastructure.persistence.dao.UserDao;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.File;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.User;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.file.FileKey;
import io.github.hellovie.snapvids.types.file.FilePath;
import io.github.hellovie.snapvids.types.file.FileSize;
import io.github.hellovie.snapvids.types.file.Filename;
import io.github.hellovie.snapvids.types.user.Username;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

/**
 * 文件信息仓储默认实现。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("fileInfoRepository")
public class DefaultFileInfoRepository implements FileInfoRepository {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultFileInfoRepository.class);

    @Resource(name = "fileDao")
    private FileDao fileDao;

    @Resource(name = "userDao")
    private UserDao userDao;

    /**
     * {@inheritDoc}
     *
     * @see FileInfoRepository#saveFileInfo(FileInfo)
     */
    @Override
    public FileInfo saveFileInfo(FileInfo fileInfo) {
        File file = new File();
        file.setOriginalName(fileInfo.getOriginalName().getValue())
                .setStorageName(fileInfo.getStorageName().getValue())
                .setFileKey(fileInfo.getFileKey().getValue())
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
     * @see FileInfoRepository#updateFileState(Id, FileState, Id)
     */
    @Override
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
     * @see FileInfoRepository#findById(Id)
     */
    @Override
    public FileInfo findById(Id id) {
        Optional<File> optional = fileDao.findById(id.getValue());
        if (optional.isPresent() && checkValid(optional.get())) {
            return toFileInfo(optional.get());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @see FileInfoRepository#findByFileKeyAndUserId(FileKey, Id)
     */
    @Override
    public FileInfo findByFileKeyAndUserId(FileKey fileKey, Id userId) {
        if (fileKey == null || userId == null) {
            return null;
        }
        Optional<File> optional = fileDao.findByFileKeyAndCreatedById(fileKey.getValue(), userId.getValue());
        if (optional.isPresent() && checkValid(optional.get())) {
            return toFileInfo(optional.get());
        }
        return null;
    }

    /**
     * 判断文件是否有效（逻辑删除）。
     *
     * @param file 文件
     * @return true：有效
     */
    private boolean checkValid(File file) {
        return file != null && !file.getIsDeleted();
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
                new FileKey(file.getFileKey()),
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
