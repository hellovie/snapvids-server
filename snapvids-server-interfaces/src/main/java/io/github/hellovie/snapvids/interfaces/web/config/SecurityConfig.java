package io.github.hellovie.snapvids.interfaces.web.config;

import io.github.hellovie.snapvids.domain.auth.service.AuthService;
import io.github.hellovie.snapvids.interfaces.web.filter.ContextManagerFilter;
import io.github.hellovie.snapvids.interfaces.web.filter.TokenAuthorizationFilter;
import io.github.hellovie.snapvids.interfaces.web.handler.AuthenticationExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collections;

/**
 * Spring Security 配置类。
 * <p>过滤链顺序：</p>
 * <ol>
 *     <li>org.springframework.security.web.session.DisableEncodeUrlFilter</li>
 *     <li>org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter</li>
 *     <li>org.springframework.security.web.context.SecurityContextPersistenceFilter</li>
 *     <li>org.springframework.security.web.header.HeaderWriterFilter</li>
 *     <li>org.springframework.web.filter.CorsFilter</li>
 *     <li>io.github.hellovie.snapvids.interfaces.web.filter.ContextManagerFilter</li>
 *     <li>io.github.hellovie.snapvids.interfaces.web.filter.TokenAuthorizationFilter</li>
 *     <li>org.springframework.security.web.savedrequest.RequestCacheAwareFilter</li>
 *     <li>org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter</li>
 *     <li>org.springframework.security.web.authentication.AnonymousAuthenticationFilter</li>
 *     <li>org.springframework.security.web.session.SessionManagementFilter</li>
 *     <li>org.springframework.security.web.access.ExceptionTranslationFilter</li>
 *     <li>org.springframework.security.web.access.intercept.FilterSecurityInterceptor</li>
 * </ol>
 *
 * @author hellovie
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    /**
     * 接口放行
     */
    public static final String[] ALLOWED_INTERFACES = {
            "/login/**",
            "/register/**",
            "/logout/**",
            "/tokens/refresh"
    };

    /**
     * 静态资源放行
     */
    public static final String[] STATIC_RESOURCES = {
            "/favicon.ico",
    };

    /**
     * 匿名用户访问无权限资源时的异常
     */
    @Resource(name = "userAuthenticationEntryPoint")
    private AuthenticationExceptionHandler authenticationEntryPoint;

    /**
     * 认证服务
     */
    @Resource(name = "redisJwtAuthService")
    private AuthService authService;

    /**
     * 获取 AuthenticationManager（认证管理器），登录时认证使用。
     * <p>用来将自定义 AuthenticationManager 在工厂中进行暴露，新版使用这种方式不会导致单元测试中依赖循环。</p>
     *
     * @param http {@link HttpSecurity}
     * @return {@link AuthenticationManager}
     */
    @Bean(name = "authenticationManagerBean")
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        return authenticationManagerBuilder.build();
    }

    /**
     * Spring Security 配置。
     *
     * @param http                      {@link HttpSecurity}
     * @param authenticationManagerBean {@link AuthenticationManager}
     * @return {@link SecurityFilterChain}
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManagerBean) throws Exception {
        return http
                // 关闭自带 logout 接口，避免跳转到 /login?logout
                .logout().disable()
                // 基于 token，不需要 session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 基于 token，不需要 csrf
                .csrf().disable()
                // 开启跨域以便前端调用接口
                .cors().configurationSource(corsConfigurationSource()).and()
                // 放行接口
                .authorizeRequests()
                .antMatchers(ALLOWED_INTERFACES).permitAll()
                .antMatchers(STATIC_RESOURCES).permitAll()
                // 其它所有接口需要认证才能访问
                .anyRequest().authenticated().and()
                // 认证拦截器
                .addFilter(new TokenAuthorizationFilter(authenticationManagerBean, authService))
                // 在认证前注册上下文拦截器
                .addFilterBefore(new ContextManagerFilter(), UsernamePasswordAuthenticationFilter.class)
                // 配置异常处理
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
                .build();
    }

    /**
     * 跨域配置.
     *
     * @return {@link CorsConfigurationSource}.
     */
    @Bean(name = "corsConfigurationSource")
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setMaxAge(Duration.ofHours(1));
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}