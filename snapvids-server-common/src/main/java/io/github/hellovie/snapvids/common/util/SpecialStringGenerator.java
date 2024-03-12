package io.github.hellovie.snapvids.common.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 特殊格式的字符串生成器。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class SpecialStringGenerator {

    private SpecialStringGenerator() {}

    /**
     * 生成今天的格式字符串。
     * <p>格式：yyyyMMdd</p>
     *
     * @return 今天的格式字符串，如：20240520
     */
    public static String genTodayFormatted() {
        return genTodayFormatted("yyyyMMdd");
    }

    /**
     * 生成今天的指定格式的字符串。
     *
     * @param pattern 格式
     * @return 指定格式的字符串
     */
    public static String genTodayFormatted(String pattern) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return today.format(formatter);
    }

    /**
     * 生成不重复的随机字符串。
     *
     * @return 不重复的随机字符串
     */
    public static String genNoRepeatRandomStr() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
