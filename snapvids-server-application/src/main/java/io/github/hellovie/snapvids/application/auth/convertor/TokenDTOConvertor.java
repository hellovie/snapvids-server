package io.github.hellovie.snapvids.application.auth.convertor;

import io.github.hellovie.snapvids.application.auth.dto.TokenDTO;
import io.github.hellovie.snapvids.domain.auth.vo.TokenInfo;
import org.springframework.stereotype.Component;

/**
 * {@link TokenDTO} 转换器。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Component("tokenDTOConvertor")
public class TokenDTOConvertor {

    public TokenDTO toTokenDTO(TokenInfo tokenInfo) {
        if (tokenInfo == null) {
            return null;
        }

        return new TokenDTO(tokenInfo.getAccessToken(), tokenInfo.getRefreshToken(), tokenInfo.getExpiresInSeconds());
    }
}
