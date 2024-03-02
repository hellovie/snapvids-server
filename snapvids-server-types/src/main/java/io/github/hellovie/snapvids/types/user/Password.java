package io.github.hellovie.snapvids.types.user;

import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.types.Verifiable;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import static io.github.hellovie.snapvids.common.module.user.UserExceptionType.*;

/**
 * [Domain Primitive] password.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class Password implements Verifiable {

    private static final Logger LOG = LoggerFactory.getLogger(Password.class);

    /**
     * 密码明文
     */
    private final String plaintext;

    /**
     * 密码密文
     */
    private final String ciphertext;

    /**
     * 密码对象构造来源
     */
    private final From from;

    /**
     * 加密解密器
     */
    private static final PasswordEncoder PASSWORD_ENCODER;

    static {
        String idForEncode = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>(1);
        encoders.put(idForEncode, new BCryptPasswordEncoder(4));
        PASSWORD_ENCODER = new DelegatingPasswordEncoder(idForEncode, encoders);
    }

    /**
     * 根据密码明文构造密码对象。
     *
     * @param plaintext 密码明文
     * @return 密码对象
     * @throws InvalidParamException 密码明文格式有误抛出
     */
    public static Password ofPlaintext(String plaintext) throws InvalidParamException {
        String ciphertext = PASSWORD_ENCODER.encode(plaintext);
        return new Password(plaintext, ciphertext, From.PLAINTEXT);
    }

    /**
     * 根据密码密文构造密码对象。
     *
     * @param ciphertext 密码密文
     * @return 密码对象
     * @throws InvalidParamException 密码密文格式有误抛出
     */
    public static Password ofCiphertext(String ciphertext) throws InvalidParamException {
        return new Password("no plaintext", ciphertext, From.CIPHERTEXT);
    }

    /**
     * 私有构造。
     *
     * @param plaintext  密码明文
     * @param ciphertext 密码密文
     * @param from       构造来源
     */
    private Password(String plaintext, String ciphertext, From from) {
        this.plaintext = plaintext;
        this.ciphertext = ciphertext;
        this.from = from;
        verify();
    }

    /**
     * {@inheritDoc}
     *
     * @see Verifiable#verify()
     */
    @Override
    public void verify() throws InvalidParamException {
        // 校验密码明文
        if (from == From.PLAINTEXT) {
            final int max = 20;
            final int min = 8;
            final String pattern = "^(?![a-zA-Z]+$)(?!\\d+$)(?![^\\da-zA-Z\\S]+$).*$";
            Validation.isNotBlankOrElseThrow(plaintext, PASSWORD_CANNOT_BE_EMPTY);
            Validation.inIntScopeOrElseThrow(plaintext.length(), min, max, PASSWORD_LEN_NOT_MATCH);
            Validation.isMatchOrElseThrow(plaintext, pattern, PASSWORD_FORMAT_NOT_MATCH);
        }
        // 校验密码密文
        if (from == From.CIPHERTEXT) {
            Validation.isNotBlankOrElseThrow(ciphertext, PASSWORD_CIPHERTEXT_CANNOT_BE_EMPTY);
        }
    }

    /**
     * 验证密码是否正确。
     *
     * @param rowPassword 原始密码
     * @throws DataException 密码错误抛出
     */
    public void verifyPassword(String rowPassword) throws DataException {
        if (StringUtils.isBlank(rowPassword) || StringUtils.isBlank(ciphertext)) {
            throw new DataException(WRONG_PASSWORD);
        }

        if (StringUtils.isBlank(ciphertext)) {
            LOG.warn("[密码错误]>>> 密码={}", ciphertext);
            throw new DataException(WRONG_PASSWORD);
        }

        boolean isMatchesSuccess = PASSWORD_ENCODER.matches(rowPassword, ciphertext);
        if (!isMatchesSuccess) {
            throw new DataException(WRONG_PASSWORD);
        }
    }

    /**
     * 是否有明文密码。
     *
     * @return true：有明文密码；false：没有明文密码
     */
    public boolean hasPlaintext() {
        // 通过密文构造的密码对象，没有密码明文，因为加密是不可逆的。
        return from != From.CIPHERTEXT;
    }

    public String getPlaintext() {
        return plaintext;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public From getFrom() {
        return from;
    }

    @Override
    public String toString() {
        return ciphertext;
    }

    /**
     * 密码对象构造来源。
     */
    public enum From {

        /**
         * 明文
         */
        PLAINTEXT,

        /**
         * 密文
         */
        CIPHERTEXT
    }
}
