package io.github.hellovie.snapvids.infrastructure.service.notify.impl;

import io.github.hellovie.snapvids.infrastructure.service.notify.ExceptionNotifyInfo;
import io.github.hellovie.snapvids.infrastructure.service.notify.NotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static io.github.hellovie.snapvids.common.exception.model.ExceptionType.*;

/**
 * 控制台异常通知服务。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Component("consoleNotifyService")
public class ConsoleNotifyService implements NotifyService {

    private static final Logger LOG = LoggerFactory.getLogger(ConsoleNotifyService.class);

    /**
     * {@inheritDoc}
     *
     * @see NotifyService#notifyMessage(ExceptionNotifyInfo)
     */
    @Override
    public void notifyMessage(ExceptionNotifyInfo notifyInfo) {
        switch (notifyInfo.getExceptionType()) {
            case BUSINESS:
                LOG.warn("[控制台异常打印]>>> 异常级别={}，异常信息={}",
                        BUSINESS.name().toLowerCase(), notifyInfo.simpleFormat());
                break;
            case SYSTEM:
                LOG.error("[控制台异常打印]>>> 异常级别={}，异常信息=⤵\n{}",
                        SYSTEM.name().toLowerCase(), notifyInfo.format());
                break;
            case UNKNOWN:
                LOG.error("[控制台异常打印]>>> 异常级别={}，异常信息=⤵\n{}",
                        UNKNOWN.name().toLowerCase(), notifyInfo.format());
                break;
            default:
                LOG.error("[控制台异常打印]>>> 异常级别={}，异常信息={}",
                        notifyInfo.getExceptionType().name().toLowerCase(), notifyInfo.simpleFormat());
        }
    }
}
