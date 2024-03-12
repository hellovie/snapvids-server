package io.github.hellovie.snapvids.infrastructure.persistence.entity;

import io.github.hellovie.snapvids.common.enums.CollectObjectType;

import javax.persistence.*;

/**
 * 用户收藏表实体。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Entity(name = "Collect")
@Table(name = "collect")
public class Collect extends BaseEntity {

    /**
     * 用户 ID
     * <p>没必要查询每条收藏记录的用户实体。</p>
     */
    private Long userId;

    /**
     * 被收藏的对象类型(默认作品)
     */
    @Enumerated(EnumType.ORDINAL)
    private CollectObjectType objectType;

    /**
     * 被收藏的作品
     */
    @OneToOne
    @JoinColumn(name = "creation_id", referencedColumnName = "id")
    private Creation creationId;

    public Collect() {}

    public Collect(Long id) {
        super(id);
    }

    public Long getUserId() {
        return userId;
    }

    public Collect setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public CollectObjectType getObjectType() {
        return objectType;
    }

    public Collect setObjectType(CollectObjectType objectType) {
        this.objectType = objectType;
        return this;
    }

    public Creation getCreationId() {
        return creationId;
    }

    public Collect setCreationId(Creation creationId) {
        this.creationId = creationId;
        return this;
    }
}
