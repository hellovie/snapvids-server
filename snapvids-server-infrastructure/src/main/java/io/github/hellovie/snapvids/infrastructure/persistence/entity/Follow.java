package io.github.hellovie.snapvids.infrastructure.persistence.entity;

import io.github.hellovie.snapvids.common.enums.FollowType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * 关注表实体。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Entity(name = "Follow")
@Table(name = "follow")
public class Follow extends BaseEntity {

    /**
     * 关注 ID
     * <p>没必要每个 {@link Follow} 都包含 {@link User}。</p>
     */
    private Long followingId;

    /**
     * 粉丝 ID
     * <p>没必要每个 {@link Follow} 都包含 {@link User}。</p>
     */
    private Long followerId;

    /**
     * 关注类型（默认普通关注）
     */
    @Enumerated(EnumType.ORDINAL)
    private FollowType type;

    /**
     * 备注
     */
    private String remark;

    /**
     * 查看权重（0 - 从未看过，权重越高，说明查看次数越频繁）
     */
    private Integer weight;

    public Follow() {}

    public Follow(Long id) {
        super(id);
    }

    public Long getFollowingId() {
        return followingId;
    }

    public Follow setFollowingId(Long followingId) {
        this.followingId = followingId;
        return this;
    }

    public Long getFollowerId() {
        return followerId;
    }

    public Follow setFollowerId(Long followerId) {
        this.followerId = followerId;
        return this;
    }

    public FollowType getType() {
        return type;
    }

    public Follow setType(FollowType type) {
        this.type = type;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public Follow setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Integer getWeight() {
        return weight;
    }

    public Follow setWeight(Integer weight) {
        this.weight = weight;
        return this;
    }
}
