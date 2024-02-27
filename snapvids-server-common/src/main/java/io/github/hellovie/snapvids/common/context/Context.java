package io.github.hellovie.snapvids.common.context;

/**
 * 调用接口的上下文。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class Context {

    /**
     * 链路跟踪 id
     */
    private final String traceId;

    /**
     * 请求头 Authorization
     */
    private final String authorization;

    /**
     * 请求方法
     */
    private final String method;

    /**
     * 请求路径
     */
    private final StringBuffer url;

    /**
     * 请求接口
     */
    private final String api;

    /**
     * 请求用户主机 ip
     */
    private final String remoteIp;

    public Context(String traceId, String authorization, String method, StringBuffer url, String queryString,
                   String api, String remoteIp) {
        this.traceId = traceId == null ? "" : traceId;
        this.authorization = authorization == null ? "" : authorization;
        this.method = method == null ? "" : method;
        String query = queryString == null ? "" : "?" + queryString;
        this.url = url == null ? new StringBuffer() : url.append(query);
        this.api = api == null ? "" : api;
        this.remoteIp = remoteIp == null ? "" : remoteIp;
    }

    /**
     * 格式化输出上下文。
     *
     * @return 格式化后的上下文字符串
     */
    public String format() {
        return "[Trace ID        ]: " + traceId + "\n" +
                "[Request URL     ]: " + method + " " + url + "\n" +
                "[Request Mapping ]: " + api + "\n" +
                "[Remote Host     ]: " + remoteIp + "\n" +
                "[Authorization   ]: " + authorization;
    }

    /**
     * 简单输出上下文。
     *
     * @return 简单拼接的上下文字符串
     */
    public String simpleFormat() {
        return "{" +
                "[TraceID: " + traceId + "], " +
                "[RequestURL: " + method + " " + url + "], " +
                "[RequestMapping: " + api + "], " +
                "[RemoteHost: " + remoteIp + "], " +
                "[Authorization: " + authorization + "]" +
                "}";
    }

    public String getTraceId() {
        return traceId;
    }

    public String getAuthorization() {
        return authorization;
    }

    public String getMethod() {
        return method;
    }

    public StringBuffer getUrl() {
        return url;
    }

    public String getApi() {
        return api;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    @Override
    public String toString() {
        return "Context{" +
                "traceId='" + traceId + '\'' +
                ", authorization='" + authorization + '\'' +
                ", method='" + method + '\'' +
                ", url=" + url +
                ", api='" + api + '\'' +
                ", remoteIp='" + remoteIp + '\'' +
                '}';
    }
}
