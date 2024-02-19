package io.github.hellovie.snapvids.infrastructure.service.token;

import io.github.hellovie.snapvids.common.service.TokenService;
import io.github.hellovie.snapvids.infrastructure.config.InfrastructureBeanConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Token Service Test.
 *
 * @author hellovie
 * @since 1.0.0
 */
@SpringBootTest(classes = InfrastructureBeanConfig.class)
public class TokenServiceTest {

    @Resource(name = "jwtTokenService")
    private TokenService jwtTokenService;

    @Test
    void testJwtTokenService() {
        final String secret = "snapvids";
        final long expireInSeconds = 60L;
        HashMap<String, String> payload = new HashMap<>(2);
        payload.put("username", "hellovie");
        payload.put("website", "https://hellovie.github.io");

        // 验证 create
        String token = jwtTokenService.create(payload, expireInSeconds, secret);
        System.out.println("token = " + token);
        Assertions.assertNotEquals("", token, "JwtTokenService create token failed");

        // 验证 verify
        boolean verify = jwtTokenService.verify(secret, token);
        Assertions.assertTrue(verify, "JwtTokenService verify token failed");

        // 验证 getPayload
        Map<String, String> claims = jwtTokenService.getPayload(secret, token);
        Assertions.assertEquals("hellovie", claims.get("username"),
                "JwtTokenService get token payload 'username' failed");
        Assertions.assertEquals("https://hellovie.github.io", claims.get("website"),
                "JwtTokenService get token payload 'website' failed");
    }
}