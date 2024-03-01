package io.github.hellovie.snapvids.domain.storage.service.impl;

import io.github.hellovie.snapvids.common.service.TokenService;
import io.github.hellovie.snapvids.domain.storage.annotation.StorageServiceMark;
import io.github.hellovie.snapvids.domain.storage.service.StorageService;
import io.github.hellovie.snapvids.domain.storage.vo.UploadToken;
import io.github.hellovie.snapvids.infrastructure.persistence.enums.FileStorage;
import io.github.hellovie.snapvids.infrastructure.properties.JwtProperties;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.common.ValueString;
import io.github.hellovie.snapvids.types.file.FileIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;

/**
 * 本地存储服务。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Service("localStorageService")
@StorageServiceMark(type = FileStorage.LOCAL)
public class LocalStorageService implements StorageService {

    private static final Logger LOG = LoggerFactory.getLogger(LocalStorageService.class);

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
     * 令牌有效时间（单位：秒）
     */
    private static final long TOKEN_EXPIRED_IN_SECONDS = 30 * 60;

    /**
     * {@inheritDoc}
     *
     * @see StorageService#generateUploadToken(Id, FileIdentifier)
     */
    @Override
    public UploadToken generateUploadToken(Id fileId, FileIdentifier fileIdentifier) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + TOKEN_EXPIRED_IN_SECONDS * 1000);
        HashMap<String, String> payload = new HashMap<>(0);
        String jwtToken = jwtTokenService.create(payload, TOKEN_EXPIRED_IN_SECONDS, jwtProperties.getSecret());
        UploadToken token = new UploadToken(
                new Id(fileId.getValue()),
                new FileIdentifier(fileIdentifier.getValue()),
                ValueString.buildOrElseThrowByMessage(jwtToken, "文件上传令牌不能为空"),
                now,
                expiredDate
        );
        LOG.info("[Generate upload token]>>> token={}", token);
        return token;
    }
}
