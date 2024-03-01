package io.github.hellovie.snapvids.common.types;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.exception.manager.ExceptionCode;

import java.util.Collection;

/**
 * 参数验证工具类。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class Validation {

    private Validation() {}

    /**
     * 判断逻辑表达式结果是否为 True，不为 True 则抛出异常。
     *
     * @param logicalExpression 逻辑表达式
     * @param exceptionCode     异常状态码
     * @throws InvalidParamException 逻辑表达式结果不为 True 抛出
     */
    public static void isTrueOrElseThrow(boolean logicalExpression, ExceptionCode exceptionCode)
            throws InvalidParamException {

        if (!logicalExpression) {
            throw new InvalidParamException(exceptionCode);
        }
    }

    /**
     * 判断逻辑表达式结果是否为 False，不为 False 则抛出异常。
     *
     * @param logicalExpression 逻辑表达式
     * @param exceptionCode     异常状态码
     * @throws InvalidParamException 逻辑表达式结果不为 False 抛出
     */
    public static void isFalseOrElseThrow(boolean logicalExpression, ExceptionCode exceptionCode)
            throws InvalidParamException {

        isTrueOrElseThrow(!logicalExpression, exceptionCode);
    }

    /**
     * 判断变量是否为 null，不为 null 则抛出异常。
     *
     * @param var           变量
     * @param exceptionCode 异常状态码
     * @throws InvalidParamException 变量值不为 null 抛出
     */
    public static void isNullOrElseThrow(Object var, ExceptionCode exceptionCode) throws InvalidParamException {
        if (var != null) {
            throw new InvalidParamException(exceptionCode);
        }
    }

    /**
     * 判断变量是否为 null，为 null 则抛出异常。
     *
     * @param var           变量
     * @param exceptionCode 异常状态码
     * @throws InvalidParamException 变量值为 null 抛出
     */
    public static void isNotNullOrElseThrow(Object var, ExceptionCode exceptionCode) throws InvalidParamException {
        if (var == null) {
            throw new InvalidParamException(exceptionCode);
        }
    }

    /**
     * 判断字符串是否为空字符串（null、""、纯空格），若不为空字符串则抛出异常。
     *
     * @param str           字符串
     * @param exceptionCode 异常状态码
     * @throws InvalidParamException 字符串不为空抛出
     */
    public static void isBlankOrElseThrow(String str, ExceptionCode exceptionCode) throws InvalidParamException {
        if (str != null && !"".equals(str.trim())) {
            throw new InvalidParamException(exceptionCode);
        }
    }

    /**
     * 判断字符串是否为空字符串（null、""、纯空格），若为空字符串则抛出异常。
     *
     * @param str           字符串
     * @param exceptionCode 异常状态码
     * @throws InvalidParamException 字符串为空抛出
     */
    public static void isNotBlankOrElseThrow(String str, ExceptionCode exceptionCode) throws InvalidParamException {
        if (str == null || "".equals(str.trim())) {
            throw new InvalidParamException(exceptionCode);
        }
    }

    /**
     * 判断集合是否为空（null、size = 0），不为空则抛出异常。
     *
     * @param collection    集合
     * @param exceptionCode 异常状态码
     * @throws InvalidParamException 集合不为空抛出
     */
    public static void isEmptyOrElseThrow(Collection<?> collection, ExceptionCode exceptionCode)
            throws InvalidParamException {

        if (collection != null && !collection.isEmpty()) {
            throw new InvalidParamException(exceptionCode);
        }
    }

    /**
     * 判断集合是否为空（null、size = 0），为空则抛出异常。
     *
     * @param collection    集合
     * @param exceptionCode 异常状态码
     * @throws InvalidParamException 集合为空抛出
     */
    public static void isNotEmptyOrElseThrow(Collection<?> collection, ExceptionCode exceptionCode)
            throws InvalidParamException {

        if (collection == null || collection.isEmpty()) {
            throw new InvalidParamException(exceptionCode);
        }
    }

    /**
     * 判断变量是否在范围内，不在范围内则抛出异常。
     *
     * @param var           变量值
     * @param min           最小值（包括）
     * @param max           最大值（包括）
     * @param exceptionCode 异常状态码
     * @throws InvalidParamException 变量值不在范围内抛出
     */
    public static void inIntScopeOrElseThrow(int var, int min, int max, ExceptionCode exceptionCode)
            throws InvalidParamException {

        if (var < min || var > max) {
            throw new InvalidParamException(exceptionCode);
        }
    }

    /**
     * 判断变量是否在范围内，不在范围内则抛出异常。
     *
     * @param var           变量值
     * @param min           最小值（包括）
     * @param max           最大值（包括）
     * @param exceptionCode 异常状态码
     * @throws InvalidParamException 变量值不在范围内抛出
     */
    public static void inLongScopeOrElseThrow(long var, long min, long max, ExceptionCode exceptionCode)
            throws InvalidParamException {

        if (var < min || var > max) {
            throw new InvalidParamException(exceptionCode);
        }
    }

    /**
     * 是否匹配模式串，不匹配则抛出异常。
     *
     * @param var           待匹配字符串
     * @param pattern       模式串
     * @param exceptionCode 异常状态码
     * @throws InvalidParamException 不匹配模式串抛出
     */
    public static void isMatchOrElseThrow(String var, String pattern, ExceptionCode exceptionCode)
            throws InvalidParamException {

        if (pattern == null || "".equals(pattern.trim())) {
            return;
        }

        if (var == null || !var.matches(pattern)) {
            throw new InvalidParamException(exceptionCode);
        }
    }

    /**
     * 判断字符串是否是枚举名，不是则抛出异常。
     *
     * @param enumName      枚举名
     * @param enumClazz     枚举类
     * @param exceptionCode 字符串不是枚举名抛出
     */
    public static void isEnumNameOrElseThrow(String enumName, Class<? extends Enum<?>> enumClazz,
                                             ExceptionCode exceptionCode) {

        if (enumName == null || "".equals(enumName) || enumClazz == null) {
            throw new InvalidParamException(exceptionCode);
        }

        boolean isMatch = false;
        Enum<?>[] enums = enumClazz.getEnumConstants();
        if (enums != null) {
            for (Enum<?> e : enums) {
                if (e.name().equalsIgnoreCase(enumName)) {
                    isMatch = true;
                    break;
                }
            }
        }

        if (!isMatch) {
            throw new InvalidParamException(exceptionCode);
        }
    }
}
