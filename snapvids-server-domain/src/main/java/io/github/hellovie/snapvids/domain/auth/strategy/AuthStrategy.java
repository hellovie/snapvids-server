package io.github.hellovie.snapvids.domain.auth.strategy;

import io.github.hellovie.snapvids.domain.auth.entity.Account;
import io.github.hellovie.snapvids.domain.auth.entity.SysRole;

import java.util.ArrayList;
import java.util.List;

import static io.github.hellovie.snapvids.domain.auth.enums.RoleType.NORMAL_USER;

/**
 * 用户认证策略接口。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface AuthStrategy {

    /**
     * 用户注册。
     *
     * @param registerParams 用户注册所需信息。
     * @return 用户账号信息
     */
    Account register(RegisterParams<?> registerParams);

    /**
     * 用户登录。
     *
     * @param loginParams 用户登录所需信息。
     * @return 用户账号信息
     */
    Account login(LoginParams<?> loginParams);

    /**
     * 注册用户时，每个用户基础的用户角色。
     *
     * @return 用户角色列表
     */
    default List<SysRole> getBaseSysRole() {
        List<SysRole> roles = new ArrayList<>(1);
        roles.add(new SysRole(
                NORMAL_USER.getId(),
                NORMAL_USER.getRoleKey(),
                NORMAL_USER.getRoleName()
        ));
        return roles;
    }

    /**
     * 认证类型。
     * <p>一个认证类型对应着一个认证策略实现类。</p>
     */
    enum AuthType {

        /**
         * 用户名认证策略
         */
        USERNAME_AUTH_STRATEGY,

        ;
    }
}
