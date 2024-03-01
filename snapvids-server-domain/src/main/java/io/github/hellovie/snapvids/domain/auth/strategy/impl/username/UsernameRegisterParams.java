package io.github.hellovie.snapvids.domain.auth.strategy.impl.username;

import io.github.hellovie.snapvids.types.common.PhoneNumber;
import io.github.hellovie.snapvids.types.user.Password;
import io.github.hellovie.snapvids.types.user.Username;

/**
 * 用户名注册所需参数。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class UsernameRegisterParams {

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
     * 构造合理的 {@link UsernameRegisterParams} 对象。
     *
     * @param username    用户名
     * @param password    密码
     * @param phoneNumber 手机号码
     */
    public UsernameRegisterParams(Username username, Password password, PhoneNumber phoneNumber) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
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

    @Override
    public String toString() {
        return "UsernameRegisterParams{" +
                "username=" + username +
                ", password=" + password +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
