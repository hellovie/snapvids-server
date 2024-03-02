package io.github.hellovie.snapvids.domain.auth.service.impl;

import io.github.hellovie.snapvids.common.cache.CacheService;
import io.github.hellovie.snapvids.common.context.Context;
import io.github.hellovie.snapvids.common.exception.business.AuthException;
import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.common.exception.business.ServiceException;
import io.github.hellovie.snapvids.common.exception.system.ConfigException;
import io.github.hellovie.snapvids.common.exception.system.UtilException;
import io.github.hellovie.snapvids.common.module.user.UserCacheKey;
import io.github.hellovie.snapvids.common.module.user.UserExceptionType;
import io.github.hellovie.snapvids.common.service.IdGenerateService;
import io.github.hellovie.snapvids.common.service.TokenService;
import io.github.hellovie.snapvids.domain.auth.entity.Account;
import io.github.hellovie.snapvids.domain.auth.entity.SysUser;
import io.github.hellovie.snapvids.domain.auth.enums.TokenType;
import io.github.hellovie.snapvids.domain.auth.repository.AuthRepository;
import io.github.hellovie.snapvids.domain.auth.service.AuthService;
import io.github.hellovie.snapvids.domain.auth.strategy.AuthStrategy;
import io.github.hellovie.snapvids.domain.auth.strategy.LoginParams;
import io.github.hellovie.snapvids.domain.auth.strategy.RegisterParams;
import io.github.hellovie.snapvids.domain.auth.strategy.annotation.AuthStrategyMark;
import io.github.hellovie.snapvids.domain.auth.vo.LoginInfo;
import io.github.hellovie.snapvids.domain.auth.vo.TokenInfo;
import io.github.hellovie.snapvids.domain.util.ContextHolder;
import io.github.hellovie.snapvids.infrastructure.properties.JwtProperties;
import io.github.hellovie.snapvids.types.common.Id;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Redis + JWT 的认证服务实现类。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Service("redisJwtAuthService")
public class RedisJwtAuthService implements AuthService {

    private static final Logger LOG = LoggerFactory.getLogger(RedisJwtAuthService.class);

    /**
     * Authorization 前缀
     */
    private static final String AUTHORIZATION_PREFIX = "Bearer ";

    /**
     * JWT 中存放 用户 ID 的 Claim Key
     */
    private static final String JWT_UID_CLAIM_KEY = "uid";

    /**
     * JWT 中存放 Token ID 的 Claim Key
     */
    private static final String JWT_TOKEN_ID_CLAIM_KEY = "token-id";

    /**
     * JWT 中存放 Token 类型的 Claim Key
     */
    private static final String JWT_TOKEN_TYPE_CLAIM_KEY = "token-type";

    /**
     * JWT Token 配置类
     */
    @Resource(name = "jwtProperties")
    private JwtProperties jwtProperties;

    /**
     * JWT Token 服务
     */
    @Resource(name = "jwtTokenService")
    private TokenService jwtTokenService;

    /**
     * Ksuid 生成器
     */
    @Resource(name = "ksuidGenerator")
    private IdGenerateService<String> ksuidGenerator;

    private final Map<String, AuthStrategy> strategyMap = new ConcurrentHashMap<>(AuthStrategy.AuthType.values().length);

    @Resource(name = "authRepository")
    private AuthRepository repository;

    @Resource(name = "redisCacheService")
    private CacheService cacheService;

    /**
     * 自动装配所有认证策略。
     *
     * @param strategies 认证策略列表
     */
    public RedisJwtAuthService(List<AuthStrategy> strategies) {
        StringBuffer registerStrategy = new StringBuffer();
        strategies.forEach(authStrategy -> {
            AuthStrategyMark strategy = AnnotationUtils.findAnnotation(authStrategy.getClass(), AuthStrategyMark.class);
            if (null != strategy) {
                strategyMap.put(strategy.type().name(), authStrategy);
                registerStrategy.append(strategy.type().name()).append(" ");
            }
        });
        LOG.info("[认证策略已成功注册]>>> [{}]{}", strategyMap.size(), registerStrategy);
    }

    /**
     * {@inheritDoc}
     *
     * @see AuthService#register(RegisterParams)
     */
    @Override
    public LoginInfo register(final RegisterParams<?> registerParams) {
        LOG.info("[用户注册入参]>>> 认证类型={}，注册参数={}", registerParams.getAuthType(), registerParams.getParams());
        AuthStrategy strategy = getAuthStrategy(registerParams.getAuthType());
        Account account = strategy.register(registerParams);
        checkUserState(account);
        TokenInfo tokenInfo = createTokenInfo(account.getId());
        LoginInfo loginInfo = new LoginInfo(account, tokenInfo);
        LOG.info("[用户注册返回值]>>> 登录信息={}", loginInfo);
        return loginInfo;
    }

