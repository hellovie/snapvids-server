package io.github.hellovie.snapvids.infrastructure.persistence.dao.custom.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.hellovie.snapvids.infrastructure.persistence.dao.custom.CustomFileDao;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileState;
import io.github.hellovie.snapvids.infrastructure.persistence.q.QFile;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * 自定义文件仓储实现。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class CustomFileDaoImpl implements CustomFileDao {

    @Resource(name = "jpaQueryFactory")
    private JPAQueryFactory jpaQueryFactory;

    /**
     * {@inheritDoc}
     *
     * @see CustomFileDao#updateState(long, FileState, long)
     */
    @Override
    public long updateState(long id, FileState state, long userId) {
        QFile qFile = QFile.file;
        return jpaQueryFactory
                .update(qFile)
                .set(qFile.state, state)
                .set(qFile.modifiedById, userId)
                .set(qFile.utcModified, new Timestamp(System.currentTimeMillis()))
                .where(qFile.id.eq(id))
                .execute();
    }
}
