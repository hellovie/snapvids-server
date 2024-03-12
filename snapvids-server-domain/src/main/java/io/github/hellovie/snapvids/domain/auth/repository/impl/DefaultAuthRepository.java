package io.github.hellovie.snapvids.domain.auth.repository.impl;


import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.common.module.user.UserExceptionType;
import io.github.hellovie.snapvids.domain.auth.entity.Account;
import io.github.hellovie.snapvids.domain.auth.entity.SysPermission;
import io.github.hellovie.snapvids.domain.auth.entity.SysRole;
import io.github.hellovie.snapvids.domain.auth.entity.SysUser;
import io.github.hellovie.snapvids.domain.auth.repository.AuthRepository;
import io.github.hellovie.snapvids.infrastructure.cache.CacheService;
import io.github.hellovie.snapvids.infrastructure.persistence.dao.UserDao;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.Permission;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.Role;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.User;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.common.Ip;
import io.github.hellovie.snapvids.types.common.PhoneNumber;
import io.github.hellovie.snapvids.types.permission.*;
import io.github.hellovie.snapvids.types.role.RoleKey;
import io.github.hellovie.snapvids.types.role.RoleName;
import io.github.hellovie.snapvids.types.user.Password;
import io.github.hellovie.snapvids.types.user.Username;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static io.github.hellovie.snapvids.common.module.user.UserCacheKey.USER_ACCESS_TOKEN;
import static io.github.hellovie.snapvids.common.module.user.UserCacheKey.USER_REFRESH_TOKEN;

