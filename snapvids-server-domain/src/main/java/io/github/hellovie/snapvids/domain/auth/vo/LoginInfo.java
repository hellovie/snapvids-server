package io.github.hellovie.snapvids.domain.auth.vo;

import io.github.hellovie.snapvids.domain.auth.entity.Account;

/**
 * 用户登录信息。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class LoginInfo {

    /**
     * 用户账号信息
     */
    private final Account account;

    /**
     * 登录令牌
     */
    private final TokenInfo tokenInfo;

    public LoginInfo(Account account, TokenInfo tokenInfo) {
        this.account = account;
        this.tokenInfo = tokenInfo;
    }

    public Account getAccount() {
        return account;
    }

    public TokenInfo getTokenInfo() {
        return tokenInfo;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "account=" + account +
                ", tokenInfo=" + tokenInfo +
                '}';
    }
}
