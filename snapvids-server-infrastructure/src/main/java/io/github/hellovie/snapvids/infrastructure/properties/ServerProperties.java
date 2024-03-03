package io.github.hellovie.snapvids.infrastructure.properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Yaml 文件注入服务器信息。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Component("serverProperties")
public class ServerProperties {

    private static final Logger LOG = LoggerFactory.getLogger(ServerProperties.class);

    /**
     * 服务器协议
     */
    private final String protocol;

    /**
     * 服务器域名
     */
    private final String domainName;

    /**
     * 服务器端口号
     */
    private final Integer port;

    /**
     * 完整路径
     */
    private final String url;

    public ServerProperties(@Value("${snapvids.server.protocol}") String protocol,
                            @Value("${snapvids.server.domain-name}") String domainName,
                            @Value("${server.servlet.context-path}") String contextPath,
                            @Value("${server.port}") Integer port) {

        // server.servlet.context-path 不存在会抛异常
        if (StringUtils.isBlank(protocol)) {
            LOG.error("[注入服务器协议失败]>>> 协议={}", protocol);
            throw new RuntimeException("注入服务器变量失败");
        }
        if (StringUtils.isBlank(domainName)) {
            LOG.error("[注入服务器域名失败]>>> 域名={}", domainName);
            throw new RuntimeException("注入服务器变量失败");
        }
        if (port == null || port < 1024 || port > 65535) {
            LOG.error("[注入服务器端口号失败]>>> 端口号={}", port);
            throw new RuntimeException("注入服务器变量失败");
        }

        this.protocol = protocol;
        this.domainName = domainName;
        this.port = port;
        this.url = protocol + "://" + domainName + ":" + port + (StringUtils.isNotBlank(contextPath) ? contextPath : "");
        LOG.info("[注入服务器变量成功]>>> 服务器地址={}", this.url);
    }

    public String getProtocol() {
        return protocol;
    }

    public String getDomainName() {
        return domainName;
    }

    public Integer getPort() {
        return port;
    }

    public String getUrl() {
        return url;
    }
}
