package io.github.hellovie.snapvids.application.auth.event;

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

    public UsernameLoginCommand(String username, String password) {
        this.username = new Username(username);
        this.password = Password.ofPlaintext(password);
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }
}
