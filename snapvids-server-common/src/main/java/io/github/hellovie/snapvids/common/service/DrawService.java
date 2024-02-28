package io.github.hellovie.snapvids.common.service;

import java.awt.image.BufferedImage;

/**
 * 绘图服务。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface DrawService {

    /**
     * 将 text 转成图片。
     *
     * @param bufferedImage {@link BufferedImage}
     * @param text          字符串
     */
    void drawToBufferedImage(BufferedImage bufferedImage, String text);

    /**
     * 将 text 转成 Base64 图片。
     *
     * @param base64 {@link StringBuffer}
     * @param text   字符串
     */
    void drawToBase64(StringBuffer base64, String text);
}
