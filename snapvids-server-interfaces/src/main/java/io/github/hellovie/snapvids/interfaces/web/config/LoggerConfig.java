package io.github.hellovie.snapvids.interfaces.web.config;

import org.springframework.context.annotation.Configuration;

/**
 * 日志配置。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Configuration
public class LoggerConfig {

    /**
     * 日志全链路跟踪 ID
     */
    public static final String TRACE_ID = "traceId";
}
