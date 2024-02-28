package io.github.hellovie.snapvids.domain.captcha.event;

import io.github.hellovie.snapvids.common.module.user.UserCacheKey;
import io.github.hellovie.snapvids.types.common.Captcha;

/**
 * 校验验证码事件。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class CheckCaptchaEvent {

    /**
     * 验证码缓存 key
     */
    private final String key;

    /**
     * 验证码值
     */
    private final String captcha;

    /**
     * 为注册短信验证码构造校验事件。
     *
     * @param captcha 验证码对象
     * @return {@link CheckCaptchaEvent}
     */
    public static CheckCaptchaEvent buildForRegisterSms(Captcha captcha) {
        return new CheckCaptchaEvent(UserCacheKey.USER_REGISTER_SMS_CAPTCHA + captcha.getId(), captcha.getValue());
    }

    /**
     * 为登录图形验证码构造校验事件。
     *
     * @param captcha 验证码对象
     * @return {@link CheckCaptchaEvent}
     */
    public static CheckCaptchaEvent buildForLoginGraphical(Captcha captcha) {
        return new CheckCaptchaEvent(UserCacheKey.USER_LOGIN_GRAPHICAL_CAPTCHA + captcha.getId(), captcha.getValue());
    }

    /**
     * 私有构造。
     *
     * @param key     验证码缓存 key
     * @param captcha 验证码值
     */
    private CheckCaptchaEvent(String key, String captcha) {
        this.key = key;
        this.captcha = captcha;
    }

    public String getKey() {
        return key;
    }

    public String getCaptcha() {
        return captcha;
    }
}
