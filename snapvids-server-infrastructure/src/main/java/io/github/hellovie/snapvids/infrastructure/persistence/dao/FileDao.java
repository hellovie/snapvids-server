package io.github.hellovie.snapvids.infrastructure.persistence.dao;

import io.github.hellovie.snapvids.infrastructure.persistence.dao.custom.CustomFileDao;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 文件仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("fileDao")
public interface FileDao extends JpaRepository<File, Long>, CustomFileDao {

    /**
     * 根据文件唯一标识和创建者 id 查询文件
     *
     * @param identifier  文件唯一标识
     * @param createdById 创建者 id
     * @return 文件
     */
    Optional<File> findByIdentifierAndCreatedById(String identifier, Long createdById);
}
