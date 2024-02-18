package io.github.hellovie.snapvids.types.user;

import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.types.Verifiable;
import org.apache.commons.codec.digest.Sha2Crypt;

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

    /**
     * 值
     */
    private String value;

    /**
     * 盐值
     */
    private final String salt;

    public Password(String value) {
        this(value, generateSalt());
    }

    public Password(String value, String salt) {
        this.salt = salt;
        this.value = value;
        verify();
        this.value = Sha2Crypt.sha256Crypt(value.getBytes(), salt);
    }

    /**
     * {@inheritDoc}
     *
     * @see Verifiable#verify()
     */
    @Override
    public void verify() throws InvalidParamException {
        final int max = 20;
        final int min = 8;
        final String pattern = "^(?![a-zA-Z]+$)(?!\\d+$)(?![^\\da-zA-Z\\S]+$).*$";
        Validation.isNotBlankOrElseThrow(salt, PASSWORD_SALT_CANNOT_BE_EMPTY);
        Validation.isTrueOrElseThrow(salt.startsWith("$5$"), WRONG_PASSWORD_SALT);
        Validation.isNotBlankOrElseThrow(value, PASSWORD_CANNOT_BE_EMPTY);
        Validation.inIntScopeOrElseThrow(value.length(), min, max, PASSWORD_LEN_NOT_MATCH);
        Validation.isMatchOrElseThrow(value, pattern, PASSWORD_FORMAT_NOT_MATCH);
    }

    /**
     * 验证密码是否正确。
     *
     * @param plaintext 密码明文
     * @throws DataException 密码错误抛出
     */
    public void verifyPassword(String plaintext) throws DataException {
        Validation.isNotBlankOrElseThrow(plaintext, WRONG_PASSWORD);
    }

    public String getValue() {
        return value;
    }

    public String getSalt() {
        return salt;
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
}
