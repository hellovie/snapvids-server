package io.github.hellovie.snapvids.domain.captcha.service;

import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.domain.captcha.enums.CaptchaType;
import io.github.hellovie.snapvids.domain.captcha.event.CheckCaptchaEvent;
import io.github.hellovie.snapvids.domain.captcha.event.GenerateCaptchaCommand;
import io.github.hellovie.snapvids.types.common.Captcha;

import java.util.Random;

import static io.github.hellovie.snapvids.domain.captcha.enums.CaptchaType.NUMBER;


/**
 * 验证码服务。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface CaptchaService {

    /**
     * 生成验证码，生成失败返回 null。
     *
     * @param command 生成验证码命令
     * @return 验证码，生成失败返回 null
     */
    Captcha generate(GenerateCaptchaCommand command);

    /**
     * 校验验证码。
     *
     * @param event 校验验证码事件
     * @throws DataException 校验失败抛出
     */
    void check(CheckCaptchaEvent event) throws DataException;

    /**
     * 生成随机验证码字符串。
     *
     * @param type 验证码类型
     * @return 验证码字符串
     */
    default String createCaptchaValue(CaptchaType type) {
        final int len = type != null ? type.getLen() : NUMBER.getLen();
        final String chars = type != null ? type.getChars() : NUMBER.getChars();

        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int index = random.nextInt(chars.length());
            code.append(chars.charAt(index));
        }
        return String.valueOf(code);
    }
}
