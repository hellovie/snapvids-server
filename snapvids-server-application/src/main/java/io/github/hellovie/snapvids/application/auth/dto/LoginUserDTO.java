package io.github.hellovie.snapvids.application.auth.dto;

import io.github.hellovie.snapvids.domain.auth.entity.Account;
import io.github.hellovie.snapvids.domain.auth.vo.LoginInfo;
import io.github.hellovie.snapvids.domain.auth.vo.TokenInfo;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.common.Ip;
import io.github.hellovie.snapvids.types.user.Username;

import java.sql.Timestamp;

/**
 * 登录用户 DTO。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class LoginUserDTO {

    /**
     * id
     */
    private final Long id;

    /**
     * 用户名
     */
    private final String username;

    /**
     * 最后登录 ip
     */
    private final String lastLoginIp;

    /**
     * 最后登录时间
     */
    private final Timestamp lastLoginTime;

    /**
     * 注册 ip
     */
    private final String registerIp;

    /**
     * 注册时间
     */
    private final Timestamp registerTime;

    /**
     * 访问令牌
     */
    private final String accessToken;

    /**
     * 刷新令牌
     */
    private final String refreshToken;

    /**
     * 刷新令牌的有效时间（单位：秒）
     */
    private final Long expiresInSeconds;


    public LoginUserDTO(Long id, String username, String lastLoginIp, Timestamp lastLoginTime, String registerIp,
                        Timestamp registerTime, String accessToken, String refreshToken, Long expiresInSeconds) {

        this.id = id;
        this.username = username;
        this.lastLoginIp = lastLoginIp;
        this.lastLoginTime = lastLoginTime;
        this.registerIp = registerIp;
        this.registerTime = registerTime;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresInSeconds = expiresInSeconds;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Long getExpiresInSeconds() {
        return expiresInSeconds;
    }

    /**
     * 转换器
     */
    public static class Convertor {

        private Convertor() {}

        public static LoginUserDTO toLoginUserDTO(LoginInfo loginInfo) {
            if (loginInfo == null) {
                return null;
            }

            Account account = loginInfo.getAccount();
            TokenInfo tokenInfo = loginInfo.getTokenInfo();
            return to(account.getId(), account.getUsername(), account.getLastLoginIp(), account.getLastLoginTime(),
                    account.getRegisterIp(), account.getRegisterTime(), tokenInfo
            );
        }

        private static LoginUserDTO to(Id id, Username username, Ip lastLoginIp, Timestamp lastLoginTime, Ip registerIp,
                                       Timestamp registerTime, TokenInfo tokenInfo) {
            long _id = id != null ? id.getValue() : 0L;
            String _username = username != null ? username.getValue() : "no username";
            String _lastLoginIp = lastLoginIp != null ? lastLoginIp.getAddress() : "no last login ip";
            String _registerIp = registerIp != null ? registerIp.getAddress() : "no register ip";
            String accessToken = tokenInfo != null ? tokenInfo.getAccessToken() : "no access token";
            String refreshToken = tokenInfo != null ? tokenInfo.getRefreshToken() : "no refresh token";
            long expiresInSeconds = tokenInfo != null ? tokenInfo.getExpiresInSeconds() : 0L;
            return new LoginUserDTO(_id, _username, _lastLoginIp, lastLoginTime, _registerIp, registerTime, accessToken,
                    refreshToken, expiresInSeconds
            );
        }
    }
}
