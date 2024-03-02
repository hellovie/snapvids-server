package io.github.hellovie.snapvids.domain.storage.repository.impl;

import io.github.hellovie.snapvids.domain.storage.entity.FileMetadata;
import io.github.hellovie.snapvids.domain.storage.repository.StorageRepository;
import io.github.hellovie.snapvids.infrastructure.persistence.dao.FileDao;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.File;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.file.FileIdentifier;
import io.github.hellovie.snapvids.types.file.FilePath;
import io.github.hellovie.snapvids.types.file.FileSize;
import io.github.hellovie.snapvids.types.file.Filename;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 文件存储仓储默认实现。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("storageRepository")
public class DefaultStorageRepository implements StorageRepository {

    @Resource(name = "fileDao")
    private FileDao fileDao;

    /**
     * {@inheritDoc}
     *
     * @see StorageRepository#findByIdentifierAndUserId(FileIdentifier, Id)
     */
    @Override
    public FileMetadata findByIdentifierAndUserId(FileIdentifier fileIdentifier, Id userId) {
        if (fileIdentifier == null || userId == null) {
            return null;
        }
        Optional<File> optional = fileDao.findByIdentifierAndCreatedById(fileIdentifier.getValue(), userId.getValue());
        return optional.map(this::toFileMetadata).orElse(null);
    }

    /**
     * {@link File} to {@link FileMetadata}.
     *
     * @param file {@link File}
     * @return {@link FileMetadata}
     */
    private FileMetadata toFileMetadata(File file) {
        if (file == null) {
            return null;
        }

        return new FileMetadata(
                new Filename(file.getOriginalName()),
                new Filename(file.getStorageName()),
                new FileIdentifier(file.getIdentifier()),
                new FilePath(file.getPath()),
                file.getExt(),
                new FileSize(file.getSize()),
                file.getType()
        );
    }
}
