package io.github.hellovie.snapvids.common.exception.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

/**
 * 异常工具类。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class ExceptionUtils {

    private ExceptionUtils() {}

    /**
     * 获取异常堆栈字符串。
     *
     * @param throwable 异常
     * @return 异常堆栈字符串
     */
    public static String getStackTrace(final Throwable throwable) {
        if (throwable == null) {
            return "cause not found";
        }

        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    /**
     * 获取发生异常的文件名，找不到文件名返回 null。
     *
     * @param throwable 异常
     * @return 发生异常的文件名，找不到文件名返回 null
     */
    public static String getFileName(final Throwable throwable) {
        StackTraceElement ste = getFirstStackTraceElement(throwable);
        return ste == null ? null : ste.getFileName();
    }

    /**
     * 获取发生异常的类名，找不到类名返回 null。
     *
     * @param throwable 异常
     * @return 发生异常的类名，找不到类名返回 null
     */
    public static String getClassName(final Throwable throwable) {
        StackTraceElement ste = getFirstStackTraceElement(throwable);
        return ste == null ? null : ste.getClassName();
    }

    /**
     * 获取发生异常的方法名，找不到方法名返回 null。
     *
     * @param throwable 异常
     * @return 发生异常的方法名，找不到方法名返回 null
     */
    public static String getMethodName(final Throwable throwable) {
        StackTraceElement ste = getFirstStackTraceElement(throwable);
        return ste == null ? null : ste.getMethodName();
    }

    /**
     * 获取发生异常的行号，找不到行号返回 -1。
     *
     * @param throwable 异常
     * @return 发生异常的行号，找不到行号返回 -1
     */
    public static int getLineNumber(final Throwable throwable) {
        StackTraceElement ste = getFirstStackTraceElement(throwable);
        return ste == null ? -1 : ste.getLineNumber();
    }

    /**
     * 生成异常跟踪 ID。
     *
     * @return 异常跟踪 ID
     */
    public static String generateTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取 {@link Throwable} 的第一个 {@link StackTraceElement}，获取失败返回 null。
     *
     * @param throwable 异常
     * @return {@link Throwable} 的第一个 {@link StackTraceElement}，获取失败返回 null
     */
    private static StackTraceElement getFirstStackTraceElement(final Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        return throwable.getStackTrace().length == 0 ? null : throwable.getStackTrace()[0];
    }
}
