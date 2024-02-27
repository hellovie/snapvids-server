package io.github.hellovie.snapvids.common.exception.notify;

import io.github.hellovie.snapvids.common.context.Context;
import io.github.hellovie.snapvids.common.exception.model.ExceptionType;
import io.github.hellovie.snapvids.common.exception.util.ExceptionUtils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 异常通知信息，系统抛出的异常的封装，用户异常告警。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class ExceptionNotifyInfo {

    /**
     * 异常发生时间
     */
    private final ZonedDateTime date;

    /**
     * 项目名称
     */
    private final String projectName;

    /**
     * 服务器 IP 地址
     */
    private final String serverIp;

    /**
     * 线程 ID
     */
    private final long threadId;

    /**
     * 异常追踪 ID
     */
    private final String traceId;

    /**
     * 报错文件名
     */
    private final String fileName;

    /**
     * 报错类名
     */
    private final String className;

    /**
     * 报错方法名
     */
    private final String methodName;

    /**
     * 报错行数
     */
    private final int lineNumber;

    /**
     * 异常类型
     */
    private final ExceptionType exceptionType;

    /**
     * 异常状态码
     */
    private final String code;

    /**
     * 异常信息
     */
    private final String message;

    /**
     * 上下文
     */
    private final Context context;

    /**
     * 异常详细信息
     */
    private final Throwable cause;

    public ExceptionNotifyInfo(
            ZonedDateTime date,
            String projectName,
            String serverIp,
            long threadId,
            String traceId,
            String fileName,
            String className,
            String methodName,
            int lineNumber,
            ExceptionType exceptionType,
            String code,
            String message,
            Context context,
            Throwable cause) {

        this.date = date;
        this.projectName = projectName;
        this.serverIp = serverIp;
        this.threadId = threadId;
        this.traceId = traceId;
        this.fileName = fileName;
        this.className = className;
        this.methodName = methodName;
        this.lineNumber = lineNumber;
        this.exceptionType = exceptionType;
        this.code = code;
        this.message = message;
        this.context = context;
        this.cause = cause;
    }

    /**
     * 格式化异常通知信息。
     *
     * @return 格式化的异常通知信息
     */
    public String format() {
        return "[Date           ]: " + (date == null ? "null" :
                date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss '['VV']'"))) + '\n' +
                "[Project Name   ]: " + projectName + '\n' +
                "[Server Address ]: " + serverIp + '\n' +
                "[Thread Id      ]: " + threadId + '\n' +
                "[Trace Id       ]: " + traceId + '\n' +
                "[File Name      ]: " + fileName + '\n' +
                "[Class Name     ]: " + className + '\n' +
                "[Method Name    ]: " + methodName + '\n' +
                "[Line Number    ]: " + lineNumber + '\n' +
                "[Exception Type ]: " + exceptionType + '\n' +
                "[Code           ]: " + code + '\n' +
                "[Message        ]: " + message + '\n' +
                "[Context        ]: " + context + '\n' +
                "[Cause          ]: " + ExceptionUtils.getStackTrace(cause);
    }

    /**
     * 格式化异常通知信息。
     *
     * @return 格式化的异常通知信息
     */
    public String simpleFormat() {
        return "{" +
                "[Code: " + code + "], " +
                "[Message: " + message + "]" +
                "}";
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getServerIp() {
        return serverIp;
    }

    public long getThreadId() {
        return threadId;
    }

    public String getTraceId() {
        return traceId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Context getContext() {
        return context;
    }

    public Throwable getCause() {
        return cause;
    }

    @Override
    public String toString() {
        return "ExceptionNotifyInfo{" +
                "date=" + date +
                ", projectName='" + projectName + '\'' +
                ", serverIp='" + serverIp + '\'' +
                ", threadId=" + threadId +
                ", traceId='" + traceId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", lineNumber=" + lineNumber +
                ", exceptionType=" + exceptionType +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", context=" + context +
                ", cause=" + cause +
                '}';
    }
}
