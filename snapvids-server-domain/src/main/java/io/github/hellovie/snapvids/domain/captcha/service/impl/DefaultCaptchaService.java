package io.github.hellovie.snapvids.domain.captcha.service.impl;

import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.common.module.common.CommonExceptionType;
import io.github.hellovie.snapvids.domain.captcha.event.CheckCaptchaEvent;
import io.github.hellovie.snapvids.domain.captcha.event.GenerateCaptchaCommand;
import io.github.hellovie.snapvids.domain.captcha.repository.CaptchaRepository;
import io.github.hellovie.snapvids.domain.captcha.service.CaptchaService;
import io.github.hellovie.snapvids.types.common.Captcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 验证码服务默认实现。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Service("captchaService")
public class DefaultCaptchaService implements CaptchaService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultCaptchaService.class);

    @Resource(name = "captchaRepository")
    private CaptchaRepository repository;

    /**
     * {@inheritDoc}
     *
     * @see CaptchaService#generate(GenerateCaptchaCommand)
     */
    @Override
    public Captcha generate(GenerateCaptchaCommand command) {
        String value = createCaptchaValue(command.getType());
        LOG.info("[Create captcha value success]>>> value={}", value);
        repository.cacheCaptcha(command.getKey(), value, command.getTime(), command.getUnit());
        try {
            return new Captcha(command.getId(), value);
        } catch (Exception ex) {
            LOG.warn("[Failed to generate a verification code]>>> id={}, value={}", command.getId(), value);
            return null;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see CaptchaService#check(CheckCaptchaEvent)
     */
    @Override
    public void check(CheckCaptchaEvent event) throws DataException {
        String value = repository.getCaptchaFromCache(event.getKey());
        if (!event.getCaptcha().equalsIgnoreCase(value)) {
            throw new DataException(CommonExceptionType.CAPTCHA_ERROR);
        }
        // 验证成功，删除缓存
        repository.removeCaptchaFromCache(event.getKey());
    }
}
