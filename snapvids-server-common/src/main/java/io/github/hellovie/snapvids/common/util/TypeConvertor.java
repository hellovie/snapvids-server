package io.github.hellovie.snapvids.common.util;

import io.github.hellovie.snapvids.common.exception.system.UtilException;
import io.github.hellovie.snapvids.common.module.common.CommonExceptionType;

/**
 * 类型转换器。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class TypeConvertor {

    private TypeConvertor() {}

    /**
     * {@link String} to {@link Enum<T>}
     *
     * @param enumName  枚举名
     * @param enumClazz 要转换成的枚举类型的类
     * @param <T>       要转换成的枚举类型
     * @return 枚举类
     */
    public static <T extends Enum<T>> Enum<T> toEnum(String enumName, Class<T> enumClazz) {
        if (enumName == null || "".equals(enumName) || enumClazz == null) {
            throw new UtilException(CommonExceptionType.TYPE_CONVERSION_FAILED);
        }

        Enum<T>[] enums = enumClazz.getEnumConstants();
        if (enums != null) {
            for (Enum<T> e : enums) {
                if (e.name().equalsIgnoreCase(enumName)) {
                    return e;
                }
            }
        }
        throw new UtilException(CommonExceptionType.TYPE_CONVERSION_FAILED);
    }

    /**
     * {@link Integer} to {@link Enum<T>}
     *
     * @param enumOrdinal 枚举值
     * @param enumClazz   要转换成的枚举类型的类
     * @param <T>         要转换成的枚举类型
     * @return 枚举类
     */
    public static <T extends Enum<T>> Enum<T> toEnum(Integer enumOrdinal, Class<T> enumClazz) {
        if (enumOrdinal == null || enumOrdinal < 0 || enumClazz == null) {
            throw new UtilException(CommonExceptionType.TYPE_CONVERSION_FAILED);
        }

        Enum<T>[] enums = enumClazz.getEnumConstants();
        if (enums != null) {
            for (Enum<T> e : enums) {
                if (e.ordinal() == enumOrdinal) {
                    return e;
                }
            }
        }
        throw new UtilException(CommonExceptionType.TYPE_CONVERSION_FAILED);
    }
}
