package io.github.hellovie.snapvids.application.auth.event;

import io.github.hellovie.snapvids.types.common.PhoneNumber;
import io.github.hellovie.snapvids.types.user.Password;
import io.github.hellovie.snapvids.types.user.Username;

/**
 * 注册命令。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class UsernameRegisterCommand {

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

    public UsernameRegisterCommand(String username, String password, String phoneNumber) {
        this.username = new Username(username);
        this.password = Password.ofPlaintext(password);
        this.phoneNumber = new PhoneNumber(phoneNumber);
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
}
