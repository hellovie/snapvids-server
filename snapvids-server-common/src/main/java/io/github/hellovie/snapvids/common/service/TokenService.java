package io.github.hellovie.snapvids.common.service;

import io.github.hellovie.snapvids.common.exception.system.UtilException;

import java.util.Map;

/**
 * Token 令牌服务（生成，验证）。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface TokenService {

    /**
     * 生成 Token 令牌。
     *
     * @param payload          载荷 (key, value)
     * @param expiredInSeconds 有效时间（单位：秒）
     * @param secret           密钥
     * @throws UtilException 生成失败抛出
     */
    String create(Map<String, String> payload, long expiredInSeconds, String secret) throws UtilException;

    /**
     * 验证 Token 令牌。
     *
     * @param secret 密钥
     * @param token  Token 令牌
     * @return true：验证成功；false：验证失败
     */
    boolean verify(String secret, String token);

    /**
     * 获取 Token 令牌中的载荷。
     *
     * @param secret 密钥
     * @param token  Token 令牌
     * @return 载荷 Map
     * @throws UtilException 获取载荷失败抛出
     */
    Map<String, String> getPayload(String secret, String token) throws UtilException;
}
