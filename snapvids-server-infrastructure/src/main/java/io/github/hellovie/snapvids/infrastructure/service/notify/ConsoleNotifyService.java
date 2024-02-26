package io.github.hellovie.snapvids.infrastructure.service.notify;

import io.github.hellovie.snapvids.common.exception.notify.ExceptionNotifyInfo;
import io.github.hellovie.snapvids.common.exception.notify.NotifyService;
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
                LOG.warn("[ConsoleNotifyService print]>>> level={} \n{}",
                        BUSINESS.name().toLowerCase(), notifyInfo.format());
                break;
            case SYSTEM:
                LOG.error("[ConsoleNotifyService print]>>> level={} \n{}",
                        SYSTEM.name().toLowerCase(), notifyInfo.format());
                break;
            case UNKNOWN:
                LOG.error("[ConsoleNotifyService print]>>> level={} \n{}",
                        UNKNOWN.name().toLowerCase(), notifyInfo.format());
                break;
            default:
                LOG.error("[ConsoleNotifyService print]>>> level={} \n{}",
                        notifyInfo.getExceptionType().name().toLowerCase(), notifyInfo.format());
        }
    }
}
