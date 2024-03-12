package io.github.hellovie.snapvids.infrastructure.service.generator;

import io.github.hellovie.snapvids.infrastructure.config.InfrastructureBeanConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashSet;

/**
 * Identifier Generate Service Test.
 *
 * @author hellovie
 * @since 1.0.0
 */
@SpringBootTest(classes = InfrastructureBeanConfig.class)
public class IdGenerateServiceTest {

    @Resource(name = "ksuidGenerator")
    private IdGenerateService<String> ksuidGenerator;

    @Test
    void testKsuidGenerateService() {
        System.out.println("ksuid: " + ksuidGenerator.genId());
        HashSet<String> ids = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            String id = ksuidGenerator.genId();
            Assertions.assertFalse(ids.contains(id), "KsuidGenerateService generate the same id");
            ids.add(id);
        }
    }
}
