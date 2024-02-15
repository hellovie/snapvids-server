package io.github.hellovie.snapvids.infrastructure.persistence.entity;

import io.github.hellovie.snapvids.infrastructure.persistence.enums.ShareFromType;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.ShareToType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * 分享表实体。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Entity(name = "Share")
@Table(name = "share")
public class Share extends BaseEntity {

    /**
     * 用户 ID
     * <p>没必要查询每条转发记录的用户实体。</p>
     */
    private Long userId;

    /**
     * 转发来源的作品 ID
     */
    private Long fromCreationId;

    /**
     * 转发来源的评论 ID
     */
    private Long fromCommentId;

    /**
     * 转发来源类型（默认作品）
     */
    @Enumerated(EnumType.ORDINAL)
    private ShareFromType fromType;

    /**
     * 转发去处类型（默认用户）
     */
    @Enumerated(EnumType.ORDINAL)
    private ShareToType toType;

    /**
     * 转发去处的用户ID
     */
    private Long toUserId;

    public Share() {}

    public Share(Long id) {
        super(id);
    }

    public Long getUserId() {
        return userId;
    }

    public Share setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getFromCreationId() {
        return fromCreationId;
    }

    public Share setFromCreationId(Long fromCreationId) {
        this.fromCreationId = fromCreationId;
        return this;
    }

    public Long getFromCommentId() {
        return fromCommentId;
    }

    public Share setFromCommentId(Long fromCommentId) {
        this.fromCommentId = fromCommentId;
        return this;
    }

    public ShareFromType getFromType() {
        return fromType;
    }

    public Share setFromType(ShareFromType fromType) {
        this.fromType = fromType;
        return this;
    }

    public ShareToType getToType() {
        return toType;
    }

    public Share setToType(ShareToType toType) {
        this.toType = toType;
        return this;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public Share setToUserId(Long toUserId) {
        this.toUserId = toUserId;
        return this;
    }
}
