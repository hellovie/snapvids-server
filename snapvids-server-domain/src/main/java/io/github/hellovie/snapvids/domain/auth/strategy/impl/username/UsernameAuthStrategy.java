package io.github.hellovie.snapvids.domain.auth.strategy.impl.username;

import io.github.hellovie.snapvids.common.exception.business.ServiceException;
import io.github.hellovie.snapvids.common.module.user.UserExceptionType;
import io.github.hellovie.snapvids.domain.auth.entity.Account;
import io.github.hellovie.snapvids.domain.auth.entity.SysUser;
import io.github.hellovie.snapvids.domain.auth.repository.AuthRepository;
import io.github.hellovie.snapvids.domain.auth.strategy.AuthStrategy;
import io.github.hellovie.snapvids.domain.auth.strategy.LoginParams;
import io.github.hellovie.snapvids.domain.auth.strategy.RegisterParams;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.UserState;
import io.github.hellovie.snapvids.types.common.Ip;
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
@Component("USERNAME_AUTH_STRATEGY")
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
        LOG.info("[register by username]>>> username={}, password={}, phoneNumber={}", params.getUsername().getValue(),
                params.getPassword().getCiphertext(), params.getPhoneNumber().getMaskedNumber());

        Account account = repository.findAccountByUsername(params.getUsername());
        if (account != null) {
            throw new ServiceException(UserExceptionType.USER_ALREADY_EXIST);
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());
        // Todo: get ip from request context
        int ip = 16777343;
        SysUser sysUser = new SysUser(
                params.getUsername().getValue(), params.getPassword().getCiphertext(), params.getPassword().getSalt(),
                params.getPhoneNumber().getNumber(), ip, now, ip, now, UserState.ENABLE);
        sysUser.addAllRole(getBaseSysRole());

        SysUser saveUser = repository.saveSysUser(sysUser);
        return new Account(
                saveUser.getId().getValue(),
                saveUser.getUsername().getValue(),
                saveUser.getPassword().getCiphertext(),
                saveUser.getPassword().getSalt(),
                saveUser.getPhoneNumber().getNumber(),
                saveUser.getLastLoginIp().getIntAddress(),
                saveUser.getLastLoginTime(),
                saveUser.getRegisterIp().getIntAddress(),
                saveUser.getRegisterTime(),
                saveUser.getState()
        );
    }

    /**
     * {@inheritDoc}
     *
     * @see AuthStrategy#login(LoginParams)
     */
    @Override
    public Account login(LoginParams<?> loginParams) {
        UsernameLoginParams params = (UsernameLoginParams) loginParams.getParams();
        LOG.info("[login by username]>>> username={}, password={}",
                params.getUsername().getValue(), params.getPassword().getCiphertext());

        Account account = repository.findAccountByUsername(params.getUsername());
        if (account == null) {
            throw new ServiceException(UserExceptionType.USER_NOT_FOUND);
        }

        // 验证密码是否正确
        params.getPassword().verifyPassword(account.getPassword());

        Timestamp now = new Timestamp(System.currentTimeMillis());
        // Todo: get ip from request context
        int ip = 16777343;

        // 更新用户登录信息
        repository.updateLoginInfoByUsername(account.getUsername(), new Ip(ip), now);

        return account;
    }
}
