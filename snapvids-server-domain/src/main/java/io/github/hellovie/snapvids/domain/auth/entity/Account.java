package io.github.hellovie.snapvids.domain.auth.entity;

import io.github.hellovie.snapvids.infrastructure.persistence.enums.UserState;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.common.Ip;
import io.github.hellovie.snapvids.types.common.PhoneNumber;
import io.github.hellovie.snapvids.types.user.Password;
import io.github.hellovie.snapvids.types.user.Username;

import java.sql.Timestamp;

/**
 * 用户账号信息。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class Account {

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

    public Account(Id id, Username username, Password password, PhoneNumber phoneNumber, Ip lastLoginIp,
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

    /**
     * {@link SysUser} to {@link Account}.
     *
     * @param sysUser {@link SysUser}
     * @return {@link Account}
     */
    public static Account toAccount(SysUser sysUser) {
        if (sysUser == null) {
            return null;
        }

        return new Account(
                sysUser.getId(),
                sysUser.getUsername(),
                sysUser.getPassword(),
                sysUser.getPhoneNumber(),
                sysUser.getLastLoginIp(),
                sysUser.getLastLoginTime(),
                sysUser.getRegisterIp(),
                sysUser.getRegisterTime(),
                sysUser.getState()
        );
    }

    public Id getId() {
        return id;
    }

    public Account setId(Id id) {
        this.id = id;
        return this;
    }

    public Username getUsername() {
        return username;
    }

    public Account setUsername(Username username) {
        this.username = username;
        return this;
    }

    public Password getPassword() {
        return password;
    }

    public Account setPassword(Password password) {
        this.password = password;
        return this;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public Account setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Ip getLastLoginIp() {
        return lastLoginIp;
    }

    public Account setLastLoginIp(Ip lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
        return this;
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public Account setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
        return this;
    }

    public Ip getRegisterIp() {
        return registerIp;
    }

    public Account setRegisterIp(Ip registerIp) {
        this.registerIp = registerIp;
        return this;
    }

    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public Account setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
        return this;
    }

    public UserState getState() {
        return state;
    }

    public Account setState(UserState state) {
        this.state = state;
        return this;
    }
}
