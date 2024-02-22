package io.github.hellovie.snapvids.types.user;

import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.exception.system.CodeException;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.types.Verifiable;
import org.apache.commons.codec.digest.Sha2Crypt;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.Base64;

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
     * 密码明文（不对外暴露）
     */
    private final String plaintext;

    /**
     * 密码密文
     */
    private final String ciphertext;

    /**
     * 加密盐值
     */
    private final String salt;

    /**
     * 密码类型
     */
    private final Type type;

    /**
     * 是否校验原始密码格式（不对外暴露）
     */
    private final boolean whetherVerifyRawPassword;

    /**
     * 根据密码明文构造明文密码对象。
     *
     * @param plaintext 密码明文
     * @return 明文密码对象
     * @throws InvalidParamException 密码明文格式有误抛出
     */
    public static Password buildPlaintext(String plaintext) throws InvalidParamException {
        return new Password(
                true,
                plaintext,
                "no encryption",
                "no salt",
                Type.PLAINTEXT);
    }

    /**
     * 根据密码明文构造密文密码对象（自动生成盐值）。
     *
     * @param plaintext 密码明文
     * @return 密文密码对象
     * @throws InvalidParamException 密码明文格式有误抛出
     */
    public static Password buildCiphertext(String plaintext) throws InvalidParamException {
        String salt = generateSalt();
        String ciphertext = encrypt(plaintext, salt);
        return new Password(
                true,
                plaintext,
                ciphertext,
                salt,
                Type.CIPHERTEXT);
    }

    /**
     * 根据密文和盐值构造密文密码对象。
     *
     * @param ciphertext 密码密文
     * @param salt       加密盐值
     * @return 密文密码对象
     */
    public static Password buildCiphertext(String ciphertext, String salt) throws CodeException {
        saltEncryptionValidityTest(salt);
        if (StringUtils.isBlank(ciphertext)) {
            throw new CodeException(PASSWORD_CIPHERTEXT_CANNOT_BE_EMPTY);
        }
        return new Password(
                false,
                "no plaintext",
                ciphertext,
                salt,
                Type.CIPHERTEXT);
    }

    /**
     * 私有构造。
     *
     * @param whetherVerifyRawPassword 是否校验原始密码格式
     * @param plaintext                密码明文
     * @param ciphertext               密码密文
     * @param salt                     加密盐值
     * @param type                     密码对象类型
     */
    private Password(boolean whetherVerifyRawPassword, String plaintext, String ciphertext, String salt, Type type) {
        this.whetherVerifyRawPassword = whetherVerifyRawPassword;
        this.plaintext = plaintext;
        this.ciphertext = ciphertext;
        this.salt = salt;
        this.type = type;
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
        if (whetherVerifyRawPassword) {
            final int max = 20;
            final int min = 8;
            final String pattern = "^(?![a-zA-Z]+$)(?!\\d+$)(?![^\\da-zA-Z\\S]+$).*$";
            Validation.isNotBlankOrElseThrow(plaintext, PASSWORD_CANNOT_BE_EMPTY);
            Validation.inIntScopeOrElseThrow(plaintext.length(), min, max, PASSWORD_LEN_NOT_MATCH);
            Validation.isMatchOrElseThrow(plaintext, pattern, PASSWORD_FORMAT_NOT_MATCH);
        }
    }

    /**
     * 验证密码是否正确。
     *
     * @param realPassword 正确密码对象
     * @throws DataException 密码错误抛出
     */
    public void verifyPassword(Password realPassword) throws DataException {
        Validation.isNotNullOrElseThrow(realPassword, WRONG_PASSWORD);

        // 如果需要校验的密码是明文
        if (type == Type.PLAINTEXT) {
            Validation.isNotBlankOrElseThrow(plaintext, WRONG_PASSWORD);
            Validation.isNotBlankOrElseThrow(realPassword.getSalt(), WRONG_PASSWORD);
            String encryptPassword = Password.encrypt(plaintext, realPassword.getSalt());
            if (!encryptPassword.equals(realPassword.getCiphertext())) {
                throw new InvalidParamException(WRONG_PASSWORD);
            }
        }

        // 如果需要校验的密码是密文
        if (type == Type.CIPHERTEXT) {
            Validation.isNotBlankOrElseThrow(salt, WRONG_PASSWORD);
            Validation.isNotBlankOrElseThrow(realPassword.getSalt(), WRONG_PASSWORD);
            if (!salt.equals(realPassword.getSalt())) {
                LOG.error("[The salt values are different and cannot be compared]>>> salt={}, realPassword.getSalt()={}"
                        , salt, realPassword.getSalt());
                throw new InvalidParamException(WRONG_PASSWORD);
            }
            Validation.isNotBlankOrElseThrow(ciphertext, WRONG_PASSWORD);
            Validation.isNotBlankOrElseThrow(realPassword.getCiphertext(), WRONG_PASSWORD);
            if (!ciphertext.equals(realPassword.getCiphertext())) {
                throw new InvalidParamException(WRONG_PASSWORD);
            }
        }
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public String getSalt() {
        return salt;
    }

    public Type getType() {
        return type;
    }

    /**
     * 生成随机盐值。
     *
     * @return 随机盐值
     */
    private static String generateSalt() {
        int saltLength = 16;
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[saltLength];
        secureRandom.nextBytes(salt);
        return "$5$" + Base64.getEncoder().encodeToString(salt);
    }

    /**
     * 加密密码。
     *
     * @param plaintext 明文
     * @param salt      盐值
     * @return 加密后的密码
     */
    private static String encrypt(String plaintext, String salt) {
        saltEncryptionValidityTest(salt);
        try {
            return Sha2Crypt.sha256Crypt(plaintext.getBytes(), salt);
        } catch (Exception ex) {
            throw new CodeException(WRONG_PASSWORD_SALT, ex);
        }
    }

    /**
     * 加盐加密有效性检验，若盐值格式错误无法加密，则抛出异常。
     *
     * @param salt 盐值
     * @throws CodeException 盐值格式错误无法加密抛出
     */
    private static void saltEncryptionValidityTest(String salt) throws CodeException {
        if (StringUtils.isBlank(salt)) {
            throw new CodeException(PASSWORD_SALT_CANNOT_BE_EMPTY);
        }

        if (!salt.startsWith("$5$")) {
            throw new CodeException(WRONG_PASSWORD_SALT);
        }
    }

    /**
     * 密码类型。
     */
    public enum Type {

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
