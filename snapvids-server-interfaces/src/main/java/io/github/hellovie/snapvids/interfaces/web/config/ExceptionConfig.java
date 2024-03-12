package io.github.hellovie.snapvids.interfaces.web.config;

import io.github.hellovie.snapvids.common.exception.manager.ExceptionManager;
import io.github.hellovie.snapvids.common.exception.model.ExceptionSourceType;
import io.github.hellovie.snapvids.infrastructure.service.notify.NotifyService;
import io.github.hellovie.snapvids.infrastructure.service.notify.NotifyServiceManager;
import io.github.hellovie.snapvids.infrastructure.service.notify.NotifyServiceType;
import io.github.hellovie.snapvids.common.module.ExceptionModuleType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 异常管理器配置类。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Configuration
public class ExceptionConfig {

    @Resource(name = "consoleNotifyService")
    private NotifyService consoleNotifyService;

    /**
     * 异常管理器。
     *
     * @return {@link ExceptionManager}
     */
    @Bean(name = "exceptionManager")
    public ExceptionManager exceptionManager() {
        ExceptionManager.Builder builder = new ExceptionManager.Builder(ExceptionModuleType.class, ExceptionSourceType.class);
        builder
                // 设置模块编号从 0 开始
                .setModuleMinNum(0)
                // 校验异常模块
                .checkModule()
                // 校验异常来源
                .checkSource()
                // 校验异常状态码
                .checkCode();
        return builder.build();
    }

    /**
     * 异常告警管理器。
     *
     * @return {@link NotifyServiceManager}
     */
    @Bean(name = "notifyServiceManager")
    public NotifyServiceManager notifyServiceManager() {
        NotifyServiceManager.Builder builder = new NotifyServiceManager.Builder();
        // 配置异常告警服务，服务为 null，则不通知
        builder.addNotifyService(NotifyServiceType.DEFAULT.name(), null);
        builder.addNotifyService(NotifyServiceType.CONSOLE.name(), consoleNotifyService);
        return builder.build();
    }
}
