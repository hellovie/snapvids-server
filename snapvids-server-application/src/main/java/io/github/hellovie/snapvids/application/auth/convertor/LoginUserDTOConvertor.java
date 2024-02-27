package io.github.hellovie.snapvids.application.auth.convertor;

import io.github.hellovie.snapvids.application.auth.dto.LoginUserDTO;
import io.github.hellovie.snapvids.domain.auth.entity.Account;
import io.github.hellovie.snapvids.domain.auth.vo.LoginInfo;
import io.github.hellovie.snapvids.domain.auth.vo.TokenInfo;
import org.springframework.stereotype.Component;

/**
 * {@link LoginUserDTO} 转换器。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Component("loginUserDTOConvertor")
public class LoginUserDTOConvertor {

    public LoginUserDTO toLoginUserDTO(LoginInfo loginInfo) {
        if (loginInfo == null) {
            return null;
        }

        Account account = loginInfo.getAccount();
        TokenInfo tokenInfo = loginInfo.getTokenInfo();
        return new LoginUserDTO(
                account.getId().getValue(),
                account.getUsername().getValue(),
                account.getLastLoginIp().getAddress(),
                account.getLastLoginTime(),
                account.getRegisterIp().getAddress(),
                account.getRegisterTime(),
                tokenInfo.getAccessToken(),
                tokenInfo.getRefreshToken(),
                tokenInfo.getExpiresInSeconds()
        );
    }
}
