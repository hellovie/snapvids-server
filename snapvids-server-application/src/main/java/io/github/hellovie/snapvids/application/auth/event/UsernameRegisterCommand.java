package io.github.hellovie.snapvids.application.auth.event;

import io.github.hellovie.snapvids.types.common.Captcha;
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

    /**
     * 验证码
     */
    private final Captcha captcha;

    public UsernameRegisterCommand(String username, String password, String phoneNumber, String captcha) {
        this.username = new Username(username);
        this.password = Password.ofPlaintext(password);
        this.phoneNumber = new PhoneNumber(phoneNumber);
        this.captcha = new Captcha(phoneNumber, captcha);
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

    public Captcha getCaptcha() {
        return captcha;
    }
}
