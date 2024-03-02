package io.github.hellovie.snapvids.domain.auth.strategy.impl.username;

import io.github.hellovie.snapvids.common.context.Context;
import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.common.exception.business.ServiceException;
import io.github.hellovie.snapvids.common.module.user.UserExceptionType;
import io.github.hellovie.snapvids.domain.auth.entity.Account;
import io.github.hellovie.snapvids.domain.auth.entity.SysUser;
import io.github.hellovie.snapvids.domain.auth.repository.AuthRepository;
import io.github.hellovie.snapvids.domain.auth.strategy.AuthStrategy;
import io.github.hellovie.snapvids.domain.auth.strategy.LoginParams;
import io.github.hellovie.snapvids.domain.auth.strategy.RegisterParams;
import io.github.hellovie.snapvids.domain.auth.strategy.annotation.AuthStrategyMark;
import io.github.hellovie.snapvids.domain.util.ContextHolder;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.UserState;
import io.github.hellovie.snapvids.types.common.Ip;
import io.github.hellovie.snapvids.types.user.Password;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * 「用户名」用户认证策略。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Component("usernameAuthStrategy")
@AuthStrategyMark(type = AuthStrategy.AuthType.USERNAME_AUTH_STRATEGY)
public class UsernameAuthStrategy implements AuthStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(UsernameAuthStrategy.class);

    @Resource(name = "authRepository")
    private AuthRepository repository;

    /**
     * {@inheritDoc}
     *
     * @see AuthStrategy#register(RegisterParams)
     */
    @Override
    public Account register(RegisterParams<?> registerParams) {
        UsernameRegisterParams params = (UsernameRegisterParams) registerParams.getParams();
        LOG.info("[通过用户名和密码注册账号]>>> 用户名={}，密码={}，手机号码={}", params.getUsername().getValue(),
                params.getPassword().getCiphertext(), params.getPhoneNumber().getMaskedNumber());

        Account account = repository.findAccountByUsername(params.getUsername());
        if (account != null) {
            throw new ServiceException(UserExceptionType.USERNAME_ALREADY_EXIST);
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());

        Context context = ContextHolder.getContext();
        int ip = context != null ? Ip.ip2Int(context.getRemoteIp()) : 0;
        SysUser sysUser = new SysUser(params.getUsername(), params.getPassword(), params.getPhoneNumber(),
                new Ip(ip), now, new Ip(ip), now, UserState.ENABLE);
        sysUser.addAllRole(getBaseSysRole());

        SysUser saveUser = repository.saveSysUser(sysUser);
        return Account.toAccount(saveUser);
    }

    /**
     * {@inheritDoc}
     *
     * @see AuthStrategy#login(LoginParams)
     */
    @Override
    public Account login(LoginParams<?> loginParams) {
        UsernameLoginParams params = (UsernameLoginParams) loginParams.getParams();
        LOG.info("[通过用户名和密码登录账号]>>> 用户名={}，密码={}",
                params.getUsername().getValue(), params.getPassword().getCiphertext());

        Account account = repository.findAccountByUsername(params.getUsername());
        if (account == null) {
            throw new ServiceException(UserExceptionType.USER_NOT_FOUND);
        }

        // 验证密码是否正确
        Password rowPassword = params.getPassword();
        if (!rowPassword.hasPlaintext()) {
            throw new DataException(UserExceptionType.WRONG_PASSWORD);
        }
        account.getPassword().verifyPassword(rowPassword.getPlaintext());

        Timestamp now = new Timestamp(System.currentTimeMillis());
        Context context = ContextHolder.getContext();
        int ip = context != null ? Ip.ip2Int(context.getRemoteIp()) : 0;

        // 更新用户登录信息
        repository.updateLoginInfoByUsername(account.getUsername(), new Ip(ip), now);

        return new Account(account.getId(), account.getUsername(), account.getPassword(), account.getPhoneNumber(),
                new Ip(ip), now, account.getRegisterIp(), account.getLastLoginTime(), account.getState()
        );
    }
}
