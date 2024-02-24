package io.github.hellovie.snapvids.domain.auth.strategy.impl.username;

import io.github.hellovie.snapvids.types.user.Password;
import io.github.hellovie.snapvids.types.user.Username;

/**
 * 用户名登录所需参数。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class UsernameLoginParams {

    /**
     * 用户名
     */
    private final Username username;

    /**
     * 密码
     */
    private final Password password;

    /**
     * 构造合理的 {@link UsernameLoginParams} 对象。
     *
     * @param username 用户名
     * @param password 密码
     */
    public UsernameLoginParams(String username, String password) {
        this.username = new Username(username);
        this.password = Password.buildPlaintext(password);
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UsernameLoginParams{" +
                "username=" + username.getValue() +
                ", password=" + password.getCiphertext() +
                '}';
    }
}
