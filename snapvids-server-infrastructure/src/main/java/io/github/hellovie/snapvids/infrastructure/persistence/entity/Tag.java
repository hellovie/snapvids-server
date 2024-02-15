package io.github.hellovie.snapvids.infrastructure.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 作品标签表实体。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Entity(name = "Tag")
@Table(name = "tag")
public class Tag extends AuditEntity {

    /**
     * 标签名
     */
    private String name;

    /**
     * 使用次数
     */
    private Integer useCount;

    /**
     * 作者 ID
     * <p>使用作者 ID 去查询 {@link UserInfo}。</p>
     */
    private Long authorId;

    /**
     * 推荐权重（0 - 不展示，权重越高，越容易被其他用户看见）
     */
    private Integer weight;

    public Tag() {}

    public Tag(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public Tag setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getUseCount() {
        return useCount;
    }

    public Tag setUseCount(Integer useCount) {
        this.useCount = useCount;
        return this;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Tag setAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }

    public Integer getWeight() {
        return weight;
    }

    public Tag setWeight(Integer weight) {
        this.weight = weight;
        return this;
    }
}
