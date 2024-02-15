package io.github.hellovie.snapvids.infrastructure.persistence.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

/**
 * 实体表基类。
 *
 * @author hellovie
 * @since 1.0.0
 */
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * ID
     */
    @Id
    protected Long id;

    /**
     * 创建时间（UTC）
     */
    @CreationTimestamp
    @Column(updatable = false)
    protected Timestamp utcCreated;

    /**
     * 更新时间（UTC）
     */
    @UpdateTimestamp
    protected Timestamp utcModified;

    /**
     * 是否删除（1 - 删除，0 - 未删除）
     */
    protected Boolean isDeleted = Boolean.FALSE;

    public BaseEntity() {}

    public BaseEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public BaseEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public Timestamp getUtcCreated() {
        return utcCreated;
    }

    public BaseEntity setUtcCreated(Timestamp utcCreated) {
        this.utcCreated = utcCreated;
        return this;
    }

    public Timestamp getUtcModified() {
        return utcModified;
    }

    public BaseEntity setUtcModified(Timestamp utcModified) {
        this.utcModified = utcModified;
        return this;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public BaseEntity setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }
}
