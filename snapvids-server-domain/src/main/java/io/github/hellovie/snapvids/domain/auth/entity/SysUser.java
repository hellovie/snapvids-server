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
    private final Id id;

    /**
     * 用户名
     */
    private final Username username;

    /**
     * 密码
     */
    private final Password password;

    /**
     * 手机号码
     */
    private final PhoneNumber phoneNumber;

    /**
     * 最后登录 ip
     */
    private final Ip lastLoginIp;

    /**
     * 最后登录时间
     */
    private final Timestamp lastLoginTime;

    /**
     * 注册 ip
     */
    private final Ip registerIp;

    /**
     * 注册时间
     */
    private final Timestamp registerTime;

    /**
     * 用户状态
     */
    private final UserState state;

    /**
     * 用户角色
     */
    private final List<SysRole> roles = new ArrayList<>();

    public SysUser(String username, String ciphertext, String phoneNumber, int lastLoginIp,
                   Timestamp lastLoginTime, int registerIp, Timestamp registerTime, UserState state) {

        this.id = null;
        this.username = new Username(username);
        this.password = Password.ofCiphertext(ciphertext);
        this.phoneNumber = new PhoneNumber(phoneNumber);
        this.lastLoginIp = new Ip(lastLoginIp);
        this.lastLoginTime = lastLoginTime;
        this.registerIp = new Ip(registerIp);
        this.registerTime = registerTime;
        this.state = state;
    }

    public SysUser(long id, String username, String ciphertext, String phoneNumber, int lastLoginIp,
                   Timestamp lastLoginTime, int registerIp, Timestamp registerTime, UserState state) {

        this.id = new Id(id);
        this.username = new Username(username);
        this.password = Password.ofCiphertext(ciphertext);
        this.phoneNumber = new PhoneNumber(phoneNumber);
        this.lastLoginIp = new Ip(lastLoginIp);
        this.lastLoginTime = lastLoginTime;
        this.registerIp = new Ip(registerIp);
        this.registerTime = registerTime;
        this.state = state;
    }

    public Id getId() {
        return id;
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public Ip getLastLoginIp() {
        return lastLoginIp;
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public Ip getRegisterIp() {
        return registerIp;
    }

    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public UserState getState() {
        return state;
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
                "id=" + id.getValue() +
                ", username=" + username.getValue() +
                ", password=" + password.getCiphertext() +
                ", phoneNumber=" + phoneNumber.getMaskedNumber() +
                ", lastLoginIp=" + lastLoginIp.getAddress() +
                ", lastLoginTime=" + lastLoginTime +
                ", registerIp=" + registerIp.getAddress() +
                ", registerTime=" + registerTime +
                ", state=" + state +
                ", roles=" + roles +
                '}';
    }
}
