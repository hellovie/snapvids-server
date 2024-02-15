package io.github.hellovie.snapvids.launcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 程序主入口。
 *
 * @author hellovie
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackages = "io.github.hellovie.snapvids")
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
