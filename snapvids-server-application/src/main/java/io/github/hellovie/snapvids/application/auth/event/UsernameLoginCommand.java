package io.github.hellovie.snapvids.application.auth.event;

import io.github.hellovie.snapvids.types.common.Captcha;
import io.github.hellovie.snapvids.types.user.Password;
import io.github.hellovie.snapvids.types.user.Username;

/**
 * 登录命令。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class UsernameLoginCommand {

    /**
     * 用户名
     */
    private final Username username;

    /**
     * 密码
     */
    private final Password password;

    /**
     * 验证码
     */
    private final Captcha captcha;

    public UsernameLoginCommand(Username username, Password password, Captcha captcha) {
        this.username = username;
        this.password = password;
        this.captcha = captcha;
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public Captcha getCaptcha() {
        return captcha;
    }
}
