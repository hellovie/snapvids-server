package io.github.hellovie.snapvids.domain.captcha.event;

import com.github.ksuid.KsuidGenerator;
import io.github.hellovie.snapvids.common.module.user.UserCacheKey;
import io.github.hellovie.snapvids.domain.captcha.enums.CaptchaType;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

import static io.github.hellovie.snapvids.domain.captcha.enums.CaptchaType.MIXTURE;
import static io.github.hellovie.snapvids.domain.captcha.enums.CaptchaType.NUMBER;

/**
 * 生成验证码命令。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class GenerateCaptchaCommand {

    /**
     * 验证码类型
     */
    private final CaptchaType type;

    /**
     * 验证码 id
     */
    private final String id;

    /**
     * 验证码缓存 key
     */
    private final String key;

    /**
     * 缓存时间
     */
    private final long time;

    /**
     * 时间单位
     */
    private final TimeUnit unit;

    /**
     * 构造注册用户短信验证码生成命令。
     *
     * @param phoneNumber 手机号码
     * @return {@link GenerateCaptchaCommand}
     */
    public static GenerateCaptchaCommand buildForRegisterSms(String phoneNumber) {
        if (StringUtils.isBlank(phoneNumber)) {
            phoneNumber = "not-phone-number-" + KsuidGenerator.generate();
        }
        String key = UserCacheKey.USER_REGISTER_SMS_CAPTCHA + phoneNumber;
        return new GenerateCaptchaCommand(NUMBER, phoneNumber, key, 1, TimeUnit.MINUTES);
    }

    /**
     * 构造登录图形验证码生成命令。
     *
     * @return {@link GenerateCaptchaCommand}
     */
    public static GenerateCaptchaCommand buildForLoginGraphical() {
        String id = KsuidGenerator.generate();
        String key = UserCacheKey.USER_LOGIN_GRAPHICAL_CAPTCHA + id;
        return new GenerateCaptchaCommand(MIXTURE, id, key, 10, TimeUnit.MINUTES);
    }

    public GenerateCaptchaCommand(CaptchaType type, String id, String key, long time, TimeUnit unit) {
        this.type = type;
        this.id = id;
        this.key = key;
        this.time = time;
        this.unit = unit;
    }

    public CaptchaType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public long getTime() {
        return time;
    }

    public TimeUnit getUnit() {
        return unit;
    }
}
