package io.github.hellovie.snapvids.infrastructure.persistence.dao.custom.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.hellovie.snapvids.infrastructure.persistence.dao.custom.CustomUserDao;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.User;
import io.github.hellovie.snapvids.infrastructure.persistence.q.QUser;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * 自定义用户仓储实现。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class CustomUserDaoImpl implements CustomUserDao {

    @Resource(name = "jpaQueryFactory")
    private JPAQueryFactory jpaQueryFactory;

    /**
     * {@inheritDoc}
     *
     * @see CustomUserDao#updateLoginInfoByUsername(String, int, Timestamp)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public long updateLoginInfoByUsername(String username, int lastLoginIp, Timestamp lastLoginTime) {
        QUser qUser = QUser.user;
        return jpaQueryFactory
                .update(qUser)
                .set(qUser.lastLoginIp, lastLoginIp)
                .set(qUser.lastLoginTime, lastLoginTime)
                .set(qUser.utcModified, lastLoginTime)
                .where(qUser.username.eq(username))
                .execute();
    }

    /**
     * {@inheritDoc}
     *
     * @see CustomUserDao#onlyFindUserByUsername(String)
     */
    @Override
    public User onlyFindUserByUsername(String username) {
        QUser qUser = QUser.user;
        return jpaQueryFactory
                .selectFrom(qUser)
                .where(qUser.username.eq(username))
                .fetchOne();
    }
}
