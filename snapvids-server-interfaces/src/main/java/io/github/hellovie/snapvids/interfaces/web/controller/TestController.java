package io.github.hellovie.snapvids.interfaces.web.controller;

import io.github.hellovie.snapvids.common.exception.business.BusinessException;
import io.github.hellovie.snapvids.common.exception.system.SystemException;
import io.github.hellovie.snapvids.common.module.common.CommonExceptionType;
import io.github.hellovie.snapvids.common.util.ResultResponse;
import io.github.hellovie.snapvids.interfaces.web.config.LoggerConfig;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 测试控制器。
 *
 * @author hellovie
 * @since 1.0.0
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public ResultResponse.SuccessResult<String> sayHello() {
        MDC.put(LoggerConfig.TRACE_ID, UUID.randomUUID().toString());
        return ResultResponse.success("Hello World!");
    }

    @GetMapping("/business")
    public ResultResponse.SuccessResult<Void> throwBusinessException() {
        MDC.put(LoggerConfig.TRACE_ID, UUID.randomUUID().toString());
        throw new BusinessException(CommonExceptionType.TEST_BUSINESS_EXCEPTION);
    }

    @GetMapping("/system")
    public ResultResponse.SuccessResult<Void> throwSystemException() {
        MDC.put(LoggerConfig.TRACE_ID, UUID.randomUUID().toString());
        try {
            int i = 10 / 0;
        } catch (Exception ex) {
            throw new SystemException(CommonExceptionType.UNKNOWN_EXCEPTION, ex);
        }
        return ResultResponse.success(null);
    }

    @GetMapping("/unknown")
    public ResultResponse.SuccessResult<Void> throwUnknownException() throws Exception {
        MDC.put(LoggerConfig.TRACE_ID, UUID.randomUUID().toString());
        try {
            int i = 10 / 0;
        } catch (Exception ex) {
            throw new Exception("未知异常", ex);
        }
        return ResultResponse.success(null);
    }
}
