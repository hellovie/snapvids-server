package io.github.hellovie.snapvids.domain.auth.entity;

import io.github.hellovie.snapvids.infrastructure.persistence.enums.UserState;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.common.Ip;
import io.github.hellovie.snapvids.types.common.PhoneNumber;
import io.github.hellovie.snapvids.types.user.Password;
import io.github.hellovie.snapvids.types.user.Username;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class SysUser {

    /**
     * id
     */
    private Id id;

    /**
     * 用户名
     */
    private Username username;

    /**
     * 密码
     */
    private Password password;

    /**
     * 手机号码
     */
    private PhoneNumber phoneNumber;

    /**
     * 最后登录 ip
     */
    private Ip lastLoginIp;

    /**
     * 最后登录时间
     */
    private Timestamp lastLoginTime;

    /**
     * 注册 ip
     */
    private Ip registerIp;

    /**
     * 注册时间
     */
    private Timestamp registerTime;

    /**
     * 用户状态
     */
    private UserState state;

    /**
     * 用户角色
     */
    private final List<SysRole> roles = new ArrayList<>();

    public SysUser(Username username, Password password, PhoneNumber phoneNumber, Ip lastLoginIp,
                   Timestamp lastLoginTime, Ip registerIp, Timestamp registerTime, UserState state) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.lastLoginIp = lastLoginIp;
        this.lastLoginTime = lastLoginTime;
        this.registerIp = registerIp;
        this.registerTime = registerTime;
        this.state = state;
    }

    public SysUser(Id id, Username username, Password password, PhoneNumber phoneNumber, Ip lastLoginIp,
                   Timestamp lastLoginTime, Ip registerIp, Timestamp registerTime, UserState state) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.lastLoginIp = lastLoginIp;
        this.lastLoginTime = lastLoginTime;
        this.registerIp = registerIp;
        this.registerTime = registerTime;
        this.state = state;
    }

    public Id getId() {
        return id;
    }

    public SysUser setId(Id id) {
        this.id = id;
        return this;
    }

    public Username getUsername() {
        return username;
    }

    public SysUser setUsername(Username username) {
        this.username = username;
        return this;
    }

    public Password getPassword() {
        return password;
    }

    public SysUser setPassword(Password password) {
        this.password = password;
        return this;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public SysUser setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Ip getLastLoginIp() {
        return lastLoginIp;
    }

    public SysUser setLastLoginIp(Ip lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
        return this;
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public SysUser setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
        return this;
    }

    public Ip getRegisterIp() {
        return registerIp;
    }

    public SysUser setRegisterIp(Ip registerIp) {
        this.registerIp = registerIp;
        return this;
    }

    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public SysUser setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
        return this;
    }

    public UserState getState() {
        return state;
    }

    public SysUser setState(UserState state) {
        this.state = state;
        return this;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void addRole(SysRole sysRole) {
        roles.add(sysRole);
    }

    public void addAllRole(List<SysRole> sysRoles) {
        roles.addAll(sysRoles);
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", username=" + username +
                ", password=" + password +
                ", phoneNumber=" + phoneNumber +
                ", lastLoginIp=" + lastLoginIp +
                ", lastLoginTime=" + lastLoginTime +
                ", registerIp=" + registerIp +
                ", registerTime=" + registerTime +
                ", state=" + state +
                ", roles=" + roles +
                '}';
    }
}
