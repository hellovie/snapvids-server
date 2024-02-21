package io.github.hellovie.snapvids.infrastructure.properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Yaml 文件注入 JWT 属性。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Component("jwtProperties")
public class JwtProperties {

    private static final Logger LOG = LoggerFactory.getLogger(JwtProperties.class);

    /**
     * 加密密钥
     */
    private final String secret;

    /**
     * 到期时间（单位：秒）
     */
    private final Long expiredInSeconds;

    /**
     * 使用构造器注入，避免空值注入。
     *
     * @param secret           JWT 加密密钥
     * @param expiredInSeconds JWT 过期时间
     */
    public JwtProperties(@Value("${snapvids.jwt.secret}") String secret,
                         @Value("${snapvids.jwt.expired-in-seconds}") Long expiredInSeconds) {

        if (StringUtils.isBlank(secret) || expiredInSeconds == null || expiredInSeconds <= 0) {
            LOG.error("[Failed to inject JWT secret]>>> secret={}", secret);
            LOG.error("[Failed to inject JWT expiredInSeconds]>>> expiredInSeconds={}s", expiredInSeconds);
            throw new RuntimeException("Failed to inject JWT properties.");
        }
        LOG.info("[Inject JWT properties success]>>> secret=***, expiredInSeconds={}s", expiredInSeconds);
        this.secret = secret;
        this.expiredInSeconds = expiredInSeconds;
    }

    public String getSecret() {
        return secret;
    }

    public Long getExpiredInSeconds() {
        return expiredInSeconds;
    }

    @Override
    public String toString() {
        return "JwtProperties{" +
                "secret='" + secret + '\'' +
                ", expiredInSeconds=" + expiredInSeconds +
                '}';
    }
}