    /**
     * {@inheritDoc}
     *
     * @see AuthService#login(LoginParams)
     */
    @Override
    public LoginInfo login(final LoginParams<?> loginParams) {
        LOG.info("[用户登录入参]>>> 认证类型={}，登录参数={}", loginParams.getAuthType(), loginParams.getParams());
        AuthStrategy strategy = getAuthStrategy(loginParams.getAuthType());
        Account account = strategy.login(loginParams);
        checkUserState(account);
        TokenInfo tokenInfo = createTokenInfo(account.getId());
        LoginInfo loginInfo = new LoginInfo(account, tokenInfo);
        LOG.info("[用户登录返回值]>>> 登录信息={}", loginInfo);
        return loginInfo;
    }

    /**
     * {@inheritDoc}
     *
     * @see AuthService#logout()
     */
    @Override
    public void logout() {
        Context context = ContextHolder.getContext();
        if (context == null || StringUtils.isBlank(context.getAuthorization()) ||
                !context.getAuthorization().startsWith(AUTHORIZATION_PREFIX)) {
            return;
        }

        String token = context.getAuthorization().replaceFirst(AUTHORIZATION_PREFIX, "");
        try {
            Map<String, String> payload = jwtTokenService.getPayload(jwtProperties.getSecret(), token);
            long userId = Long.parseLong(payload.get(JWT_UID_CLAIM_KEY));
            String tokenId = payload.get(JWT_TOKEN_ID_CLAIM_KEY);
            String type = payload.get(JWT_TOKEN_TYPE_CLAIM_KEY);

            // 无法清除缓存中的令牌的情况，直接返回
            if (!TokenType.ACCESS_TOKEN.getKey().equals(type) || "".equals(tokenId) || userId <= 0L) {
                return;
            }

            repository.removeToken(userId, tokenId);
        } catch (Exception ex) {
            LOG.error("[退出登录发生异常]>>> {}", ex.getMessage(), ex);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see AuthService#auth()
     */
    @Override
    public SysUser auth() throws AuthException {
        Context context = ContextHolder.getContext();
        if (context == null || StringUtils.isBlank(context.getAuthorization()) ||
                !context.getAuthorization().startsWith(AUTHORIZATION_PREFIX)) {

            LOG.info("[获取当前请求的Authorization失败]>>> Authorization={}",
                    context == null ? "上下文为Null" : context.getAuthorization());
            throw new AuthException(UserExceptionType.UNAUTHORIZED);
        }

        // JWT token 校验
        String token = context.getAuthorization().replaceFirst(AUTHORIZATION_PREFIX, "");
        long userId;
        String tokenId;
        String type;
        try {
            Map<String, String> payload = jwtTokenService.getPayload(jwtProperties.getSecret(), token);
            userId = Long.parseLong(payload.get(JWT_UID_CLAIM_KEY));
            tokenId = payload.get(JWT_TOKEN_ID_CLAIM_KEY);
            type = payload.get(JWT_TOKEN_TYPE_CLAIM_KEY);
        } catch (Exception ex) {
            LOG.info("[JWT解析失败]>>> {}", ex.getMessage(), ex);
            throw new AuthException(UserExceptionType.UNAUTHORIZED);
        }
        // 访问令牌才能进行访问操作接口
        if (!TokenType.ACCESS_TOKEN.getKey().equals(type) || "".equals(tokenId) || userId <= 0L) {
            LOG.warn("[获取访问令牌载荷失败]>>> 用户ID={}，令牌ID={}，令牌类型={}", userId, tokenId, type);
            throw new AuthException(UserExceptionType.UNAUTHORIZED);
        }

        // Redis token 校验
        Long expiredTime = repository.getAccessTokenExpiredTime(userId, tokenId);
        if (expiredTime == null) {
            throw new AuthException(UserExceptionType.ACCESS_TOKEN_HAS_EXPIRED);
        }
        long now = new Date().getTime() / 1000;
        // 令牌已经过期
        if (expiredTime <= now) {
            repository.removeAccessToken(userId, tokenId);
            throw new AuthException(UserExceptionType.ACCESS_TOKEN_HAS_EXPIRED);
        }

        // 获取当前请求的系统用户
        SysUser sysUser = repository.findSysUserById(new Id(userId));
        if (sysUser == null) {
            LOG.error("[访问令牌中获取的用户ID有误]>>> 用户ID={}", userId);
            throw new AuthException(UserExceptionType.USER_NOT_FOUND);
        }
        // 校验用户状态
        checkUserState(Account.toAccount(sysUser));
        List<String> roles = sysUser.getRoles().stream()
                .map(role -> role.getRoleKey().getValue())
                .collect(Collectors.toList());
        LOG.info("[用户认证成功]>>> 用户名={}，用户角色={}", sysUser.getUsername().getValue(), roles);
        return sysUser;
    }

    /**
     * {@inheritDoc}
     *
     * @see AuthService#refreshToken()
     */
    @Override
    public TokenInfo refreshToken() throws AuthException {
        Context context = ContextHolder.getContext();
        if (context == null || StringUtils.isBlank(context.getAuthorization()) ||
                !context.getAuthorization().startsWith(AUTHORIZATION_PREFIX)) {

            LOG.info("[获取当前请求的Authorization失败]>>> Authorization={}",
                    context == null ? "上下文为Null" : context.getAuthorization());
            throw new AuthException(UserExceptionType.USER_AUTH_EXCEPTION);
        }

        // JWT token 校验
        String token = context.getAuthorization().replaceFirst(AUTHORIZATION_PREFIX, "");
        long userId;
        String tokenId;
        String type;
        try {
            Map<String, String> payload = jwtTokenService.getPayload(jwtProperties.getSecret(), token);
            userId = Long.parseLong(payload.get(JWT_UID_CLAIM_KEY));
            tokenId = payload.get(JWT_TOKEN_ID_CLAIM_KEY);
            type = payload.get(JWT_TOKEN_TYPE_CLAIM_KEY);
        } catch (Exception ex) {
            LOG.info("[JWT解析失败]>>> {}", ex.getMessage(), ex);
            throw new AuthException(UserExceptionType.USER_AUTH_EXCEPTION, ex);
        }
        // 刷新令牌才能进行刷新操作
        if (!TokenType.REFRESH_TOKEN.getKey().equals(type) || "".equals(tokenId) || userId <= 0L) {
            LOG.warn("[获取刷新令牌载荷失败]>>> 用户ID={}，令牌ID={}，令牌类型={}", userId, tokenId, type);
            throw new AuthException(UserExceptionType.USER_AUTH_EXCEPTION);
        }

        // Redis token 校验
        Long expiredTime = repository.getRefreshTokenExpiredTime(userId, tokenId);
        if (expiredTime == null) {
            throw new AuthException(UserExceptionType.REFRESH_TOKEN_HAS_EXPIRED);
        }

        long now = new Date().getTime() / 1000;
        // 令牌已经过期，移除令牌
        if (expiredTime <= now) {
            repository.removeAccessToken(userId, tokenId);
            repository.removeRefreshToken(userId, tokenId);
            throw new AuthException(UserExceptionType.REFRESH_TOKEN_HAS_EXPIRED);
        }

        // 刷新令牌未过期，生成新的令牌
        TokenInfo tokenInfo = createTokenInfo(new Id(userId));
        LOG.info("[刷新令牌成功]>>> 用户ID={}，令牌信息={}", userId, tokenInfo);
        return tokenInfo;
    }

    /**
     * 检查用户账号状态是否健康。
     *
     * @param account 用户账号
     * @throws AuthException 用户账号状态不健康抛出
     */
    private void checkUserState(Account account) throws AuthException {
        if (account == null) {
            throw new AuthException(UserExceptionType.USER_STATE_EXCEPTION);
        }

        switch (account.getState()) {
            case ENABLE:
                return;
            case DISABLE:
                LOG.warn("[用户已被禁用]>>> 用户名={}", account.getUsername().getValue());
                throw new AuthException(UserExceptionType.USER_STATE_DISABLE);
            case LOCK:
                LOG.warn("[用户已被锁定]>>> 用户名={}", account.getUsername().getValue());
                throw new AuthException(UserExceptionType.USER_STATE_LOCK);
            default:
                throw new AuthException(UserExceptionType.USER_STATE_EXCEPTION);
        }
    }

    /**
     * 生成登录令牌信息。
     * <p>每次颁发新的访问令牌时，都会颁发新的刷新令牌，而刷新令牌的时间远大于访问令牌。</p>
     * <p>访问令牌数量 <= 刷新令牌数量，所以用户是否在线和同时在线数以访问令牌为主。</p>
     * <p>对于当前用户的登录账号的请求是串行的。</p>
     * <p>能够生成登录令牌的条件如下：</p>
     * <ul>
     *     <li>{@link AuthService#WHETHER_LOCK_USER} = false：没有锁定用户。</li>
     *     <li>{@link AuthService#WHETHER_LOCK_USER} = true：当该用户的同时在线数未超出限制
     *     （{@link AuthService#MAXIMUM_LOGIN_TOKEN_ID}）时。</li>
     * </ul>
     *
     * @param identifier 用户标识
     * @throws DataException 生成失败抛出异常
     */
    private TokenInfo createTokenInfo(final Id identifier) throws DataException {
        String lockKey = UserCacheKey.USER_LOGIN_LOCK + identifier.getValue();
        boolean tryLock = cacheService.lock(lockKey, 1, TimeUnit.MINUTES);

        if (tryLock) {
            try {
                Map<String, Long> accessTokens = repository.findOnlineAccessTokenByUserId(identifier);
                Map<String, Long> refreshTokens = repository.findOnlineRefreshTokenByUserId(identifier);
                checkTokenQuantity(accessTokens, refreshTokens);

                if (AuthService.WHETHER_LOCK_USER && refreshTokens.size() >= AuthService.MAXIMUM_LOGIN_TOKEN_ID) {
                    // 用户已锁定，同时超出该用户的同时在线数，无法生成登录令牌
                    throw new AuthException(UserExceptionType.USER_LOCKED_CAN_NOT_LOGIN);
                }

                // 清除无效令牌
                clearTokens(accessTokens, refreshTokens);

                // 满足条件，生成 JWT Token
                String tokenId = ksuidGenerator.genId();
                LOG.info("[生成令牌ID]>>> 令牌ID={}", tokenId);
                String jwtAccessToken = createJwtToken(identifier, tokenId, TokenType.ACCESS_TOKEN);
                String jwtRefreshToken = createJwtToken(identifier, tokenId, TokenType.REFRESH_TOKEN);

                // 更新 Token ID 到缓存
                long now = new Date().getTime() / 1000;
                accessTokens.put(tokenId, now + AuthService.ACCESS_TOKEN_EXPIRED_IN_SECONDS);
                refreshTokens.put(tokenId, now + AuthService.REFRESH_TOKEN_EXPIRED_IN_SECONDS);
                repository.saveOnlineToken(identifier, accessTokens, refreshTokens);
                LOG.info("[缓存在线令牌成功]>>> 访问令牌=[{}, {}]，刷新令牌=[{}, {}]",
                        tokenId, accessTokens.get(tokenId), tokenId, refreshTokens.get(tokenId));
                LOG.info("[当前在线令牌的数量]>>> 访问令牌的数量={}，刷新令牌的数量={}",
                        accessTokens.size(), refreshTokens.size());

                TokenInfo tokenInfo = new TokenInfo(jwtAccessToken, jwtRefreshToken, REFRESH_TOKEN_EXPIRED_IN_SECONDS);
                LOG.info("[生成令牌信息成功]>>> 令牌信息={}", tokenInfo);
                return tokenInfo;
            } finally {
                cacheService.unlock(lockKey);
            }
        }

        // 在该用户账号登录的过程中，该账号还有登录请求，抛出异常
        throw new ServiceException(UserExceptionType.USER_LOGGING_IN_TRY_AGAIN_LATER);
    }

    /**
     * 清除无效令牌（过期和超出数量）。
     *
     * @param accessTokens  访问令牌集合
     * @param refreshTokens 刷新令牌集合
     */
    private void clearTokens(Map<String, Long> accessTokens, Map<String, Long> refreshTokens) {
        // 清除过期的令牌
        clearExpiredTokens(accessTokens, refreshTokens);
        checkTokenQuantity(accessTokens, refreshTokens);
        // 清除超出数量的令牌
        clearExcessQuantityTokens(accessTokens, refreshTokens);
        checkTokenQuantity(accessTokens, refreshTokens);
    }

    /**
     * 清除超出数量的令牌（保留一个空位）。
     * <p>将超出数量 {@link AuthService#MAXIMUM_LOGIN_TOKEN_ID} 的令牌中，最先过期的令牌清除。</p>
     *
     * @param accessTokens  访问令牌集合
     * @param refreshTokens 刷新令牌集合
     */
    private void clearExcessQuantityTokens(Map<String, Long> accessTokens, Map<String, Long> refreshTokens) {
        LOG.info("[清除超出数量的令牌执行前]>>> 访问令牌数量={}，刷新令牌数量={}", accessTokens.size(), refreshTokens.size());

        // 保留一个空位
        int clearSize = refreshTokens.size() - AuthService.MAXIMUM_LOGIN_TOKEN_ID + 1;
        if (clearSize <= 0) {
            return;
        }

        // 刷新令牌根据过期时间从小到大排序
        List<Map.Entry<String, Long>> refreshTokenList = new ArrayList<>(refreshTokens.entrySet());
        refreshTokenList.sort(Map.Entry.comparingByValue());

        // 开始清除令牌，根据刷新令牌来判断用户同时在线的令牌数
        int clearNum = 0;
        for (Map.Entry<String, Long> entry : refreshTokenList) {
            if (clearNum == clearSize) {
                break;
            }
            accessTokens.remove(entry.getKey());
            refreshTokens.remove(entry.getKey());
            clearNum++;
        }

        LOG.info("[清除超出数量的令牌执行后]>>> 访问令牌数量={}，刷新令牌数量={}", accessTokens.size(), refreshTokens.size());
    }

    /**
     * 清除过期的令牌。
     *
     * @param accessTokens  访问令牌集合
     * @param refreshTokens 刷新令牌集合
     */
    private void clearExpiredTokens(Map<String, Long> accessTokens, Map<String, Long> refreshTokens) {
        LOG.info("[清除过期的令牌执行前]>>> 访问令牌数量={}，刷新令牌数量={}", accessTokens.size(), refreshTokens.size());

        // 使用副本遍历，这样 remove 就不会影响原 Map。
        // 解决 java.util.ConcurrentModificationException 问题。
        Map<String, Long> tempRefreshTokens = new HashMap<>(refreshTokens);

        long now = new Date().getTime() / 1000;
        // 先遍历刷新令牌，若刷新令牌过期，访问令牌也一定过期
        for (String token : tempRefreshTokens.keySet()) {
            // 令牌已过期
            if (refreshTokens.get(token) != null && refreshTokens.get(token) <= now) {
                accessTokens.remove(token);
                refreshTokens.remove(token);
            }
        }

        Map<String, Long> tempAccessTokens = new HashMap<>(accessTokens);
        // 再遍历访问令牌
        for (String token : tempAccessTokens.keySet()) {
            // 令牌已过期
            if (accessTokens.get(token) != null && accessTokens.get(token) <= now) {
                accessTokens.remove(token);
            }
        }

        LOG.info("[清除过期的令牌执行后]>>> 访问令牌数量={}，刷新令牌数量={}", accessTokens.size(), refreshTokens.size());
    }

    /**
     * 检查令牌数量是否正常。
     *
     * @param accessTokens  访问令牌集合
     * @param refreshTokens 刷新令牌集合
     * @throws UtilException 访问令牌数量超出刷新令牌数量抛出
     */
    private void checkTokenQuantity(final Map<String, Long> accessTokens, final Map<String, Long> refreshTokens) {
        if (accessTokens.size() > refreshTokens.size()) {
            throw new UtilException(UserExceptionType.ACCESS_TOKEN_NUM_EXCEEDS_REFRESH_TOKEN_NUM);
        }
    }

    /**
     * 生成带有 uid，tokenId，tokenType 的 JWT Token。
     *
     * @param uid       用户 ID
     * @param tokenId   Token ID
     * @param tokenType Token 令牌类型
     * @return JWT Token
     * @throws UtilException 生成 JWT Token 失败抛出异常
     */
    private String createJwtToken(final Id uid, final String tokenId, final TokenType tokenType)
            throws UtilException {

        HashMap<String, String> payload = new HashMap<>(3);
        payload.put(JWT_UID_CLAIM_KEY, String.valueOf(uid.getValue()));
        payload.put(JWT_TOKEN_ID_CLAIM_KEY, tokenId);
        payload.put(JWT_TOKEN_TYPE_CLAIM_KEY, tokenType.getKey());

        return jwtTokenService.create(payload, jwtProperties.getExpiredInSeconds(), jwtProperties.getSecret());
    }

    /**
     * 根据认证类型获取指定的认证策略实现类。
     *
     * @param type 认证类型
     * @return 认证策略实现类
     * @throws ConfigException 找不到认证策略实现类抛出
     */
    private AuthStrategy getAuthStrategy(final AuthStrategy.AuthType type) throws ConfigException {
        if (strategyMap.isEmpty()) {
            LOG.error("[获取认证策略失败]>>> 认证策略数量=0");
            throw new ConfigException(UserExceptionType.AUTH_STRATEGY_NOT_FOUND);
        }

        if (type == null || !strategyMap.containsKey(type.name())) {
            LOG.error("[找不到认证策略]>>> 认证策略类型={}", type);
            throw new ConfigException(UserExceptionType.AUTH_STRATEGY_NOT_FOUND);
        }

        return strategyMap.get(type.name());
    }
}