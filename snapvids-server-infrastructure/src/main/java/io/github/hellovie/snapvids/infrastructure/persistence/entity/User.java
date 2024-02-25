package io.github.hellovie.snapvids.infrastructure.persistence.entity;

import io.github.hellovie.snapvids.infrastructure.persistence.enums.UserState;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * 用户表实体。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Entity(name = "User")
@Table(name = "user")
public class User extends BaseEntity {

    /**
     * 用户名
     */
    @Column(updatable = false)
    private String username;

    /**
     * 密码（加密）
     */
    private String password;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 最后登录 IP
     */
    private Integer lastLoginIp;

    /**
     * 最后登录时间
     */
    private Timestamp lastLoginTime;

    /**
     * 注册 IP
     */
    @Column(updatable = false)
    private Integer registerIp;

    /**
     * 注册时间
     */
    @Column(updatable = false)
    private Timestamp registerTime;

    /**
     * 用户状态（0 - 启用）
     */
    @Enumerated(EnumType.ORDINAL)
    private UserState state;

    /**
     * 用户角色列表
     * <p>每次（新增 / 删除）用户时，会自动（新增 / 删除）用户与角色的关联（中间表）。</p>
     * <p>若需要在用户已经关联的角色上（新增 / 删除），需要将用户角色全部查出修改后再进行保存。</p>
     * <p>角色 ID 不存在时，无法新增用户。</p>
     */
    @OneToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<Role> roles;

    public User() {}

    public User(Long id) {
        super(id);
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Integer getLastLoginIp() {
        return lastLoginIp;
    }

    public User setLastLoginIp(Integer lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
        return this;
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public User setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
        return this;
    }

    public Integer getRegisterIp() {
        return registerIp;
    }

    public User setRegisterIp(Integer registerIp) {
        this.registerIp = registerIp;
        return this;
    }

    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public User setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
        return this;
    }

    public UserState getState() {
        return state;
    }

    public User setState(UserState state) {
        this.state = state;
        return this;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public User setRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }
}