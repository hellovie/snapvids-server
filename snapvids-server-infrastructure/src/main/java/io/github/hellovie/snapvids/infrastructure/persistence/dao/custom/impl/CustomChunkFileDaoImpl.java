package io.github.hellovie.snapvids.infrastructure.persistence.dao.custom.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.hellovie.snapvids.infrastructure.persistence.dao.custom.CustomChunkFileDao;
import io.github.hellovie.snapvids.infrastructure.persistence.q.QChunkFile;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 自定义文件分片仓储实现。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class CustomChunkFileDaoImpl implements CustomChunkFileDao {

    @Resource(name = "jpaQueryFactory")
    private JPAQueryFactory jpaQueryFactory;

    /**
     * {@inheritDoc}
     *
     * @see CustomChunkFileDao#deleteByFileId(long)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public long deleteByFileId(long fileId) {
        QChunkFile qChunkFile = QChunkFile.chunkFile;
        return jpaQueryFactory.delete(qChunkFile)
                .where(qChunkFile.fileId.eq(fileId))
                .execute();
    }
}
