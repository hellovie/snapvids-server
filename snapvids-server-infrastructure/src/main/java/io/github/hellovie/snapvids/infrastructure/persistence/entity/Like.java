package io.github.hellovie.snapvids.infrastructure.persistence.entity;

import io.github.hellovie.snapvids.infrastructure.persistence.enums.LikeObjectType;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.LikeType;

import javax.persistence.*;

/**
 * 点赞表实体。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Entity(name = "Like")
@Table(name = "like")
public class Like extends BaseEntity {

    /**
     * 用户 ID
     * <p>没必要查询每条点赞记录的用户实体。</p>
     */
    private Long userId;

    /**
     * 点赞类型（默认喜欢）
     */
    @Enumerated(EnumType.ORDINAL)
    private LikeType type;

    /**
     * 点赞状态（1 - 点赞，0 - 取消点赞）
     */
    private Boolean isLike;

    /**
     * 被点赞的对象类型（默认作品）
     */
    @Enumerated(EnumType.ORDINAL)
    private LikeObjectType objectType;

    /**
     * 被点赞的作品
     */
    @OneToOne
    @JoinColumn(name = "creation_id", referencedColumnName = "id")
    private Creation creation;

    /**
     * 被点赞的评论
     */
    @OneToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private Comment comment;

    public Like() {}

    public Like(Long id) {
        super(id);
    }

    public Long getUserId() {
        return userId;
    }

    public Like setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public LikeType getType() {
        return type;
    }

    public Like setType(LikeType type) {
        this.type = type;
        return this;
    }

    public Boolean getLike() {
        return isLike;
    }

    public Like setLike(Boolean like) {
        isLike = like;
        return this;
    }

    public LikeObjectType getObjectType() {
        return objectType;
    }

    public Like setObjectType(LikeObjectType objectType) {
        this.objectType = objectType;
        return this;
    }

    public Creation getCreation() {
        return creation;
    }

    public Like setCreation(Creation creation) {
        this.creation = creation;
        return this;
    }

    public Comment getComment() {
        return comment;
    }

    public Like setComment(Comment comment) {
        this.comment = comment;
        return this;
    }
}