/**
 * 认证领域仓储默认实现。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Repository("authRepository")
public class DefaultAuthRepository implements AuthRepository {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultAuthRepository.class);

    @Resource(name = "userDao")
    private UserDao userDao;

    @Resource(name = "redisCacheService")
    private CacheService cacheService;

    /**
     * {@inheritDoc}
     *
     * @see AuthRepository#findAccountByUsername(Username)
     */
    @Override
    public Account findAccountByUsername(Username username) {
        User user = userDao.onlyFindUserByUsername(username.getValue());
        if (user == null || user.getIsDeleted()) {
            return null;
        }

        return new Account(
                new Id(user.getId()),
                new Username(user.getUsername()),
                Password.ofCiphertext(user.getPassword()),
                new PhoneNumber(user.getPhoneNumber()),
                new Ip(user.getLastLoginIp()),
                user.getLastLoginTime(),
                new Ip(user.getRegisterIp()),
                user.getRegisterTime(),
                user.getState()
        );
    }

    /**
     * {@inheritDoc}
     *
     * @see AuthRepository#findOnlineAccessTokenByUserId(Id)
     */
    @Override
    public Map<String, Long> findOnlineAccessTokenByUserId(Id id) {
        String cacheKey = USER_ACCESS_TOKEN + id.getValue();
        Map<String, Object> cacheMap = cacheService.getMap(cacheKey);
        return convertLongValueTokenMap(cacheMap);
    }

    /**
     * {@inheritDoc}
     *
     * @see AuthRepository#findOnlineRefreshTokenByUserId(Id)
     */
    @Override
    public Map<String, Long> findOnlineRefreshTokenByUserId(Id id) {
        String cacheKey = USER_REFRESH_TOKEN + id.getValue();
        Map<String, Object> cacheMap = cacheService.getMap(cacheKey);
        return convertLongValueTokenMap(cacheMap);
    }

    /**
     * {@inheritDoc}
     *
     * @see AuthRepository#saveOnlineToken(Id, Map, Map)
     */
    @Override
    public void saveOnlineToken(Id id, Map<String, Long> accessTokens, Map<String, Long> refreshTokens) {
        String accessTokenKey = USER_ACCESS_TOKEN + id.getValue();
        String refreshTokenKey = USER_REFRESH_TOKEN + id.getValue();

        cacheService.delete(accessTokenKey);
        if (!accessTokens.isEmpty()) {
            cacheService.addToMap(accessTokenKey, convertObjectValueTokenMap(accessTokens));
        }

        cacheService.delete(refreshTokenKey);
        if (!refreshTokens.isEmpty()) {
            cacheService.addToMap(refreshTokenKey, convertObjectValueTokenMap(refreshTokens));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see AuthRepository#saveSysUser(SysUser)
     */
    @Override
    public SysUser saveSysUser(SysUser sysUser) {
        User user = new User();
        if (sysUser.getId() != null) {
            user.setId(sysUser.getId().getValue());
        }
        user.setUsername(sysUser.getUsername().getValue())
                .setPassword(sysUser.getPassword().getCiphertext())
                .setPhoneNumber(sysUser.getPhoneNumber().getNumber())
                .setLastLoginIp(sysUser.getLastLoginIp().getIntAddress())
                .setLastLoginTime(sysUser.getLastLoginTime())
                .setRegisterIp(sysUser.getRegisterIp().getIntAddress())
                .setRegisterTime(sysUser.getRegisterTime())
                .setState(sysUser.getState());
        List<Role> roles = sysUser.getRoles().stream()
                .map(sysRole -> new Role(sysRole.getId().getValue()))
                .collect(Collectors.toList());
        user.setRoles(roles);

        return toLazySysUser(userDao.save(user));
    }

    /**
     * {@inheritDoc}
     *
     * @see AuthRepository#findSysUserById(Id)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUser findSysUserById(Id id) {
        Optional<User> optional = userDao.findById(id.getValue());
        return optional.map(this::toSysUser).orElse(null);
    }

    /**
     * {@inheritDoc}
     *
     * @see AuthRepository#updateLoginInfoByUsername(Username, Ip, Timestamp)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLoginInfoByUsername(Username username, Ip lastLoginIp, Timestamp lastLoginTime)
            throws DataException {

        long updateRows = userDao.updateLoginInfoByUsername(
                username.getValue(), lastLoginIp.getIntAddress(), lastLoginTime);

        if (updateRows != 1) {
            LOG.error("[更新用户登录信息失败]>>> 更新行数={}，用户名={}，最后登录IP={}，最后登录时间={}",
                    updateRows, username.getValue(), lastLoginIp.getIntAddress(), lastLoginTime);
            throw new DataException(UserExceptionType.UPDATE_USER_LOGIN_INFO_FAILED);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see AuthRepository#removeToken(long, String...)
     */
    @Override
    public void removeToken(long userId, String... tokenId) {
        String accessTokenKey = USER_ACCESS_TOKEN + userId;
        String refreshTokenKey = USER_REFRESH_TOKEN + userId;
        Object[] tokenIds = Arrays.stream(tokenId).toArray();
        cacheService.delInMap(accessTokenKey, tokenIds);
        cacheService.delInMap(refreshTokenKey, tokenIds);
    }

    /**
     * {@inheritDoc}
     *
     * @see AuthRepository#getAccessTokenExpiredTime(long, String)
     */
    @Override
    public Long getAccessTokenExpiredTime(long userId, String tokenId) {
        String accessTokenKey = USER_ACCESS_TOKEN + userId;
        return getTokenExpiredTime(accessTokenKey, tokenId);
    }

    /**
     * {@inheritDoc}
     *
     * @see AuthRepository#getRefreshTokenExpiredTime(long, String)
     */
    @Override
    public Long getRefreshTokenExpiredTime(long userId, String tokenId) {
        String refreshTokenKey = USER_REFRESH_TOKEN + userId;
        return getTokenExpiredTime(refreshTokenKey, tokenId);
    }

    /**
     * {@inheritDoc}
     *
     * @see AuthRepository#removeAccessToken(long, String)
     */
    @Override
    public void removeAccessToken(long userId, String tokenId) {
        String accessTokenKey = USER_ACCESS_TOKEN + userId;
        cacheService.delInMap(accessTokenKey, tokenId);
    }

    /**
     * {@inheritDoc}
     *
     * @see AuthRepository#removeRefreshToken(long, String)
     */
    @Override
    public void removeRefreshToken(long userId, String tokenId) {
        String refreshTokenKey = USER_REFRESH_TOKEN + userId;
        cacheService.delInMap(refreshTokenKey, tokenId);
    }

    /**
     * 获取令牌的过期时间。
     *
     * @param key   缓存 key
     * @param field map field
     * @return 令牌的过期时间
     */
    private Long getTokenExpiredTime(String key, String field) {
        Map<String, Object> map = cacheService.getMap(key);
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, Long> tokenMap = convertLongValueTokenMap(map);
        if (!tokenMap.containsKey(field)) {
            return null;
        }

        return tokenMap.get(field);
    }

    /**
     * 「单表」{@link User} to {@link SysUser}.
     *
     * @param user {@link User}
     * @return {@link SysUser}
     */
    private SysUser toLazySysUser(User user) {
        if (user == null) {
            return null;
        }

        return new SysUser(
                new Id(user.getId()),
                new Username(user.getUsername()),
                Password.ofCiphertext(user.getPassword()),
                new PhoneNumber(user.getPhoneNumber()),
                new Ip(user.getLastLoginIp()),
                user.getLastLoginTime(),
                new Ip(user.getRegisterIp()),
                user.getRegisterTime(),
                user.getState()
        );
    }

    /**
     * {@link User} to {@link SysUser}.
     *
     * @param user {@link User}
     * @return {@link SysUser}
     */
    private SysUser toSysUser(User user) {
        if (user == null) {
            return null;
        }

        SysUser sysUser = new SysUser(
                new Id(user.getId()),
                new Username(user.getUsername()),
                Password.ofCiphertext(user.getPassword()),
                new PhoneNumber(user.getPhoneNumber()),
                new Ip(user.getLastLoginIp()),
                user.getLastLoginTime(),
                new Ip(user.getRegisterIp()),
                user.getRegisterTime(),
                user.getState()
        );

        List<Role> roles = user.getRoles() != null ? user.getRoles() : new ArrayList<>();
        List<SysRole> sysRoles = roles.stream()
                .map(this::toSysRole)
                .collect(Collectors.toList());
        sysUser.addAllRole(sysRoles);

        return sysUser;
    }

    /**
     * {@link Role} to {@link SysRole}.
     *
     * @param role {@link Role}
     * @return {@link SysRole}
     */
    private SysRole toSysRole(Role role) {
        if (role == null) {
            return null;
        }

        SysRole sysRole = new SysRole(new Id(role.getId()), new RoleKey(role.getRoleKey()), new RoleName(role.getName()));
        List<Permission> permissions = role.getPermissions() != null ? role.getPermissions() : new ArrayList<>();
        List<SysPermission> sysPermissions = permissions.stream()
                .map(this::toSysPermission)
                .collect(Collectors.toList());
        sysRole.addAllPermission(sysPermissions);

        return sysRole;
    }

    /**
     * {@link Permission} to {@link SysPermission}.
     *
     * @param permission {@link Permission}
     * @return {@link SysPermission}
     */
    private SysPermission toSysPermission(Permission permission) {
        if (permission == null) {
            return null;
        }

        return new SysPermission(
                new Id(permission.getId()),
                new PermissionCode(permission.getCode()),
                new Id(permission.getPid()),
                new PermissionIcon(permission.getIcon()),
                new PermissionName(permission.getName()),
                new PermissionUrl(permission.getUrl()),
                permission.getType(),
                new PermissionPath(permission.getPath()),
                new PermissionLevel(permission.getLevel()),
                new PermissionSort(permission.getSort())
        );
    }

    /**
     * 将缓存中获取出来的令牌键值对的值从 {@link Object} 转化为 {@link Long}。
     *
     * @param objectMap 缓存中获取出来的令牌键
     * @return key: {@link String}, value: {@link Long}
     */
    private Map<String, Long> convertLongValueTokenMap(Map<String, Object> objectMap) {
        if (objectMap == null || objectMap.isEmpty()) {
            return new HashMap<>();
        }

        HashMap<String, Long> longMap = new HashMap<>(objectMap.size());
        for (String key : objectMap.keySet()) {
            if (objectMap.get(key) instanceof Integer) {
                Integer value = (Integer) objectMap.get(key);
                longMap.put(key, value.longValue());
            } else {
                Long value = (Long) objectMap.get(key);
                longMap.put(key, value);
            }
        }
        return longMap;
    }

    /**
     * 将外部传入的令牌键值对的值从 {@link Long} 转化为 {@link Object}。
     *
     * @param longMap 外部传入的令牌键值对
     * @return key: {@link String}, value: {@link Object}
     */
    private Map<String, Object> convertObjectValueTokenMap(Map<String, Long> longMap) {
        if (longMap == null || longMap.isEmpty()) {
            return new HashMap<>();
        }

        HashMap<String, Object> objectMap = new HashMap<>(longMap.size());
        for (String key : longMap.keySet()) {
            Long value = longMap.get(key);
            objectMap.put(key, value);
        }
        return objectMap;
    }
}
