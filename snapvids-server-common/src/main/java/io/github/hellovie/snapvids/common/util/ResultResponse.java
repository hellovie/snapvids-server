package io.github.hellovie.snapvids.common.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 返回信息封装。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class ResultResponse {

    private static final String SUCCESS_MESSAGE = "调用成功";

    /**
     * 接口调用成功。
     *
     * @param data 传递数据
     * @param <T>  数据类型
     * @return 数据封装集
     */
    public static <T> SuccessResult<T> success(final T data) {
        return new SuccessResult<>(Boolean.TRUE, SUCCESS_MESSAGE, data);
    }

    /**
     * 接口调用失败。
     *
     * @param canRetry 是否允许重试，true：允许重试
     * @param code     状态码
     * @param message  响应描述
     * @return 数据封装集
     */
    public static FailResult fail(final Boolean canRetry, final String code, final String message) {
        return new FailResult(Boolean.FALSE, canRetry, code, message);
    }

    /**
     * 接口调用失败, 记录日志跟踪 ID。
     *
     * @param traceId 日志跟踪 ID
     * @param code    状态码
     * @param message 响应描述
     * @return 数据封装集
     */
    public static TrackResult track(final String traceId, final String code, final String message) {
        return new TrackResult(Boolean.FALSE, traceId, code, message);
    }

    /**
     * 接口调用成功。
     *
     * @param <T> 响应数据类型
     */
    public static class SuccessResult<T> extends ResultResponse {
        /**
         * 接口是否调用成功
         * <p>成功：true</p>
         */
        private final Boolean success;

        /**
         * 响应描述
         */
        private final String message;

        /**
         * 响应数据
         */
        private final T data;

        /**
         * 接口调用时间
         */
        private final String timestamp;

        /**
         * 全参构造器，自动生成接口调用时间。
         *
         * @param isSuccess 接口是否调用成功，true：调用成功
         * @param message   响应描述
         * @param data      传递数据
         */
        private SuccessResult(Boolean isSuccess, String message, T data) {
            this.success = isSuccess;
            this.message = message;
            this.data = data;
            this.timestamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss '['VV']'"));
        }

        public Boolean getSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public T getData() {
            return data;
        }

        public String getTimestamp() {
            return timestamp;
        }
    }

    /**
     * 接口调用失败。
     */
    public static class FailResult extends ResultResponse {
        /**
         * 接口是否调用成功
         * <p>成功：true</p>
         */
        private final Boolean success;

        /**
         * 是否能够重试
         * <p>可以重试：true</p>
         */
        private final Boolean canRetry;

        /**
         * 状态码
         */
        private final String code;

        /**
         * 响应描述
         */
        private final String message;

        /**
         * 接口调用时间
         */
        private final String timestamp;

        /**
         * 全参构造器，自动生成接口调用时间。
         *
         * @param isSuccess 接口是否调用成功，true：调用成功
         * @param canRetry  是否允许重试，true：允许重试
         * @param code      状态码
         * @param message   响应描述
         */
        private FailResult(Boolean isSuccess, Boolean canRetry, String code, String message) {
            this.success = isSuccess;
            this.canRetry = canRetry;
            this.code = code;
            this.message = message;
            this.timestamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss '['VV']'"));
        }

        public Boolean getSuccess() {
            return success;
        }

        public Boolean getCanTry() {
            return canRetry;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public String getTimestamp() {
            return timestamp;
        }
    }

    /**
     * 接口调用失败，追踪错误。
     */
    public static class TrackResult extends ResultResponse {
        /**
         * 接口是否调用成功
         * <p>成功：true</p>
         */
        private final Boolean success;

        /**
         * 状态码
         */
        private final String code;

        /**
         * 日志跟踪 ID
         */
        private final String traceId;

        /**
         * 响应描述
         */
        private final String message;

        /**
         * 接口调用时间
         */
        private final String timestamp;

        /**
         * 全参构造器，自动生成接口调用时间。
         *
         * @param isSuccess 接口是否调用成功，true：调用成功
         * @param traceId   日志跟踪 ID
         * @param code      状态码
         * @param message   响应描述
         */
        private TrackResult(Boolean isSuccess, String traceId, String code, String message) {
            this.success = isSuccess;
            this.traceId = traceId;
            this.code = code;
            this.message = message;
            this.timestamp = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss '['VV']'"));
        }

        public Boolean getSuccess() {
            return success;
        }

        public String getCode() {
            return code;
        }

        public String getTraceId() {
            return traceId;
        }

        public String getMessage() {
            return message;
        }

        public String getTimestamp() {
            return timestamp;
        }
    }
}
