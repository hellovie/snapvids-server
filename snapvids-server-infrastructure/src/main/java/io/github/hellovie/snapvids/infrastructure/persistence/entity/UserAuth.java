package io.github.hellovie.snapvids.infrastructure.persistence.entity;

import io.github.hellovie.snapvids.infrastructure.persistence.enums.AuthType;

import javax.persistence.*;

/**
 * 第三方认证表实体。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Entity(name = "UserAuth")
@Table(name = "user_auth")
public class UserAuth extends BaseEntity {

    /**
     * 用户
     * <p>需要先有 {@link User} 才能保存。</p>
     */
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    /**
     * 认证类型
     */
    @Enumerated(EnumType.ORDINAL)
    private AuthType type;

    /**
     * 身份标识
     */
    @Column(updatable = false)
    private String externalId;

    /**
     * 密码凭证
     */
    private String credential;

    public UserAuth() {}

    public UserAuth(Long id) {
        super(id);
    }

    public User getUser() {
        return user;
    }

    public UserAuth setUser(User user) {
        this.user = user;
        return this;
    }

    public AuthType getType() {
        return type;
    }

    public UserAuth setType(AuthType type) {
        this.type = type;
        return this;
    }

    public String getExternalId() {
        return externalId;
    }

    public UserAuth setExternalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    public String getCredential() {
        return credential;
    }

    public UserAuth setCredential(String credential) {
        this.credential = credential;
        return this;
    }
}
