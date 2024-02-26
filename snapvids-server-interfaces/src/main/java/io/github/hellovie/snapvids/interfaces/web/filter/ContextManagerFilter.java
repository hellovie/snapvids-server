package io.github.hellovie.snapvids.interfaces.web.filter;

import io.github.hellovie.snapvids.common.context.Context;
import io.github.hellovie.snapvids.domain.util.ContextHolder;
import io.github.hellovie.snapvids.interfaces.web.config.LoggerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static io.github.hellovie.snapvids.interfaces.web.config.RequestConstants.AUTHORIZATION;

/**
 * 生成上下文的拦截器，所有 Controller 接口的请求都必须经过此过滤器，该拦截器在用户认证之前。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class ContextManagerFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(ContextManagerFilter.class);

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws ServletException, IOException {

        long start = System.currentTimeMillis();
        Context context = parseRequest((HttpServletRequest) servletRequest);
        LOG.info("[The request begins]>>> Context format\n{}", context.format());
        ContextHolder.setContext(context);

        // 跳转下一个过滤链
        filterChain.doFilter(servletRequest, servletResponse);

        // 请求结束
        long end = System.currentTimeMillis();
        long time = end - start;
        LOG.info("[The request has been closed]>>> api={}, time={}ms", context.getApi(), time);
        ContextHolder.clear();
    }

    /**
     * 从请求中解析出 {@link Context} 对象。
     *
     * @param request 请求
     * @return 上下文对象
     */
    private Context parseRequest(HttpServletRequest request) {
        String traceId = LoggerConfig.generateTraceId(request);
        String token = request.getHeader(AUTHORIZATION);
        String method = request.getMethod();
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();
        String servletPath = request.getServletPath();
        String remoteHost = request.getRemoteHost();
        return new Context(traceId, token, method, requestURL, queryString, servletPath, remoteHost);
    }
}
