package io.github.hellovie.snapvids.infrastructure.service.generator;

import com.github.ksuid.KsuidGenerator;
import io.github.hellovie.snapvids.common.service.IdGenerateService;
import org.springframework.stereotype.Component;

/**
 * Ksuid 生成器。
 *
 * @author hellovie
 * @since 1.0.0
 */
@Component("ksuidGenerator")
public class KsuidGenerateService implements IdGenerateService<String> {

    /**
     * {@inheritDoc}
     *
     * @see IdGenerateService#genId()
     */
    @Override
    public String genId() {
        return KsuidGenerator.generate();
    }
}
