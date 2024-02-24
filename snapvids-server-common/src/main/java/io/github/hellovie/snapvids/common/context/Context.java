package io.github.hellovie.snapvids.common.context;

/**
 * Todo：调用接口的上下文。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class Context {

    /**
     * 请求头 Authorization
     */
    private String authorization;

    public String getAuthorization() {
        return authorization;
    }

    public Context setAuthorization(String authorization) {
        this.authorization = authorization;
        return this;
    }
}
