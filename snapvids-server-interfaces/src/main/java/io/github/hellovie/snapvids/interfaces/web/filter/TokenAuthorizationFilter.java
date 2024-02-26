package io.github.hellovie.snapvids.interfaces.web.filter;

import io.github.hellovie.snapvids.domain.auth.entity.SysUser;
import io.github.hellovie.snapvids.domain.auth.service.AuthService;
import io.github.hellovie.snapvids.domain.util.ContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.hellovie.snapvids.interfaces.web.config.SecurityConfig.ALLOWED_INTERFACES;

/**
 * Token 认证过滤器。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class TokenAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger LOG = LoggerFactory.getLogger(TokenAuthorizationFilter.class);

    /**
     * 认证服务
     */
    private final AuthService authService;

    /**
     * 接口匹配工具
     */
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public TokenAuthorizationFilter(AuthenticationManager authenticationManager, AuthService authService) {
        super(authenticationManager);
        this.authService = authService;
    }

    /**
     * 具体拦截逻辑。
     *
     * @param request  {@link HttpServletRequest}.
     * @param response {@link HttpServletResponse}.
     * @param chain    {@link FilterChain}.
     * @throws IOException      {@link IOException}.
     * @throws ServletException {@link ServletException}.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        // 放行接口不拦截
        for (String allowedInterface : ALLOWED_INTERFACES) {
            boolean match = pathMatcher.match(allowedInterface, path);
            if (match) {
                chain.doFilter(request, response);
                return;
            }
        }

        // 认证逻辑内部会抛出异常，这里无需处理
        // Todo：需要解决在过滤器抛出异常时，自动转发到 ”/error“ 的问题。
        SysUser curUser = authService.auth();
        String username = curUser.getUsername().getValue();
        List<GrantedAuthority> authorities = curUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleKey().getValue()))
                .collect(Collectors.toList());
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(username, null, authorities);

        // 将当前请求用户存储在 ContextHolder 中
        ContextHolder.setUser(curUser);
        // 将认证信息存储在 SecurityContextHolder 中
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        LOG.info("[The user authentication is successful]>>> username={}", username);
        chain.doFilter(request, response);
    }
}
