package io.github.hellovie.snapvids.infrastructure.service.notify;

import io.github.hellovie.snapvids.common.exception.system.ConfigException;
import io.github.hellovie.snapvids.common.module.common.CommonExceptionType;

import java.util.HashMap;
import java.util.Map;

/**
 * 异常告警管理器。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class NotifyServiceManager {

    /**
     * 异常通知服务
     */
    private final Map<String, NotifyService> notifyServices;

    /**
     * 私有构造，仅支持 Builder 构造对象。
     *
     * @param notifyServices 异常通知服务实现类
     */
    private NotifyServiceManager(Map<String, NotifyService> notifyServices) {
        this.notifyServices = notifyServices;
    }

    /**
     * 根据异常通知服务类型发送异常通知，注册的异常通知服务为 null，则不发送异常通知。
     *
     * @param type       异常通知服务类型
     * @param notifyInfo 异常通知信息
     * @throws ConfigException 当找不到异常通知服务时抛出
     */
    public void notify(final String type, ExceptionNotifyInfo notifyInfo) throws ConfigException {
        if (!notifyServices.containsKey(type)) {
            throw new ConfigException(CommonExceptionType.NOTIFY_SERVICE_NOT_FOUND);
        }
        // 注册的异常通知服务为 null，则不发送异常通知
        if (notifyServices.get(type) != null) {
            notifyServices.get(type).notifyMessage(notifyInfo);
        }
    }

    /**
     * 建造者模式。
     */
    public static class Builder {
        /**
         * 异常通知服务
         */
        private final Map<String, NotifyService> notifyServices = new HashMap<>();

        /**
         * 注册异常通知服务。
         *
         * @param type    异常通知服务类型，不允许为 null 或 ""
         * @param service 异常通知服务，可以为 null，为 null 则不通知
         */
        public void addNotifyService(final String type, final NotifyService service) {
            if (type == null || "".equals(type)) {
                throw new ConfigException(CommonExceptionType.NOTIFY_SERVICE_INVALID_INJECTION);
            }
            // 只有异常通知服务未注册，才注册
            notifyServices.putIfAbsent(type, service);
        }

        /**
         * 构造异常告警管理器。
         *
         * @return 异常告警管理器
         */
        public NotifyServiceManager build() {
            return new NotifyServiceManager(notifyServices);
        }
    }

    /**
     * 根据异常通知服务类型获取指定的异常通知服务。
     *
     * @param notifyType 异常通知服务类型
     * @return 异常通知服务
     */
    public NotifyService getNotifyService(final String notifyType) {
        return notifyServices.get(notifyType);
    }

    /**
     * 获取已经注册的所有异常通知服务。
     *
     * @return 异常通知服务集合
     */
    public Map<String, NotifyService> getAllNotifyService() {
        return notifyServices;
    }
}
