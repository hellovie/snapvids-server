package io.github.hellovie.snapvids.infrastructure.persistence.dao;

import io.github.hellovie.snapvids.infrastructure.persistence.dao.custom.CustomChunkFileDao;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.ChunkFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 分片文件仓储。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("chunkFileDao")
public interface ChunkFileDao extends JpaRepository<ChunkFile, Long>, CustomChunkFileDao {

    /**
     * 根据文件 id 和分片编号查找分片信息
     *
     * @param fileId     文件 id
     * @param currentNum 分片编号
     * @return 分片信息
     */
    Optional<ChunkFile> findByFileIdAndCurrentNum(Long fileId, Integer currentNum);

    /**
     * 根据文件 id 查询分片列表
     *
     * @param fileId 文件 id
     * @return 分片列表
     */
    List<ChunkFile> findAllByFileId(Long fileId);
}
