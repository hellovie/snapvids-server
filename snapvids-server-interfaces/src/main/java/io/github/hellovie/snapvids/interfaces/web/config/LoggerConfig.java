package io.github.hellovie.snapvids.interfaces.web.config;

import com.github.ksuid.KsuidGenerator;
import io.github.hellovie.snapvids.common.context.Context;
import io.github.hellovie.snapvids.domain.util.ContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

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
    private static final String TRACE_ID = "traceId";

    /**
     * 获取链路跟踪 id
     *
     * @return 链路跟踪 id
     */
    public static String getTraceId() {
        // Todo：需要修复子线程无法获取主线程 trace id 的问题。
        return MDC.get(TRACE_ID);
    }

    /**
     * 「优先从上下文获取」从上层请求获取链路跟踪id，不存在则生成链路跟踪 id。
     *
     * @param request 上层请求
     * @return 链路跟踪 id
     */
    public static String generateTraceId(HttpServletRequest request) {
        Context context = ContextHolder.getContext();
        if (context != null && StringUtils.isNotBlank(context.getTraceId())) {
            MDC.put(TRACE_ID, context.getTraceId());
            return context.getTraceId();
        }

        String traceId = request.getHeader(TRACE_ID);
        if (StringUtils.isBlank(traceId)) {
            traceId = KsuidGenerator.generate();
        }
        MDC.put(TRACE_ID, traceId);
        return traceId;
    }

    /**
     * 「优先从上下文获取」从上层请求获取链路跟踪id，不存在则生成链路跟踪 id。
     *
     * @param traceId 上层请求链路跟踪 id
     * @return 链路跟踪 id
     */
    public static String generateTraceId(String traceId) {
        Context context = ContextHolder.getContext();
        if (context != null && StringUtils.isNotBlank(context.getTraceId())) {
            MDC.put(TRACE_ID, context.getTraceId());
            return context.getTraceId();
        }

        if (StringUtils.isBlank(traceId)) {
            traceId = KsuidGenerator.generate();
        }
        MDC.put(TRACE_ID, traceId);
        return traceId;
    }
}
