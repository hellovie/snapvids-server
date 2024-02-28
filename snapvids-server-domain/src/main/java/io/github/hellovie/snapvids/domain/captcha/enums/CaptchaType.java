package io.github.hellovie.snapvids.domain.captcha.enums;

/**
 * 验证码类型。
 *
 * @author hellovie
 * @since 1.0.0
 */
public enum CaptchaType {

    /**
     * 纯数字
     */
    NUMBER(6, "123456789"),

    /**
     * 字母
     */
    LETTER(6, "abcdefghijklmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ"),

    /**
     * 字母和数字混合
     */
    MIXTURE(6, "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ"),

    ;

    /**
     * 验证码长度
     */
    private final int len;

    /**
     * 字符集
     */
    private final String chars;

    CaptchaType(int len, String chars) {
        this.len = len;
        this.chars = chars;
    }

    public int getLen() {
        return len;
    }

    public String getChars() {
        return chars;
    }
}
