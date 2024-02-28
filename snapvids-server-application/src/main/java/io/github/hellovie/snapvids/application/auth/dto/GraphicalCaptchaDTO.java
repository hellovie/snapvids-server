package io.github.hellovie.snapvids.application.auth.dto;

/**
 * 图形验证码 DTO。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class GraphicalCaptchaDTO {

    /**
     * 验证码 id
     */
    private final String captchaId;

    /**
     * Base64 图形验证码
     */
    private final String base64Captcha;

    public GraphicalCaptchaDTO(String captchaId, String base64Captcha) {
        this.captchaId = captchaId;
        this.base64Captcha = base64Captcha;
    }

    public String getCaptchaId() {
        return captchaId;
    }

    public String getBase64Captcha() {
        return base64Captcha;
    }
}
