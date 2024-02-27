package io.github.hellovie.snapvids.interfaces.web.request;

/**
 * 用户名登录表单。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class UsernameLoginRequest {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
