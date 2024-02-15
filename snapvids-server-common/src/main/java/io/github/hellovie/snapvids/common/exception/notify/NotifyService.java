package io.github.hellovie.snapvids.common.exception.notify;

/**
 * 异常通知服务。
 *
 * @author hellovie
 * @since 1.0.0
 */
@FunctionalInterface
public interface NotifyService {

    /**
     * 异常通知，发送异常信息到异常监控平台。
     *
     * @param notifyInfo 异常通知信息
     */
    void notifyMessage(ExceptionNotifyInfo notifyInfo);
}
