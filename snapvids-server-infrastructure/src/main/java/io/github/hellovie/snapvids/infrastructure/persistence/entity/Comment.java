package io.github.hellovie.snapvids.infrastructure.persistence.entity;

import io.github.hellovie.snapvids.infrastructure.persistence.enums.CommentType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * 评论表实体。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Entity(name = "Comment")
@Table(name = "comment")
public class Comment extends AuditEntity {

    /**
     * 作品 ID
     * <p>使用作品去找评论，所以无需在 {@link Comment} 保存 {@link Creation}。</p>
     */
    private Long creationId;

    /**
     * 是否顶层评论（1 - 是，0 - 否）
     */
    private Boolean isParentComment = Boolean.TRUE;

    /**
     * 根评论ID
     * <p>不需要每个评论都查询其根评论实体。</p>
     */
    private Long pid;

    /**
     * 评论内容
     */
    private String content;

    /**
     * At 的用户 ID（英文逗号分割）
     */
    private String atUserIds;

    /**
     * 发布评论的用户 ID
     * <p>使用用户 ID 去查询 {@link UserInfo}。</p>
     */
    private Long authorId;

    /**
     * 被回复的用户ID
     * <p>使用用户 ID 去查询 {@link UserInfo}。</p>
     */
    private Long replyUserId;

    /**
     * 是否置顶（1 - 置顶，0 - 不置顶）
     */
    private Boolean isTop = Boolean.FALSE;

    /**
     * 评论类型（默认普通评论）
     */
    @Enumerated(EnumType.ORDINAL)
    private CommentType type;

    /**
     * 推荐权重（0 - 不展示，权重越高，越容易被其他用户看见）
     */
    private Integer weight;

    public Comment() {}

    public Comment(Long id) {
        super(id);
    }

    public Long getCreationId() {
        return creationId;
    }

    public Comment setCreationId(Long creationId) {
        this.creationId = creationId;
        return this;
    }

    public Boolean getParentComment() {
        return isParentComment;
    }

    public Comment setParentComment(Boolean parentComment) {
        isParentComment = parentComment;
        return this;
    }

    public Long getPid() {
        return pid;
    }

    public Comment setPid(Long pid) {
        this.pid = pid;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Comment setContent(String content) {
        this.content = content;
        return this;
    }

    public String getAtUserIds() {
        return atUserIds;
    }

    public Comment setAtUserIds(String atUserIds) {
        this.atUserIds = atUserIds;
        return this;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Comment setAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public Long getReplyUserId() {
        return replyUserId;
    }

    public Comment setReplyUserId(Long replyUserId) {
        this.replyUserId = replyUserId;
        return this;
    }

    public Boolean getTop() {
        return isTop;
    }

    public Comment setTop(Boolean top) {
        isTop = top;
        return this;
    }

    public CommentType getType() {
        return type;
    }

    public Comment setType(CommentType type) {
        this.type = type;
        return this;
    }

    public Integer getWeight() {
        return weight;
    }

    public Comment setWeight(Integer weight) {
        this.weight = weight;
        return this;
    }
}
